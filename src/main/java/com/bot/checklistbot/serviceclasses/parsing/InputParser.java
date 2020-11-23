package com.bot.checklistbot.serviceclasses.parsing;

/**
 * Класс для удобной работы с пользовательским вводом
 */
public interface InputParser {
    /**
     * Разбирает пользовательский ввод и подготавливает внутренние состояние {@link InputParser}
     * @param input пользовательский ввод
     * @return true - если разбор прошёл успешно, false - в противном случа
     */
    boolean parse(String input);

    /**
     * Получение элемента по названию, зарегистрированному в {@link InputParserBuilder}
     * @param elementName название элемента
     * @return элемент, полученный в группе с соответсвующим названием
     */
    String getElement(String elementName);
}
