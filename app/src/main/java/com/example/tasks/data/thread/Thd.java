package com.example.tasks.data.thread;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "thread_t")
public class Thd {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "thd_id")        public int id;
    @ColumnInfo(name = "thd_title")     public String title;
    @ColumnInfo(name = "thd_sts")       public boolean sts;

    public Thd(int id, String title, boolean sts) {
        this.id = id;
        this.title = title;
        this.sts = sts;
    }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setSts(boolean sts) { this.sts = sts; }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean getSts() { return sts; }
}
