package com.bot.checklistbot.model.userstate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @Convert(converter = UserStateConverter.class)
    private UserState state;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private final List<Checklist> checklists = new ArrayList<>();

    public UserData() {}

    public UserData(long id) {
        this.id = id;
        this.state = new UserState();
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

    void setState(UserState state) {
        this.state = state;
    }

    void addChecklists(Checklist checklist) {
        checklists.add(checklist);
    }

    void deleteChecklist(Checklist checklist) {
        checklists.remove(checklist);
    }
}
