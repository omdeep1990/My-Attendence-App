package com.omdeep.myattendenceapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.omdeep.myattendenceapp.AttendenceInfo.PresentAbsentInfo;
import com.omdeep.myattendenceapp.Database.PresentAbsentDb;
import com.omdeep.myattendenceapp.databinding.FragmentSubmittedAttendenceBinding;

import java.util.ArrayList;
import java.util.List;


public class SubmittedAttendence extends Fragment {
    private FragmentSubmittedAttendenceBinding binding;
    private PresentAbsentDb presentAbsentDb;
    private List<PresentAbsentInfo> presentAbsentInfoList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubmittedAttendenceBinding.inflate(inflater, container, false);
        presentAbsentDb = new PresentAbsentDb();







        return binding.getRoot();
    }
}