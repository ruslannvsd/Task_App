package com.example.tasks.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tasks.data.alarm.AlarmDao;
import com.example.tasks.data.alarm.Alm;
import com.example.tasks.data.link.TskTg;
import com.example.tasks.data.link.TskTgDao;
import com.example.tasks.data.tag.TagDao;
import com.example.tasks.data.tag.Tg;
import com.example.tasks.data.task.TaskDao;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.data.thread.Thd;
import com.example.tasks.data.thread.ThreadDao;

@Database(entities = { Thd.class, Tsk.class, Tg.class, Alm.class, TskTg.class }, version = 1, exportSchema = false)
abstract public class ProjDB extends RoomDatabase {
    public abstract ThreadDao threadDao();
    public abstract TaskDao taskDao();
    public abstract TagDao tagDao();
    public abstract AlarmDao alarmDao();
    public abstract TskTgDao ttDao();
    private static volatile ProjDB INSTANCE;

    public static synchronized ProjDB getDatabase(final Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), ProjDB.class, "proj_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
/*
abstract public class ProjDB extends RoomDatabase {
    public static volatile ProjDB projDB;
    public abstract TodoDao todoDao();
    public abstract LineDao lineDao();
    public abstract TagDao tagDao();
    public abstract JunctionDao junctionDao();
    public abstract AlarmDao alarmDao();

    public static synchronized ProjDB getProjDB(final Context ctx) {
        if (projDB == null) {
            projDB = Room.databaseBuilder(ctx.getApplicationContext(), ProjDB.class, "proj_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return projDB;
    }
}
 */
