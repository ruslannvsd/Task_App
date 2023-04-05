package com.example.tasks.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tasks.data.alarm.Alm;
import com.example.tasks.data.tag.Tg;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.data.thread.Thd;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class ProjVM extends AndroidViewModel {
    ProjRep projRep;
    private final LiveData<List<Thd>> threads;
    private final LiveData<List<Tsk>> tasks;
    private final LiveData<List<Tg>> tags;

    public ProjVM(@NonNull Application app) {
        super(app);
        projRep = new ProjRep(app);
        threads = projRep.getThreads();
        tasks = projRep.getAllTasks();
        tags = projRep.getAllTags();
    }
    // lists
    public LiveData<List<Thd>> getThreads() { return threads; }
    public LiveData<List<Tsk>> getTasks() { return tasks; }
    public LiveData<List<Tsk>> getThdTasks(int thread) { return projRep.getThreadTasks(thread); }
    public LiveData<List<Alm>> getTaskAlarms(int task) { return projRep.getTaskAlarms(task); }
    public LiveData<List<Alm>> getThdAlarms(int thread) { return projRep.getThdAlarms(thread); }
    public LiveData<Alm> getMinAlarm(int task) { return projRep.getMinAlarm(task); }
    public LiveData<List<Tg>> getTags() { return tags; }

    // IUD thread fun
    public void insThd(Thd thread) {
        CompletableFuture.runAsync(() -> projRep.insThd(thread), Executors.newSingleThreadExecutor());
    }
    public void updThd(Thd thread) {
        CompletableFuture.runAsync(() -> projRep.updThd(thread), Executors.newSingleThreadExecutor());
    }
    public void delThd(Thd thread) {
        CompletableFuture.runAsync(() -> projRep.delThd(thread), Executors.newSingleThreadExecutor());
    }

    // IUD task fun
    public void insTask(Tsk task) {
        CompletableFuture.runAsync(() -> projRep.insTask(task), Executors.newSingleThreadExecutor());
    }
    public void updTask(Tsk task) {
        CompletableFuture.runAsync(() -> projRep.updTask(task), Executors.newSingleThreadExecutor());
    }
    public void delTask(Tsk task) {
        CompletableFuture.runAsync(() -> projRep.delTask(task), Executors.newSingleThreadExecutor());
    }

    // IUD task fun
    public void insAlarm(Alm alarm) {
        CompletableFuture.runAsync(() -> projRep.insAlarm(alarm), Executors.newSingleThreadExecutor());
    }
    public void updAlarm(Alm alarm) {
        CompletableFuture.runAsync(() -> projRep.updAlarm(alarm), Executors.newSingleThreadExecutor());
    }
    public void delAlarm(Alm alarm) {
        CompletableFuture.runAsync(() -> projRep.delAlarm(alarm), Executors.newSingleThreadExecutor());
    }
}
