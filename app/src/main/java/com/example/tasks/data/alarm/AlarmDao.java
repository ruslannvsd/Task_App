package com.example.tasks.data.alarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insAlarm(Alm alarm);

    @Update
    void updAlarm(Alm alarm);

    @Delete
    void delAlarm(Alm alarm);

    @Query("SELECT * FROM alarm_t WHERE al_task = :task ORDER BY al_time ASC LIMIT 1")
    LiveData<Alm> getMinAlarm(int task);

    @Query("select * from alarm_t where al_task = :almTask order by -al_time desc")
    LiveData<List<Alm>> getTaskAlarms(int almTask);

    @Query("select alarm_t.* " +
            "from alarm_t " +
            "inner join task_t ON alarm_t.al_task = task_t.tsk_id " +
            "WHERE task_t.tsk_thd = :thread")
    LiveData<List<Alm>> getThdAlarms(int thread);
}
