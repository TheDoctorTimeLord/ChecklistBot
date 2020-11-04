package com.bot.checklistbot.model.checklists;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, описывающая чеклист пользователя.
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
    private final List<ChecklistItem> items = new ArrayList<>();


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
