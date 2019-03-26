package com.example.admin.payman;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.admin.payman.manager.ManagerImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.admin.payman.MainActivity.CHANNEL_ID;


public class MyService extends Service {

    private Cursor cursor;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        ManagerImpl manager = new ManagerImpl(this);
        cursor = manager.getPaymentCursor();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        while(cursor.moveToNext()) {
            String name = cursor.getString(3);
            if(name.equals(formattedDate)) {
                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,
                        0, notificationIntent, 0);

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(name)
                        .setContentText(input).setSmallIcon(R.drawable.ic_stat_account_balance_wallet)
                        .setContentIntent(pendingIntent)
                        .build();

                startForeground(1, notification);
            }

        }
        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}