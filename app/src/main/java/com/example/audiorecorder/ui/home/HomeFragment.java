package com.example.audiorecorder.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.audiorecorder.R;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button play, stop, record;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ORDER = "order";
    public static final String ABSOLUTE_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Records";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        play = root.findViewById(R.id.play);
        stop = root.findViewById(R.id.stop);
        record = root.findViewById(R.id.record);
        stop.setEnabled(false);
        play.setEnabled(false);

        File directory = new File(ABSOLUTE_FOLDER);
        if (! directory.exists()){
            directory.mkdir();
        }

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentOrder = getOrderRecord();
                String fileName = "/recording("+ currentOrder.toString() +").3gp";
                outputFile = ABSOLUTE_FOLDER + fileName;
                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(root.getContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                increaseOrderRecord();
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(root.getContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(root.getContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    public void saveOrderRecord(int currentOrder) {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ORDER, currentOrder);
        editor.apply();
    }

    public Integer getOrderRecord() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Integer order = sharedPref.getInt(ORDER, 0);
        return order;
    }

    public void increaseOrderRecord() {
        Integer currentOrder = getOrderRecord();
        currentOrder++;
        saveOrderRecord(currentOrder);
    }
}