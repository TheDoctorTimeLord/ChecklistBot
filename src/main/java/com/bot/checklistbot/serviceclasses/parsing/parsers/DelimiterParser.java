package com.bot.checklistbot.serviceclasses.parsing.parsers;

import com.bot.checklistbot.serviceclasses.parsing.ChainElement;
import com.bot.checklistbot.serviceclasses.parsing.ChainParsersHandler;

/**
 * Парсер разделителя в строке
 */
public class DelimiterParser implements ChainElement {
    private static final String TEMPLATE = "\\s*%s\\s+";
    private final String delimiter;

    public DelimiterParser(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public void nextParsePatternPart(StringBuilder pattern, ChainParsersHandler handler) {
        pattern.append(String.format(TEMPLATE, delimiter));
        handler.parseNextPatternPart(pattern);
    }
}
