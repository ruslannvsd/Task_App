package com.example.tasks.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tasks.data.task.Tsk;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TimeFn {
    final static String DATE_LONG = "HH.mm / dd/MM/yy";
    final static String DATE_SHORT = "HH.mm";

    public Long stringToMillis(String timeString) throws ParseException {
        return Objects.requireNonNull(new SimpleDateFormat(DATE_LONG, Locale.UK).parse(timeString)).getTime();
    }

    public String millisToString(long timeLong) {
        String isToday = isToday(timeLong);
        Date date = new Date(timeLong);
        Format format = new SimpleDateFormat(isToday, Locale.UK);
        return format.format(date);
    }

    String isToday(long time) {
        LocalDate today = LocalDate.now();
        long start = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long end = today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;
        if (time > start && time < end) return DATE_SHORT; else return DATE_LONG;
    }

    public Long timeCheck(String timeString) {
        if (timeString != null) {
            try {
                return new TimeFn().stringToMillis(timeString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else return null;
    }
    /*public void checkingAndSetting(Tsk task, Context ctx) {
        Long now = System.currentTimeMillis();
        if (alarm != null) {
            if (alarm - now > 0) {
                AlarmSrv alarmSrv = new AlarmSrv(ctx);
                alarmSrv.cancelAlarm(tsk);
                alarmSrv.setAlarm(tsk);
                Log.d("1...", alarm.toString());
            }
            else Toast.makeText(ctx, "Time already passed", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(ctx, "Alarm Not Set", Toast.LENGTH_SHORT).show();
    }*/
}
