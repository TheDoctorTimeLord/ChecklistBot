package com.bot.checklistbot.serviceclasses.parsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bot.checklistbot.serviceclasses.parsing.parsers.DelimiterParser;
import com.bot.checklistbot.serviceclasses.parsing.parsers.OptionalPartParser;
import com.bot.checklistbot.serviceclasses.parsing.parsers.StartOfLineParser;
import com.bot.checklistbot.serviceclasses.parsing.parsers.TextElementParser;

/**
 * Builder для удобного формирования {@link InputParser парсера ввода пользователя}
 */
public class InputParserBuilder {
    private InputParserBuilder() {}

    /**
     * Начинает процесс сборки парсера
     */
    public static BeginPatternParserBuilder builder() {
        return new BeginPatternParserBuilder();
    }

    /**
     * Builder, предоставляющий дополнительный функционал для действий вначале ввода
     */
    public static class BeginPatternParserBuilder extends WithOptionalPartPatternParserBuilder {
        /**
         * Привязывает паттерн парсинга к началу строки
         */
        public WithOptionalPartPatternParserBuilder bindToStartOfLine() {
            chain.add(new StartOfLineParser());
            return this;
        }
    }

    /**
     * Builder, предоставляющий дополнительный функционал для работы с необязательными частями ввода пользователя
     */
    public static class WithOptionalPartPatternParserBuilder extends ParserBuilder {
        /**
         * Объявляет о начале необязательной части
         */
        public ParserBuilder optionalPart() {
            chain.add(new OptionalPartParser());
            return this;
        }

        @Override
        public WithOptionalPartPatternParserBuilder addElement(String elementName)
        {
            super.addElement(elementName);
            return this;
        }

        @Override
        public WithOptionalPartPatternParserBuilder addDelimiter(String delimiter)
        {
            super.addDelimiter(delimiter);
            return this;
        }
    }

    /**
     * Builder, предоставляющий основной функционал для парсинга
     */
    public static class ParserBuilder {
        protected final Set<String> availableElements = new HashSet<>();
        protected final List<ChainElement> chain = new ArrayList<>();

        /**
         * Собирает итоговый {@link InputParser}
         */
        public InputParser build() {
            return new InputParserImpl(availableElements, chain);
        }

        /**
         * Добавляет в парсер место для извлечения данных. Получить извлечённые данные можно будет по заданному имени
         * @param elementName имя извлекаемых данных
         */
        public ParserBuilder addElement(String elementName) {
            availableElements.add(elementName);
            chain.add(new TextElementParser(elementName));
            return this;
        }

        /**
         * Добавляет разделитель для разграничения элементов (см. {@link #addElement(String)})
         * @param delimiter разделитель элементов
         */
        public ParserBuilder addDelimiter(String delimiter) {
            chain.add(new DelimiterParser(delimiter));
            return this;
        }
    }
}
