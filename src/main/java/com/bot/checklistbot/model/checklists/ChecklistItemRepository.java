package com.bot.checklistbot.model.checklists;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, для работы с {@link ChecklistItem}, хранящемся в базе данных
 */
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    /**
     * Осуществляет поиск пункта списка по его описанию
     * @param capture описание списка
     * @return элемент списка
     */
    ChecklistItem findByCapture(String capture);
}
