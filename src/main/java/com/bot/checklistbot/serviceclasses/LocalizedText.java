package com.bot.checklistbot.serviceclasses;

import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.model.checklists.ChecklistItem;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Предоставляет весь локализованный текст в приложении
 */
public class LocalizedText {
    private static final String EXPECTED = "Ожидался: ";

    public static final String CHECKLIST_ITEM_MARKER = "(о) ";
    public static final String FORMAT_TEMPLATE = ">>> %s";
    public static final String CHECKLIST_TEMPLATE = "Список: %s";
    public static final String CHECKLIST_ITEM_TEMPLATE = "Пункт: %s";

    public static final String COMMAND_NOT_FOUND = "Команда не была распознана. Введите: " + Commands.LIST_COMMAND_CODE;
    public static final String ERROR = "Во время обработки команды произошла ошибка, команда не выполнена";
    public static final String INCORRECT_STATE = "Невозможно совершить эту операцию в данном меню";
    public static final String INCORRECT_SCHEDULE = "Расписание активации пункта неверно";
    public static final String NOT_HAVE_DATA = "Не хватает данных в запросе для совершения операции";
    public static final String CHECKLIST_NOT_FOUND = "Список с таким названием не найден";
    public static final String CHECKLIST_ITEM_NOT_FOUND = "Пункт с таким описанием не найден";
    public static final String SUCCESS_COMMAND = "Команда выполнена успешно";
    public static final String HAS_NOT_AVAILABLE_COMMANDS = "Нет доступных команд";
    public static final String AVAILABLE_COMMANDS = "Доступные команды:";

    private static final Map<String, String> COMMAND_FORMAT = ImmutableMap.<String, String>builder()
            .put(Commands.ADD_CHECKLIST_COMMAND_CODE, ": <название>! <описание>")
            .put(Commands.EDIT_CHECKLIST_COMMAND_CODE, ": <новое описание пункта>")
            .put(Commands.DELETE_CHECKLIST_COMMAND_CODE, "")
            .put(Commands.VIEW_CHECKLIST_COMMAND_CODE, "(: <название списка>)")
            .put(Commands.ADD_CHECKLIST_ITEM_COMMAND_CODE, ": <описание пункта>! <расписание в формате cron>")
            .put(Commands.DELETE_CHECKLIST_ITEM_COMMAND_CODE, "(: <описание пункта>)")
            .put(Commands.VIEW_CHECKLIST_ITEM_COMMAND_CODE, ": <описание пункта>")
            .put(Commands.CHANGE_CHECKLIST_ITEM_COMMAND_CODE, ": <описание пункта>")
            .put(Commands.BACK_MENU_COMMAND_CODE, "")
            .put(Commands.LIST_COMMAND_CODE, "")
            .build();

    public static final String EMPTY_ITEMS = "<Список пуст>";

    public static String incorrectCommandFormat(String message) {
        return "Неверный формат команды. " + message;
    }

    public static String saveSuccessful(String title) {
        return title + " успешно добавлен";
    }

    public static String deleteSuccessful(String title) {
        return title + " успешно удалён";
    }

    public static String titleChecklist(String title) {
        return "Список: " + title;
    }

    public static String captureChecklistItem(String capture) {
        return "Пункт: " + capture;
    }

    public static String notifyItemActive(String capture) {
        return capture + " снова активен";
    }

    public static String stateChecklistItem(boolean state) {
        return "Состояние: " + (state ? "активен" : "не активен");
    }

    public static String checklistItemChanged(ChecklistItem item) {
        return item.getCapture() + (item.isState() ? " активен" : " не активен");
    }

    public static String getCommandName(BotCommand botCommand) {
        return botCommand.getCode();
    }

    public static String getCommandFormat(BotCommand botCommand) {
        String code = botCommand.getCode();
        String format = COMMAND_FORMAT.get(code);
        if (format == null) {
            return "";
        }

        return getCommandName(botCommand) + format;
    }

    public static String getExpectedCommandFormat(BotCommand botCommand) {
        return EXPECTED + getCommandFormat(botCommand);
    }
}
