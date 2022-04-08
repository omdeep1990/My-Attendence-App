package com.omdeep.myattendenceapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.omdeep.myattendenceapp.Adapter.StudentDataDisplayAdapter;
import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.Database.FirebaseDb;
import com.omdeep.myattendenceapp.Interface.OnItemClickListenerFirebase;
import com.omdeep.myattendenceapp.R;
import com.omdeep.myattendenceapp.Utility.Utility;
import com.omdeep.myattendenceapp.databinding.FragmentAllStudentsBinding;

import java.util.ArrayList;
import java.util.List;

public class AllStudentsFragment extends Fragment {
    private FragmentAllStudentsBinding binding;
//    private Context context;
    private FirebaseDb firebaseDb;
    private List<AttendenceInformation> attendenceInformationList = new ArrayList<>();
    StudentDataDisplayAdapter displayAdapter;


    public AllStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: Inflate the layout for this fragment
        binding = FragmentAllStudentsBinding.inflate(inflater, container, false);
        firebaseDb = new FirebaseDb();

        displayAdapter = new StudentDataDisplayAdapter(getContext(), new OnItemClickListenerFirebase() {
            @Override
            public void onItemClick(int position, List<AttendenceInformation> attendenceInformations) {
                //TODO: Code to replace one fragment with another fragment
                Fragment fragment = new OneDataDisplayFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_all, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                //TODO: Code to send data from one fragment to another fragment using bundle method
                Bundle bundle = new Bundle();
                //TODO: Conversion from GSON to json for sending data
                bundle.putString("_student_info_", new Gson().toJson(attendenceInformations.get(position)));
                fragment.setArguments(bundle);


            }
        });
        //TODO: Setting layout for recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //TODO: Setting adapter for recycler view
        binding.recyclerView.setAdapter(displayAdapter);

        //TODO: Method called to Get data for diplay
        FetchData();

        return binding.getRoot();
    }

    //TODO: Method for getting Data for display
    public void FetchData() {
        firebaseDb.get().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AttendenceInformation attendenceInformation = dataSnapshot.getValue(AttendenceInformation.class);
                    attendenceInformation.setKey(dataSnapshot.getKey());
                    attendenceInformationList.add(attendenceInformation);
                    displayAdapter.setAttendenceInformationList(attendenceInformationList);
                    displayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utility.showLongToast(getContext(), "Something Went wrong...");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FetchData();
    }
}