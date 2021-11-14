package com.Schedo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, Scheduler.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    //DB Conctract
    public static final String DB = "notes_db";
    public static final String TABLE = "task";
    public static final String TABLE_SCHEDULE = "scheduler";

    public abstract TaskDao taskDao();
}
