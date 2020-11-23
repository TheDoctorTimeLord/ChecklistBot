package com.bot.checklistbot.handling.implementations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.handling.CommandManager;
import com.bot.checklistbot.handling.commands.ExceptionCommand;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.LocalizedText;

/**
 * Реализация для {@link CommandManager}
 */
@Component
public class CommandManagerImpl implements CommandManager {
    private static final Logger LOG = LoggerFactory.getLogger(CommandManagerImpl.class);
    private static final String EMPTY_VALUE = "";
    private static final Collection<String> DELIMITERS = Arrays.asList(":", "\n");

    private final Map<String, Class<? extends BotCommand>> commands = new HashMap<>();
    private final ApplicationContext applicationContext;

    @Autowired
    public CommandManagerImpl(ApplicationContext applicationContext, List<BotCommand> commands) {
        this.applicationContext = applicationContext;

        for (BotCommand command : commands) {
            registerCommand(command);
        }
    }

    /**
     * Регистрирует команду в менеджере
     * @param command регистрируемая команда
     */
    void registerCommand(BotCommand command) {
        String commandCode = command.getCode();
        commands.put(commandCode, command.getClass());
    }

    @Override
    public List<BotCommand> getAllCommands() {
        return commands.values().stream()
                .map(applicationContext::getBean)
                .collect(Collectors.toList());
    }

    @Override
    public BotCommand getCommandByContext(UserData userData, BotMessage message) {
        UserState state = userData.getState();
        CommandContext context = extractContextFromMessage(message.getMessage());

        if (context.commandName.equals(EMPTY_VALUE) || !commands.containsKey(context.commandName)) {
            return new ExceptionCommand(userData, LocalizedText.COMMAND_NOT_FOUND);
        }

        Class<? extends BotCommand> commandClass = commands.get(context.commandName);
        BotCommand command = applicationContext.getBean(commandClass);

        try {
            if (!command.isValid(userData)) {
                throw new CommandInitializeException(LocalizedText.INCORRECT_STATE);
            }
            command.initialize(userData, context.commandMetadata);
        } catch (CommandInitializeException e) {
            command = new ExceptionCommand(userData, LocalizedText.incorrectCommandFormat(e.getMessage()));
            String msg = String.format("Error during initialize command %s in state %s", command.toString(), state.toString());
            LOG.error(msg, e);
        }

        return command;
    }

    private static CommandContext extractContextFromMessage(String message) {
        if (message == null) {
            return new CommandContext(EMPTY_VALUE, EMPTY_VALUE);
        }

        message = message.trim();
        int endCommandName = findDelimiter(message);

        if (endCommandName == -1) {
            return new CommandContext(message, EMPTY_VALUE);
        }

        String commandName = message.substring(0, endCommandName).trim();
        String commandMeta = message.substring(endCommandName + 1).trim();
        return new CommandContext(commandName, commandMeta);
    }

    private static int findDelimiter(String message) {
        for (String delimiter : DELIMITERS) {
            int position = message.indexOf(delimiter);
            if (position != -1) {
                return position;
            }
        }
        return -1;
    }

    private static class CommandContext {
        private final String commandName;
        private final String commandMetadata;

        private CommandContext(String commandName, String commandMetadata)
        {
            this.commandName = commandName;
            this.commandMetadata = commandMetadata;
        }
    }
}
