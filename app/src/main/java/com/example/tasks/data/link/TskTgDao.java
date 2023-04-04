package com.example.tasks.data.link;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface TskTgDao {
    @Insert
    void insTT(TskTg tt);
}
