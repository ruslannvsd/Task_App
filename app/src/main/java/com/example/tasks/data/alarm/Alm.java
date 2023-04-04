package com.example.tasks.data.alarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_t")
public class Alm {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "al_id")         private int id;
    @ColumnInfo(name = "al_time")       private long time;
    @ColumnInfo(name = "al_task")       private int task;

    public Alm(int id, long time, int task) {
        this.id = id;
        this.time = time;
        this.task = task;
    }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTime(long time) { this.time = time; }
    public void setTask(int task) { this.task = task; }

    // getters
    public int getId() { return id; }
    public long getTime() { return time; }
    public int getTask() { return task; }
}
