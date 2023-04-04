package com.example.tasks.data.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insTask(Tsk task);

    @Update
    void updTask(Tsk task);

    @Delete
    void delTask(Tsk task);

    @Query("select * from task_t")
    LiveData<List<Tsk>> getAllTasks();

    @Query("SELECT t.tsk_id, t.tsk_title, t.tsk_note, t.tsk_sts, t.tsk_thd, MIN(a.al_time) AS min_alarm_time " +
            "FROM task_t t LEFT JOIN alarm_t a ON t.tsk_id = a.al_task " +
            "where t.tsk_thd = :taskThd " +
            "GROUP BY t.tsk_id " +
            "ORDER BY min_alarm_time ASC")
    LiveData<List<Tsk>> getThreadTask(int taskThd);
}
