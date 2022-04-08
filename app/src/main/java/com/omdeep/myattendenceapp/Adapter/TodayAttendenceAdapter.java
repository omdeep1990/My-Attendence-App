package com.omdeep.myattendenceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.databinding.DisplayAttendenceDataBinding;
import com.omdeep.myattendenceapp.databinding.TodayAttendenceDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class TodayAttendenceAdapter extends RecyclerView.Adapter<TodayAttendenceAdapter.MyViewHolder> {
    private Context context;
    private List<AttendenceInformation> attendenceInformationList = new ArrayList<>();

    public TodayAttendenceAdapter(Context context) {
        this.context = context;
    }

    public void setAttendenceInformationList(List<AttendenceInformation> attendenceInformationList) {
        this.attendenceInformationList = attendenceInformationList;
    }

    @NonNull
    @Override
    public TodayAttendenceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodayAttendenceDisplayBinding binding = TodayAttendenceDisplayBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAttendenceAdapter.MyViewHolder holder, int position) {
        holder.binding.name.setText(attendenceInformationList.get(position).getStudentFullName());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listenerFirebase != null){
//                    listenerFirebase.onItemClick(holder.getAdapterPosition(), attendenceInformationList);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return attendenceInformationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TodayAttendenceDisplayBinding binding;
        public MyViewHolder(@NonNull TodayAttendenceDisplayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
