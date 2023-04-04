package com.example.tasks.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.tasks.data.alarm.Alm;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.utils.Cons;
import com.example.tasks.utils.TimeFn;

public class AlarmSrv {
    Context ctx;
    public AlarmManager alarmMng;

    public AlarmSrv(Context ctx) {
        this.ctx = ctx;
        alarmMng = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Tsk task, Alm alarm) {
        alarmMng = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendInt = getPendInt(task, alarm);
        alarmMng.setExact(AlarmManager.RTC_WAKEUP, alarm.getTime(), pendInt);
        Toast.makeText(ctx, "Alarm Set", Toast.LENGTH_LONG).show();
    }

    public void cancelAlarm(Tsk task, Alm alarm) {
        alarmMng = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent oldPendInt = getPendInt(task, alarm);
        alarmMng.cancel(oldPendInt);
        Toast.makeText(ctx, "Alarm Canceled", Toast.LENGTH_LONG).show();
    }

    private PendingIntent getPendInt(Tsk task, Alm alarm) {
        Intent intent = new Intent(ctx, AlarmRec.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(Cons.SET_EXACT_ALARM);
        intent.putExtra(Cons.ALARM_ID, task.getId());
        intent.putExtra(Cons.TITLE, task.getTitle());
        intent.putExtra(Cons.TIME_STR, new TimeFn().millisToString(alarm.getTime()));
        return PendingIntent.getBroadcast(
                ctx, alarm.getId() + 666, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
