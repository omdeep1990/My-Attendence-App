package com.omdeep.myattendenceapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omdeep.myattendenceapp.Fragments.AllStudentsFragment;
import com.omdeep.myattendenceapp.Fragments.ReportFragment;
import com.omdeep.myattendenceapp.Fragments.TodayAttendenceFragment;

public class ViewPageAdapter extends FragmentStateAdapter {


    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new AllStudentsFragment();
            case 1:
                return new TodayAttendenceFragment();
            default:
                return new ReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
