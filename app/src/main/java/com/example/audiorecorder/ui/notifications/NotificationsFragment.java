package com.example.audiorecorder.ui.notifications;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.audiorecorder.BroadcastReceiver.MyAlarm;
import com.example.audiorecorder.R;

import java.util.Calendar;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final TimePicker timePicker = (TimePicker) root.findViewById(R.id.timePicker);
        Button buttonAlarm = root.findViewById(R.id.button_alarm);
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }

                AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                //creating a new intent specifying the broadcast receiver
                Intent i = new Intent(root.getContext(), MyAlarm.class);

                //creating a pending intent using the intent
                PendingIntent pi = PendingIntent.getBroadcast(root.getContext(), 0, i, 0);

                //setting the repeating alarm that will be fired every day
                am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.RTC, pi);
                Toast.makeText(root.getContext(), "Alarm is set", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

}