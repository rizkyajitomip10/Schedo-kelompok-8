package com.Schedo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM " + AppDb.TABLE + " ORDER BY task_id DESC")
    List<Task> getAll();

    @Query("SELECT * FROM " + AppDb.TABLE + " WHERE task_id=:task_id")
    Task getById(String task_id);

    @Insert
    long add(Task task);

    @Update
    int up(Task task);

    @Delete
    void del(Task task);

    // Scheduler
    @Query("SELECT * FROM " + AppDb.TABLE_SCHEDULE + " WHERE hari=:hari ORDER BY schedule_id DESC")
    List<Scheduler> getAllSchedule(String hari);

    @Query("SELECT * FROM " + AppDb.TABLE_SCHEDULE + " WHERE schedule_id=:sch_id")
    Scheduler getByIdSchedule(String sch_id);

    @Insert
    long addSchedule(Scheduler scheduler);

    @Update
    int upSchedule(Scheduler scheduler);

    @Delete
    void delSchedule(Scheduler scheduler);
}
