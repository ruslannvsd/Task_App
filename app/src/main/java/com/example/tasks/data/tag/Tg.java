package com.example.tasks.data.tag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_t")
public class Tg {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "tg_id")     private int id;
    @ColumnInfo(name = "title")     private String title;

    public Tg(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) {this.title = title; }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
}
