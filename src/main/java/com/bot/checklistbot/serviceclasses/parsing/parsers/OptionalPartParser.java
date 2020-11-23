package com.bot.checklistbot.serviceclasses.parsing.parsers;

import com.bot.checklistbot.serviceclasses.parsing.ChainElement;
import com.bot.checklistbot.serviceclasses.parsing.ChainParsersHandler;

/**
 * Парсер для необязательной части ввода пользователя
 */
public class OptionalPartParser implements ChainElement {
    @Override
    public void nextParsePatternPart(StringBuilder pattern, ChainParsersHandler handler) {
        pattern.append("(");
        handler.parseNextPatternPart(pattern);
        pattern.append(")?");
    }
}
