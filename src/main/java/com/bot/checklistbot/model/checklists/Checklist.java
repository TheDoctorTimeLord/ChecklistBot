package com.bot.checklistbot.model.checklists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bot.checklistbot.model.userstate.UserData;

/**
 * Сущность, описывающая чеклист (список дел) пользователя.
 * Title - название чеклиста, Capture - описание чеклиста, Items - набор пунктов в чеклисте.
 */
@Entity
public class Checklist
{
    @GeneratedValue
    @Id
    private long id;
    @Column
    private String title;
    @Column
    private String capture;
    @OneToMany(mappedBy = "checklist")
    private final Set<ChecklistItem> items = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "userdata_id")
    private UserData owner;


    public Checklist() {}

    public Checklist(String title, String capture) {
        this.title = title;
        this.capture = capture;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setOwner(UserData owner)
    {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    public void addItem(ChecklistItem checklistItem) {
        items.add(checklistItem);
    }

    public void deleteItem(ChecklistItem checklistItem) {
        items.remove(checklistItem);
    }
}
