package com.example.tasks.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.tasks.interfaces.OnTimeSelected;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;

public class Picker implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private OnTimeSelected onTimeSelected;
    Context ctx;

    int nowYear, nowMon, nowDay, nowHour, nowMin, selYear, selMon, selDay, selHour;
    String selMin;

    public void getTime(Long time, Context ctx, OnTimeSelected onTimeSelected) {
        this.onTimeSelected = onTimeSelected;
        getTime(time);
        this.ctx = ctx;
        new DatePickerDialog(ctx, this, nowYear, nowMon, nowDay).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        selYear = year;
        selMon = month + 1;
        selDay = day;
        new TimePickerDialog(ctx, this, nowHour, nowMin, true).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        selHour = hour;
        if (min < 10) selMin = "0" + min; else selMin = String.valueOf(min);
        String alarmString = selHour + "." + selMin + " / " + selDay + "/" + selMon + "/" + selYear;
        long time = 0L;
        try {
            time = new TimeFn().stringToMillis(alarmString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        onTimeSelected.onTimeSelected(time);
    }

    void getTime(Long time) {
        Calendar cal = Calendar.getInstance();
        if (time != null) cal.setTimeInMillis(time);
        nowYear = cal.get(Calendar.YEAR);
        nowMon = cal.get(Calendar.MONTH);
        nowDay = cal.get(Calendar.DAY_OF_MONTH);
        nowHour = cal.get(Calendar.HOUR_OF_DAY);
        nowMin = cal.get(Calendar.MINUTE);
    }
}
