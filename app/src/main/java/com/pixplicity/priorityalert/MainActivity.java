package com.pixplicity.priorityalert;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtTrigger = (Button) findViewById(R.id.bt_trigger);
        mBtTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManagerCompat nm = NotificationManagerCompat.from(MainActivity.this);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("Test")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .build();
                nm.notify(1001, notification);
            }
        });
    }

}
