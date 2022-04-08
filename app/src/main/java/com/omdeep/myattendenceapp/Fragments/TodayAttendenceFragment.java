package com.omdeep.myattendenceapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.omdeep.myattendenceapp.Activity.AddAttendenceActivity;
import com.omdeep.myattendenceapp.Adapter.TodayAttendenceAdapter;
import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.AttendenceInfo.PresentAbsentInfo;
import com.omdeep.myattendenceapp.Database.FirebaseDb;
import com.omdeep.myattendenceapp.Database.PresentAbsentDb;
import com.omdeep.myattendenceapp.Interface.OnItemClickListenerFirebase;
import com.omdeep.myattendenceapp.R;
import com.omdeep.myattendenceapp.Utility.Utility;
import com.omdeep.myattendenceapp.databinding.FragmentAllStudentsBinding;
import com.omdeep.myattendenceapp.databinding.FragmentTodayAttendenceBinding;
import com.omdeep.myattendenceapp.databinding.TodayAttendenceDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class TodayAttendenceFragment extends Fragment {
    private FragmentTodayAttendenceBinding binding;
    private FirebaseDb firebaseDb;
    private PresentAbsentDb presentAbsentDb;
    private List<AttendenceInformation> attendenceInformationList = new ArrayList<>();
    TodayAttendenceAdapter attendenceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//TODO: Binding procedure in Fragment
        binding = FragmentTodayAttendenceBinding.inflate(inflater, container, false);
        firebaseDb = new FirebaseDb();
        presentAbsentDb = new PresentAbsentDb();

        attendenceAdapter = new TodayAttendenceAdapter(getContext());
        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView1.setAdapter(attendenceAdapter);

        firebaseDb.get().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AttendenceInformation attendenceInformation = dataSnapshot.getValue(AttendenceInformation.class);
                    attendenceInformationList.add(attendenceInformation);
                    attendenceAdapter.setAttendenceInformationList(attendenceInformationList);
                    attendenceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utility.showLongToast(getContext(), "Something Went wrong...");
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PresentAbsentInfo presentAbsentInfo = new PresentAbsentInfo();
                TodayAttendenceDisplayBinding binding1;
                binding1 = TodayAttendenceDisplayBinding.inflate(getLayoutInflater());
                presentAbsentInfo.setPresent(binding1.present.getText().toString());
                presentAbsentInfo.setAbsent(binding1.absent.getText().toString());
//                presentAbsentInfo.setPresent(binding.);

                presentAbsentDb.add1(presentAbsentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Utility.showLongToast(getContext(), "Successfully Inserted...");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utility.showLongToast(getContext(), e.getMessage());
                    }
                });

                //TODO: Code to replace one fragment with another fragment
                Fragment fragment = new SubmittedAttendence();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.today_attendence, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}