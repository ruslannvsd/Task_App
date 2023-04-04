package com.example.tasks.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

import java.util.List;

public class ProjRep {
    private final ThreadDao threadDao;
    private final TaskDao taskDao;
    private final TagDao tagDao;
    private final AlarmDao alarmDao;
    private final TskTgDao ttDao;

    public ProjRep(Application app) {
        ProjDB db = ProjDB.getDatabase(app);
        threadDao = db.threadDao();
        taskDao = db.taskDao();
        tagDao = db.tagDao();
        alarmDao = db.alarmDao();
        ttDao = db.ttDao();
    }

    // main getAll queries
    LiveData<List<Thd>> getThreads() { return threadDao.getSortedThreads(); }
    LiveData<List<Tsk>> getAllTasks() { return taskDao.getAllTasks(); }
    LiveData<List<Tg>> getAllTags() { return tagDao.getTags(); }
    LiveData<List<Tsk>> getThreadTasks(int thread) { return taskDao.getThreadTask(thread); }
    LiveData<List<Alm>> getTaskAlarms(int task) { return alarmDao.getTaskAlarms(task); }

    // thread IUD fun
    void insThd(Thd thread) { threadDao.insThd(thread); }
    public void updThd(Thd thread) { threadDao.updThd(thread); }
    public void delThd(Thd thread) { threadDao.delThd(thread); }

    // task IUD fun
    public void insTask(Tsk task) { taskDao.insTask(task); }
    public void updTask(Tsk task) { taskDao.updTask(task); }
    public void delTask(Tsk task) { taskDao.delTask(task); }

    // alarms IUD fun
    public void insAlarm(Alm alarm) { alarmDao.insAlarm(alarm); }
    public void updAlarm(Alm alarm) { alarmDao.updAlarm(alarm); }
    public void delAlarm(Alm alarm) { alarmDao.delAlarm(alarm); }

    // tags IUD fun
    public void insTag(Tg tag) { tagDao.insTag(tag); }
    public void updTag(Tg tag) { tagDao.updTag(tag); }
    public void delTag(Tg tag) { tagDao.delTag(tag); }

    // link IUD fun
    void insTT(TskTg tt) { ttDao.insTT(tt); }
}
