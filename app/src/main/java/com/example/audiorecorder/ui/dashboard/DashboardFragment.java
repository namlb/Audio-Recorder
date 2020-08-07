package com.example.audiorecorder.ui.dashboard;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.audiorecorder.R;
import com.example.audiorecorder.adapter.FileAdapter;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public static final String ABSOLUTE_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Records";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ListView listView = root.findViewById(R.id.list);
        File directory = new File(ABSOLUTE_FOLDER);
        File[] files = directory.listFiles();
        List<File> fileList = Arrays.asList(files);

        FileAdapter fileAdapter = new FileAdapter(this.getContext(), R.layout.file_item, fileList);
        listView.setAdapter(fileAdapter);
        return root;
    }
}