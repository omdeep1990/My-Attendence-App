package com.omdeep.myattendenceapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.Fragments.AllStudentsFragment;
import com.omdeep.myattendenceapp.Interface.OnItemClickListenerFirebase;
import com.omdeep.myattendenceapp.Utility.Utility;
import com.omdeep.myattendenceapp.databinding.DisplayAttendenceDataBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentDataDisplayAdapter extends RecyclerView.Adapter<StudentDataDisplayAdapter.MyViewHolder> {
    private Context context;
    private List<AttendenceInformation> attendenceInformationList = new ArrayList<>();
    private OnItemClickListenerFirebase listenerFirebase;

    public StudentDataDisplayAdapter(Context context, OnItemClickListenerFirebase listenerFirebase) {
        this.context = context;
        this.listenerFirebase = listenerFirebase;
    }

    public void setAttendenceInformationList(List<AttendenceInformation> attendenceInformationList) {
        this.attendenceInformationList = attendenceInformationList;
    }

    @NonNull
    @Override
    public StudentDataDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayAttendenceDataBinding binding = DisplayAttendenceDataBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentDataDisplayAdapter.MyViewHolder holder, int position) {
        holder.binding.yourName.setText(attendenceInformationList.get(position).getStudentFullName());
        holder.binding.joiningDate.setText(attendenceInformationList.get(position).getJoiningData());

        Bitmap bitmap = Utility.convertBase64ToBitmap(attendenceInformationList.get(position).getProfileImage());
        holder.binding.displayProfileView.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerFirebase != null){
                    listenerFirebase.onItemClick(holder.getAdapterPosition(), attendenceInformationList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendenceInformationList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        DisplayAttendenceDataBinding binding;
        public MyViewHolder(@NonNull DisplayAttendenceDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
