package com.example.mukulkarni.simpletodo.todo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by mukulkarni on 2/20/17.
 */

// Task table for the SimpleTodo database
@Table(database = TaskDataBase.class)
public class Task extends BaseModel implements Serializable {
    @PrimaryKey
    @Column
    private String task;

    @Column
    private String priority;

    @Column
    private String notes;

    @Column
    private String dueDate;

    public void setTask(String task) {
        this.task = task;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }

    public String getTask()
    {
        return task;
    }

    public String getPriority()
    {
        return priority;
    }

    public String getNotes()
    {
        return notes;
    }

    public String getDueDate()
    {
        return dueDate;
    }
}
