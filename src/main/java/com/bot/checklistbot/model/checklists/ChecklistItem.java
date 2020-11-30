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
    @Column
    private boolean hasSchedule;
    @Column
    private String schedule;


    public ChecklistItem() {}

    public ChecklistItem(String capture, String schedule, boolean hasSchedule) {
        this.capture = capture;
        this.state = true;
        this.schedule = schedule;
        this.hasSchedule = hasSchedule;
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

    public boolean hasSchedule() {
        return hasSchedule;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
