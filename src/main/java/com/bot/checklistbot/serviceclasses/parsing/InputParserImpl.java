package com.bot.checklistbot.serviceclasses.parsing;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация для {@link InputParser}
 */
public class InputParserImpl implements InputParser {
    private final Set<String> availableElements;
    private final Pattern pattern;
    private Matcher matcher;

    public InputParserImpl(Set<String> availableElements, List<ChainElement> chain) {
        this.availableElements = availableElements;
        this.pattern = new ChainParserHandlerImpl(chain).createPattern();
    }

    @Override
    public boolean parse(String input)
    {
        matcher = pattern.matcher(input);
        boolean result = matcher.find();
        if (!result) {
            matcher = null;
        }
        return result;
    }

    @Override
    public String getElement(String elementName)
    {
        if (matcher == null) {
            throw new IllegalStateException("InputParsers not found any values");
        }
        if (!availableElements.contains(elementName)) {
            throw new IllegalArgumentException("Element '%s' was not defined");
        }
        return matcher.group(elementName);
    }
}
