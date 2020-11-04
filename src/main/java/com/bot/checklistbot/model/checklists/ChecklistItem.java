package com.bot.checklistbot.model.checklists;

import javax.persistence.*;

/**
 * Сущность, описывающая пункт чеклиста пользователя.
 * Capture - основная информация пункта, которую будет задавать пользователь,
 * State - логическая переменная, обозначающая, является ли данный пункт активным или нет.
 */
@Entity
public class ChecklistItem
{
    @GeneratedValue
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;
    @Column
    private String capture;
    @Column
    private boolean state;


    public ChecklistItem() {}

    public ChecklistItem(String capture, boolean state) {
        this.capture = capture;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }
}
