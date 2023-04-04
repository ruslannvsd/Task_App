package com.example.tasks.adaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.data.alarm.Alm;
import com.example.tasks.databinding.RvAlarmBinding;
import com.example.tasks.interfaces.OnAlarmDeleting;
import com.example.tasks.utils.TimeFn;

import java.util.List;

public class AlarmAd extends RecyclerView.Adapter<AlarmAd.AlarmVH> {
    List<Alm> alarms = List.of();
    OnAlarmDeleting alarmDeleting;
    public static class AlarmVH extends RecyclerView.ViewHolder {
        private final RvAlarmBinding bnd;
        public AlarmVH(RvAlarmBinding bnd) {
            super(bnd.getRoot());
            this.bnd = bnd;
        }
    }
    @NonNull
    @Override
    public AlarmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvAlarmBinding bnd = RvAlarmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AlarmVH(bnd);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmVH h, int p) {
        Alm alarm = alarms.get(p);
        CardView card = h.bnd.aCard;
        String time = new TimeFn().millisToString(alarm.getTime());
        h.bnd.alarm.setText(time);
        card.setOnLongClickListener(view -> {
            alarmDeleting.onAlarmDeleting(alarm);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alm> alarms, OnAlarmDeleting alarmDeleting) {
        this.alarms = alarms;
        this.alarmDeleting = alarmDeleting;
    }
}
