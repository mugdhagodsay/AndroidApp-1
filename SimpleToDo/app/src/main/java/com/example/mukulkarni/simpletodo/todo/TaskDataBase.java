package com.example.mukulkarni.simpletodo.todo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by mukulkarni on 2/20/17.
 */

//Database for the SimpleTodo app

@Database(name = TaskDataBase.NAME, version = TaskDataBase.VERSION)
public class TaskDataBase {
    public static final String NAME = "TaskDataBase";

    public static final int VERSION = 1;
}
