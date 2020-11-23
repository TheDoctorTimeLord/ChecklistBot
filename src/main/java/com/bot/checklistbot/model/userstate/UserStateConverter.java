package com.bot.checklistbot.model.userstate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Конвертер для преобразования состояния пользователя в формат для хранения в базе данных
 */
@Converter
public class UserStateConverter implements AttributeConverter<UserState, String> {
    @Override
    public String convertToDatabaseColumn(UserState attribute) {
        return attribute.toString();
    }

    @Override
    public UserState convertToEntityAttribute(String dbData) {
        return UserState.createByString(dbData);
    }
}
