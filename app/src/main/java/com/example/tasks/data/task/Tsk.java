package com.example.tasks.data.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.tasks.data.thread.Thd;

@Entity(tableName = "task_t",
        foreignKeys = @ForeignKey(
        entity = Thd.class,
        parentColumns = "thd_id",
        childColumns = "tsk_thd",
        onDelete = ForeignKey.CASCADE
))
public class Tsk {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "tsk_id")        private int id;
    @ColumnInfo(name = "tsk_title")     private String title;
    @ColumnInfo(name = "tsk_note")      private String note;
    @ColumnInfo(name = "tsk_sts")       private boolean sts;
    @ColumnInfo(name = "tsk_thd")       private int thread;

    public Tsk(int id, String title, String note, boolean sts, int thread) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.sts = sts;
        this.thread = thread;
    }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setNote(String note) { this.note = note; }
    public void setSts(boolean sts) { this.sts = sts; }
    public void setThread(int thread) { this.thread = thread; }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getNote() { return note; }
    public boolean getSts() { return sts; }
    public int getThread() { return thread; }
}
