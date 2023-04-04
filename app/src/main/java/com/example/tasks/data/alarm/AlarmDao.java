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

    @Query("select * from alarm_t where al_task = :almTask order by -al_time desc")
    LiveData<List<Alm>> getTaskAlarms(int almTask);
}
