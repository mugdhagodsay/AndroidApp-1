package com.example.mukulkarni.simpletodo.todo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by mukulkarni on 2/20/17.
 */

// Task table for the SimpleTodo database
@Table(database = TaskDataBase.class)
public class Task extends BaseModel {
    @PrimaryKey
    @Column
    private String task;

    public void setTask(String task) {
        this.task = task;
    }

    public String getTask()
    {
        return task;
    }
}
