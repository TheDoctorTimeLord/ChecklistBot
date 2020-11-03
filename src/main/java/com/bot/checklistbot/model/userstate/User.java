package com.bot.checklistbot.model.userstate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Сущность, описывающая регистрацию пользователя. Хранит всю необходимую информацию о сессии пользователя. Сущность
 * характеризуется id, задаваемым извне.
 */
@Entity
public class User
{
    @Id
    private long id;
    @Enumerated(EnumType.STRING)
    private UserState state;

    public User() {}

    public User(long id) {
        this.id = id;
        this.state = UserState.START;
    }

    public long getId() {
        return id;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
