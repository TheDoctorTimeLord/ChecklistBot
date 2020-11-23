package com.bot.checklistbot.serviceclasses.parsing;

/**
 * Интерфейс объекта, используемого в {@link ChainParsersHandler цепи парсеров}.
 */
public interface ChainElement {
    /**
     * Добавляет в паттерн регулярного выражения новую часть
     * @param pattern паттерн регулярного выражения
     * @param handler обработчик, необходимый для запуска следующих элементов цепи
     */
    void nextParsePatternPart(StringBuilder pattern, ChainParsersHandler handler);
}
