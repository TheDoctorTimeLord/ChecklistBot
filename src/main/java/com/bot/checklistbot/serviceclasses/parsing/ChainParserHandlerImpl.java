package com.bot.checklistbot.serviceclasses.parsing;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Реализация интерфейса {@link ChainParsersHandler}
 */
public class ChainParserHandlerImpl implements ChainParsersHandler {
    private final List<ChainElement> chain;
    private int currentElement = -1;

    public ChainParserHandlerImpl(List<ChainElement> chain) {
        this.chain = chain;
    }

    @Override
    public void parseNextPatternPart(StringBuilder pattern) {
        currentElement++;

        if (chain.size() == currentElement) {
            return;
        }

        chain.get(currentElement).nextParsePatternPart(pattern, this);
    }

    /**
     * Создаёт паттерн парсинга по переданным компонентам паттерна
     * @return паттерн регулярного выражения
     */
    public Pattern createPattern() {
        StringBuilder sb = new StringBuilder();
        parseNextPatternPart(sb);
        String pattern = sb.toString();
        return Pattern.compile(pattern);
    }
}
