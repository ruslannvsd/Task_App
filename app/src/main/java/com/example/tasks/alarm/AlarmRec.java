package com.example.tasks.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.tasks.MainActivity;
import com.example.tasks.R;
import com.example.tasks.utils.Cons;

import java.util.Objects;

public class AlarmRec extends BroadcastReceiver {
    NotificationManager nMng;
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (Objects.equals(intent.getAction(), Cons.SET_EXACT_ALARM)) {
            int id = intent.getIntExtra(Cons.ALARM_ID, -1);
            String title = intent.getStringExtra(Cons.TITLE);
            String timeStr = intent.getStringExtra(Cons.TIME_STR);
            nMng = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            channel();
            int reqCode = id + 666;
            Intent launchIntent = new Intent(ctx, MainActivity.class);
            PendingIntent pendInt
                    = PendingIntent.getActivity(ctx, reqCode, launchIntent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, Cons.CHN_ID)
                    .setSmallIcon(R.drawable.alarm)
                    .setContentTitle(title)
                    .setContentText(timeStr)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendInt)
                    .setAutoCancel(true)
                    .setGroup(Cons.CHN_ID)
                    .setGroupSummary(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
            nMng.notify(reqCode, builder.build());
        }
    }
    void channel() {
        String name = "Tasks App";
        String body = "Notifications";
        int imp = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel nChn = new NotificationChannel(Cons.CHN_ID, name, imp);
        nChn.setDescription(body);
        nChn.setShowBadge(true);
        nChn.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        nMng.createNotificationChannel(nChn);
    }
}
