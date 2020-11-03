package com.bot.checklistbot.model.userstate;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, для работы с {@link User}, хранящемся в базе данных
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
