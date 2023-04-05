package com.example.tasks.adaps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
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
        projVM.getMinAlarm(task.getId()).observe(owner, alarm -> {
            if (alarm != null) taskColor(card, alarm.getTime());
            else taskColor(card, null);
        });

        card.setOnClickListener(view -> new TaskPopup().taskPopup(ctx, task, projVM, owner));
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

    void taskColor(CardView card, Long time) {
        long now = System.currentTimeMillis();
        // no alarm tasks
        if (time == null ) card.getBackground().setTint(ctx.getColor(R.color.no_alarm));
        else {
            long diff = time - now;
            if (diff < 0) card.getBackground().setTint(ctx.getColor(R.color.card_1));
            // tasks due 3 hours = 10800000 milliseconds
            if (diff >= 0L && diff < 10800000) card.getBackground().setTint(ctx.getColor(R.color.card_2));
            // tasks due 12 hours = 43200000 milliseconds
            if (diff >= 10800001 && diff < 43200000) card.getBackground().setTint(ctx.getColor(R.color.card_3));
            // tasks due 24 hours = 86400000 milliseconds
            if (diff >= 43200001 && diff < 86400000) card.getBackground().setTint(ctx.getColor(R.color.card_4));
            // tasks due 2 days = 172800000 milliseconds
            if (diff >= 86400001 && diff < 172800000) card.getBackground().setTint(ctx.getColor(R.color.card_5));
            // tasks due 4 days = 345600000 milliseconds
            if (diff >= 172800001 && diff < 345600000) card.getBackground().setTint(ctx.getColor(R.color.card_6));
            // tasks due 7 days = 604800000 milliseconds
            if (diff >= 345600001 && diff < 604800000) card.getBackground().setTint(ctx.getColor(R.color.card_7));
            // tasks more than 7 days
            if (diff >= 604800001) card.getBackground().setTint(ctx.getColor(R.color.card_8));
        }
    }
}
