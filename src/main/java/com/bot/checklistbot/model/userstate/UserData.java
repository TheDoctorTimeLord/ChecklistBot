package com.bot.checklistbot.model.userstate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bot.checklistbot.model.checklists.Checklist;

/**
 * Сущность, описывающая регистрацию пользователя. Хранит всю необходимую информацию о сессии пользователя. Сущность
 * характеризуется id, задаваемым извне.
 * Id - идентификатор пользователя, state - состояние пользователя в последнем запросе, checklists - списки дел
 * пользоватлея
 */
@Entity
public class UserData
{
    @Id
    private long id;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @OneToMany(mappedBy = "owner")
    private final List<Checklist> checklists = new ArrayList<>();

    public UserData() {}

    public UserData(long id) {
        this.id = id;
        this.state = UserState.START;
    }

    public long getId() {
        return id;
    }

    public UserState getState() {
        return state;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void addChecklists(Checklist checklist) {
        checklists.add(checklist);
    }

    public void deleteChecklist(Checklist checklist) {
        checklists.remove(checklist);
    }
}
