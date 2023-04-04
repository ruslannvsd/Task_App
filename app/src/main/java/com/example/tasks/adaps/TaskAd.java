package com.example.tasks.adaps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.R;
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.databinding.RvTaskBinding;
import com.example.tasks.popups.TaskPopup;

import java.util.List;

public class TaskAd extends RecyclerView.Adapter<TaskAd.TaskVH> {
    List<Tsk> tasks = List.of();
    Context ctx;
    ProjVM projVM;
    LifecycleOwner owner;
    int selected;
    public static class TaskVH extends RecyclerView.ViewHolder {
        private final RvTaskBinding bnd;
        public TaskVH(RvTaskBinding bnd) {
            super(bnd.getRoot());
            this.bnd = bnd;
        }
    }
    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvTaskBinding bnd = RvTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskVH(bnd);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH h, int p) {
        Tsk task = tasks.get(p);
        CardView card = h.bnd.tCard;
        h.bnd.task.setText(task.getTitle());
        card.setOnClickListener(view -> {

            new TaskPopup().taskPopup(ctx, task, projVM, owner);
        });
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    public void setTasks(List<Tsk> tasks, Context ctx, ProjVM projVM, LifecycleOwner owner, int selected) {
        this.tasks = tasks;
        this.ctx = ctx;
        this.projVM = projVM;
        this.owner = owner;
        this.selected = selected;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void notifyChange() {
        notifyDataSetChanged();
    }
}
