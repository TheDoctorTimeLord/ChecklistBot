package com.bot.checklistbot.model.checklists;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, для работы с {@link Checklist}, хранящемся в базе данных
 */
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
