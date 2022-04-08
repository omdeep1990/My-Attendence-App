package com.omdeep.myattendenceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omdeep.myattendenceapp.AttendenceInfo.PresentAbsentInfo;
import com.omdeep.myattendenceapp.Database.PresentAbsentDb;
import com.omdeep.myattendenceapp.databinding.DisplayAttendenceDataBinding;
import com.omdeep.myattendenceapp.databinding.DisplayPresentabsentInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class SubmittedAttendenceAdapter extends RecyclerView.Adapter<SubmittedAttendenceAdapter.MyViewHolder> {
    private Context context;
    private List<PresentAbsentInfo> presentAbsentInfoList = new ArrayList<>();

    public SubmittedAttendenceAdapter(Context context) {
        this.context = context;
    }

    public void setPresentAbsentInfoList(List<PresentAbsentInfo> presentAbsentInfoList) {
        this.presentAbsentInfoList = presentAbsentInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayPresentabsentInfoBinding binding = DisplayPresentabsentInfoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return presentAbsentInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        DisplayPresentabsentInfoBinding binding;
        public MyViewHolder(@NonNull DisplayPresentabsentInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
