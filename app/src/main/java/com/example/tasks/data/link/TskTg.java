package com.example.tasks.data.link;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.tasks.data.tag.Tg;
import com.example.tasks.data.task.Tsk;

@Entity(tableName = "link_t",
        primaryKeys = {"task_id", "tag_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Tsk.class,
                        parentColumns = "tsk_id",
                        childColumns = "task_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Tg.class,
                        parentColumns = "tg_id",
                        childColumns = "tag_id",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class TskTg {
    @ColumnInfo(name = "task_id")       private int task;
    @ColumnInfo(name = "tag_id")        private int tag;

    public TskTg(int task, int tag) {
        this.task = tag;
        this.tag = tag;
    }
    // setters
    public void setTask(int task) { this.task = task; }
    public void setTag(int tag) { this.tag = tag; }

    // getters
    public int getTask() { return task; }
    public int getTag() { return tag; }
}
