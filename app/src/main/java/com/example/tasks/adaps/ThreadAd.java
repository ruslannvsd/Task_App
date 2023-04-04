package com.example.tasks.adaps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.R;
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.thread.Thd;
import com.example.tasks.databinding.RvThreadBinding;
import com.example.tasks.interfaces.OnThdAction;
import com.example.tasks.interfaces.OnThdPosChange;
import com.example.tasks.menus.ThdMenu;

import java.util.List;

public class ThreadAd extends RecyclerView.Adapter<ThreadAd.ThreadVH> {
    public List<Thd> threads = List.of();
    int newSelected = -1;
    int oldSelected = -1;
    Context ctx;
    ProjVM projVM;
    OnThdAction thdRename;
    OnThdPosChange thdDelete;
    OnThdAction settingTasks;

    public static class ThreadVH extends RecyclerView.ViewHolder {
        private final RvThreadBinding bnd;
        public ThreadVH(RvThreadBinding bnd) {
            super(bnd.getRoot());
            this.bnd = bnd;
        }
    }

    @NonNull
    @Override
    public ThreadVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RvThreadBinding bnd = RvThreadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Log.d("ThreadAd", "NewThdVH created");
            return new ThreadVH(bnd);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThreadVH h, final int p) {
        Thd thread = threads.get(p);
        CardView card = h.bnd.thdCard;
        int pos = h.getAbsoluteAdapterPosition();
        setCardColor(pos, card);
        TextView thdTx = h.bnd.thread;
        thdTx.setText(thread.getTitle());
        card.setOnClickListener(view -> {
            if (newSelected != pos) {
                oldSelected = newSelected;
                newSelected = pos;
                settingTasks.onThdAction(thread);
                notifyItemChanged(oldSelected);
                notifyItemChanged(newSelected);
            } else {
                new ThdMenu().thdMenu(ctx, thread, projVM, card, thdRename, thdDelete, newSelected);
            }
        });
    }

    @Override
    public int getItemCount() { return threads.size(); }

    public void setThreads(List<Thd> threads, Context ctx, ProjVM projVM, OnThdAction thdRename, OnThdPosChange thdDelete, OnThdAction settingTasks) {
        this.threads = threads;
        this.ctx = ctx;
        this.projVM = projVM;
        this.thdRename = thdRename;
        this.thdDelete = thdDelete;
        this.settingTasks = settingTasks;
        notifyChange();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void notifyChange() {
        notifyDataSetChanged();
    }

    void setCardColor(int sel, CardView c) {
        if (sel == newSelected) { c.getBackground().setTint(ctx.getColor(R.color.dark_4)); }
        else c.getBackground().setTint(ctx.getColor(R.color.gray_1));
    }
    public void setHighlighted(int pos) {
        oldSelected = newSelected;
        newSelected = pos;
        Log.d("SetH: selected", oldSelected + " old / new " + newSelected);
        notifyItemChanged(oldSelected);
        notifyItemChanged(newSelected);
    }
}