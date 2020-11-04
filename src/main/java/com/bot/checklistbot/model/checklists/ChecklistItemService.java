package com.bot.checklistbot.model.checklists;

/**
 * Сервис для работы с {@link ChecklistItem пунктами чеклиста}. Позволяет получать данные, связанные с пунктом, а
 * также изменять информацию о его состоянии
 */
public interface ChecklistItemService
{
    /**
     * Метод получения {@link ChecklistItem пункта чеклиста} из базы данных.
     *
     * @param id уникальный идентификатор пункта
     * @return данные пункта
     */
    ChecklistItem find(long id);

    /**
     * Обновляет данные о пункте в базе данных
     *
     * @param checklistItem новые данные пункта
     */
    void save(ChecklistItem checklistItem);

    /**
     * Метод удаления {@link ChecklistItem пункта чеклиста} из базы данных.
     *
     * @param checklistItem пункт
     */
    void delete(ChecklistItem checklistItem);
}
