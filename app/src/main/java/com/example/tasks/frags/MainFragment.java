package com.example.tasks.frags;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tasks.adaps.AlarmAd;
import com.example.tasks.adaps.TaskAd;
import com.example.tasks.adaps.ThreadAd;
import com.example.tasks.alarm.AlarmSrv;
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.alarm.Alm;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.data.thread.Thd;
import com.example.tasks.databinding.FragmentMainBinding;
import com.example.tasks.popups.ThdAddPopup;
import com.example.tasks.utils.Cons;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class MainFragment extends Fragment {
    FragmentMainBinding bnd;
    ProjVM projVM;
    LifecycleOwner owner;
    Context ctx;

    // thread block
    RecyclerView threadRv;
    ThreadAd threadAd;

    // tasks block
    RecyclerView tasksRv;
    TaskAd taskAd;

    // alarms block
    AlarmAd alarmAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bnd = FragmentMainBinding.inflate(inflater, container, false);
        initial();
        bnd.threadBtn.setOnClickListener(view -> new ThdAddPopup().thdAddPopup(ctx, (thread, selected) -> {
            addingThd(thread);
            threadAd.setHighlighted(selected);
        }, threadAd));
        bnd.taskBtn.setOnClickListener(view -> {
            projVM.getTasks().observe(owner, this::setTasks);
            threadAd.setHighlighted(Cons.MINUS_ONE);
            threadAd.notifyChange();
        });

        // making thread block
        projVM.getThreads().observe(owner, this::setThreads);
        allTasksSet();

        return bnd.getRoot();
    }
    // recyclerview population functions
    void allTasksSet() {
        projVM.getTasks().observe(owner, this::setTasks);
    }

    void setThreads(List<Thd> threads) {
        threadAd.setThreads(threads, ctx, projVM, this::renamingThd, this::deletingThd, this::setThreadTasks, task -> {
            projVM.insTask(task);
            projVM.getThdTasks(task.getThread()).observe(owner, this::setTasks);
        });
        threadRv.setAdapter(threadAd);
        threadRv.setLayoutManager(new FlexboxLayoutManager(ctx));
    } // all threads
    void setTasks(List<Tsk> tasks) {
        taskAd.setTasks(tasks, ctx, projVM, owner, Cons.MINUS_ONE);
        tasksRv.setAdapter(taskAd);
        tasksRv.setLayoutManager(new FlexboxLayoutManager(ctx));
    } // all tasks
    void setThreadTasks(Thd thread) {
        projVM.getThdTasks(thread.id).observe(owner, this::setTasks);
    } // for passing to thread adapter for populating with attached tasks when a thread is pressed

    // thread functions to pass
    void addingThd(Thd thread) {
        projVM.insThd(thread);
        setTasks(List.of());
    }
    void renamingThd(Thd thread) {
        projVM.updThd(thread);
    }
    void deletingThd(Thd thread, int pos) {
        projVM.delThd(thread);
        threadAd.notifyItemRemoved(pos);
        projVM.getThdTasks(thread.id).observe(owner, tasks -> {
            for (Tsk task : tasks) {
                projVM.getTaskAlarms(task.getId()).observe(owner, alarms -> {
                    AlarmSrv alarmSrv = new AlarmSrv(ctx);
                    for (Alm alarm : alarms) {
                        alarmSrv.cancelAlarm(task, alarm);
                    }
                });
            }
            Toast.makeText(ctx, "All alarms are canceled", Toast.LENGTH_SHORT).show();
        });
        allTasksSet();
    }

    void initial() {
        projVM = new ViewModelProvider(this).get(ProjVM.class);
        owner = getViewLifecycleOwner();
        ctx = requireContext();
        threadRv = bnd.threadRv;
        threadAd = new ThreadAd();

        tasksRv = bnd.taskRv;
        taskAd = new TaskAd();

        alarmAd = new AlarmAd();
    }
}