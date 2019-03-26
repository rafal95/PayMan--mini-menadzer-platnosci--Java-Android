package com.example.admin.payman;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "exampleServiceChannel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        replaceFragment(new PaymentList());

        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("inputExtra", "Text serwisu");

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Przypomnienie platnosci",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void newPayment(View view){
        replaceFragment(new NewPayment());
    }

    public void paymentList(View view){
        replaceFragment(new PaymentList());
    }

    private void replaceFragment(Fragment payment) {
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.frame, payment).commit();
    }

}
