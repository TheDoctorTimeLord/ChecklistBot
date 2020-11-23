package com.bot.checklistbot.model.checklists;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, для работы с {@link Checklist}, хранящемся в базе данных
 */
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    /**
     * Осуществляет поиск списка по его названию
     * @param title название списка
     * @return списка
     */
    Checklist findByTitle(String title);
}
