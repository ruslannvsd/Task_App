package com.example.tasks.data.tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tasks.data.task.Tsk;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    void insTag(Tg tag);

    @Update
    void updTag(Tg tag);

    @Delete
    void delTag(Tg tag);

    @Query("select * from tag_t")
    LiveData<List<Tg>> getTags();
}
