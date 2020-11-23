package com.bot.checklistbot.model.checklists;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @OneToMany(mappedBy = "checklist", fetch = FetchType.EAGER)
    private final List<ChecklistItem> items = new ArrayList<>();
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

    public String getCapture() {
        return capture;
    }

    public UserData getOwner() {
        return owner;
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    public void setOwner(UserData owner)
    {
        this.owner = owner;
    }

    public void addItem(ChecklistItem checklistItem) {
        items.add(checklistItem);
    }

    public void deleteItem(ChecklistItem checklistItem) {
        items.remove(checklistItem);
    }
}
