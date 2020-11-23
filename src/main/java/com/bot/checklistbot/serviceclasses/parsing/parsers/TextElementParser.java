package com.bot.checklistbot.serviceclasses.parsing.parsers;

import com.bot.checklistbot.serviceclasses.parsing.ChainElement;
import com.bot.checklistbot.serviceclasses.parsing.ChainParsersHandler;

/**
 * Парсер для извлекаемой части строки. Извлекаемая часть маркируется именем
 */
public class TextElementParser implements ChainElement {
    private static final String TEMPLATE = "(?<%s>[a-zA-Zа-яА-ЯёЁ0-9 ]+)";
    private final String elementName;

    public TextElementParser(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public void nextParsePatternPart(StringBuilder pattern, ChainParsersHandler handler) {
        pattern.append(String.format(TEMPLATE, elementName));
        handler.parseNextPatternPart(pattern);
    }
}
