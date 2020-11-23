package com.bot.checklistbot.serviceclasses;

/**
 * Утилитарные методы для работы со строками
 */
public class StringUtils {
    /**
     * Зачёркивает полученную строку
     * @param str строка
     * @return зачёркнутая строка
     */
    public static String strikeOut(String str) {
        return "<s>" + str + "</s>";
    }

    /**
     * Проверяет строку на пустоту
     * @param str проверяемая строка
     * @return true - строка пуста, false - иначе
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
