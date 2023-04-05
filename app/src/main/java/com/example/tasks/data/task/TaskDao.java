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

    @Query("select task_t.*, MIN(alarm_t.al_time) as min_alarm_time " +
            "from task_t left join alarm_t on task_t.tsk_id = alarm_t.al_task " +
            "group by task_t.tsk_id " +
            "order by min_alarm_time asc")
    LiveData<List<Tsk>> getAllTasks();

    @Query("select t.tsk_id, t.tsk_title, t.tsk_note, t.tsk_sts, t.tsk_thd, MIN(a.al_time) as min_alarm_time " +
            "from task_t t left join alarm_t a on t.tsk_id = a.al_task " +
            "where t.tsk_thd = :taskThd " +
            "group by t.tsk_id " +
            "order by min_alarm_time asc")
    LiveData<List<Tsk>> getThreadTask(int taskThd);
}
