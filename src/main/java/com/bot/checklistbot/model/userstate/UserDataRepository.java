package com.bot.checklistbot.model.userstate;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, для работы с {@link UserData}, хранящемся в базе данных
 */
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
