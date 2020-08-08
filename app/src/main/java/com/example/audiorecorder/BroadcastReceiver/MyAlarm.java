package com.example.audiorecorder.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //do job here
        Toast.makeText(context, "Alarm is ring", Toast.LENGTH_SHORT).show();
    }
}
