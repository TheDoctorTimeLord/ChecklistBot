package com.bot.checklistbot.serviceclasses.parsing.parsers;

import com.bot.checklistbot.serviceclasses.parsing.ChainElement;
import com.bot.checklistbot.serviceclasses.parsing.ChainParsersHandler;

/**
 * Парсер привязки паттерна к началу строки
 */
public class StartOfLineParser implements ChainElement {
    private static final String PATTERN_PART = "^";

    @Override
    public void nextParsePatternPart(StringBuilder pattern, ChainParsersHandler handler) {
        if (pattern.length() != 0) {
            throw new IllegalStateException(String.format("%s is not in start pattern", PATTERN_PART));
        }
        pattern.append(PATTERN_PART);
        handler.parseNextPatternPart(pattern);
    }
}
