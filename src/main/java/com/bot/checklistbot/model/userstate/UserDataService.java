package com.bot.checklistbot.model.userstate;

import com.bot.checklistbot.model.checklists.Checklist;

/**
 * Сервис для работы с {@link UserData данными пользователя}. Позволяет получать данные, связанный с пользователем, а
 * также изменять информацию о его состоянии
 */
public interface UserDataService
{
    /**
     * Метод получения {@link UserData пользователя} из базы данных. Если в базе нет пользователя с указанным id, то
     * производит сохранения его в базу
     *
     * @param id уникальный идентификатор пользователя
     * @return данные пользователя
     */
    UserData findOrSave(long id);

    /**
     * Обновляет данные о пользователе в базе данных
     *
     * @param registration новые данные пользователя
     */
    void update(UserData registration);

    /**
     * Добавляет {@link Checklist список дел} пользователю
     *
     * @param userData пользователь, которому добавляется список дел
     * @param checklist список дел, добавляемый пользователю
     */
    void addChecklist(UserData userData, Checklist checklist);

    /**
     * Удаляет {@link Checklist список дел} пользователя
     *
     * @param userData список дел, удаляемый у пользователя
     * @param checklist удаляемый список дел
     */
    void deleteChecklist(UserData userData, Checklist checklist);

    /**
     * Изменяет текущее состояние пользователя на новое
     * @param userData пользователь, которому изменяют состояние
     * @param userState новое состояние
     */
    void changeUserState(UserData userData, UserState userState);
}
