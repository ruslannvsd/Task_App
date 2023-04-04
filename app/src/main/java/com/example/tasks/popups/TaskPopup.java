package com.example.tasks.popups;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.R;
import com.example.tasks.adaps.AlarmAd;
import com.example.tasks.adaps.ThreadAd;
import com.example.tasks.alarm.AlarmSrv;
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.alarm.Alm;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.databinding.TaskPopupBinding;
import com.example.tasks.utils.Picker;
import com.google.android.flexbox.FlexboxLayoutManager;

public class TaskPopup {
    AlarmAd alarmAd;
    RecyclerView alarmRv;
    EditText taskEt;

    int id = 0;
    String title;
    String note = null;
    Long timeLong;
    boolean sts = true;
    int thread;

    public void taskPopup(Context ctx, Tsk task, ProjVM projVM, LifecycleOwner owner) {
        LayoutInflater inflater =
                (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.task_popup, new LinearLayout(ctx), false);
        TaskPopupBinding bnd = TaskPopupBinding.bind(popupView);
        PopupWindow window = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setElevation(2f);
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        PopupWindowCompat.setWindowLayoutType(
                window,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );

        taskEt = bnd.tTitle;
        alarmAd = new AlarmAd();
        alarmRv = bnd.alarmRv;
        taskEt.setText(task.getTitle());
        id = task.getId();
        title = task.getTitle();
        note = task.getNote();
        sts = task.getSts();
        thread = task.getThread();

        projVM.getTaskAlarms(task.getId()).observe(owner, alarms -> {
            alarmAd.setAlarms(alarms, alarm -> {
                new AlarmSrv(ctx).cancelAlarm(task, alarm);
                projVM.delAlarm(alarm);
            });
            alarmRv.setLayoutManager(new FlexboxLayoutManager(ctx));
            alarmRv.setAdapter(alarmAd);
        });

        bnd.addAlarm.setOnClickListener(view -> new Picker().getTime(System.currentTimeMillis(), ctx, time -> {
                    timeLong = time;
                    Alm alarm = new Alm(0, time, task.getId());
                    new AlarmSrv(ctx).setAlarm(task, alarm);
                    projVM.insAlarm(alarm);
                }
        ));

        bnd.save.setOnClickListener(view -> {
            title = taskEt.getText().toString();
            if (!title.matches("")) {
                Tsk newTask = new Tsk(id, title, note, sts, thread);
                projVM.updTask(newTask);
                new ThreadAd().notifyChange();
                window.dismiss();
            }
        });
        bnd.delete.setOnClickListener(view -> {
            projVM.delTask(task);
            window.dismiss();
        });
    }
}
