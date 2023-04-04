package com.example.tasks.data.thread;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ThreadDao {
    @Insert
    void insThd(Thd thread);

    @Update
    void updThd(Thd thread);

    @Delete
    void delThd(Thd thread);

    @Query("select thd.thd_id, thd.thd_title, thd.thd_sts " +
            "from thread_t thd " +
            "left join task_t t on t.tsk_thd = thd.thd_id " +
            "left join alarm_t a on a.al_task = t.tsk_id " +
            "group by thd.thd_id " +
            "order by MIN(a.al_time) desc")
    LiveData<List<Thd>> getSortedThreads();
}
