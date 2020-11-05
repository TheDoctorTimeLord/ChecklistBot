package com.bot.checklistbot.model.checklists;

/**
 * Сервис для работы с {@link Checklist чеклистами}. Позволяет получать данные, связанные с секлистом, а
 * также изменять информацию о его состоянии
 */
public interface ChecklistService
{
    /**
     * Метод получения {@link Checklist чеклиста} из базы данных.
     *
     * @param id уникальный идентификатор чеклиста
     * @return данные чеклиста
     */
    Checklist find(long id);

    /**
     * Обновляет данные о чеклисте в базе данных
     *
     * @param checklist новые данные чеклиста
     */
    Checklist save(Checklist checklist);

    /**
     * Метод удаления {@link Checklist чеклиста} из базы данных.
     *
     * @param checklist чеклист
     */
    void delete(Checklist checklist);

    /**
     * Метод добавления {@link ChecklistItem пункта чеклиста}
     *
     * @param checklistItem пункт
     * @param checklist чеклист
     */
    void addItem(ChecklistItem checklistItem, Checklist checklist);

    /**
     * Метод удаления {@link ChecklistItem пункта чеклиста}
     *
     * @param checklistItem пункт
     * @param checklist чеклист
     */
    void deleteItem(ChecklistItem checklistItem, Checklist checklist);
}
