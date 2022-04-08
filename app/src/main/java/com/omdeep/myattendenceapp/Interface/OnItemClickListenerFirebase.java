package com.omdeep.myattendenceapp.Interface;

import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;

import java.util.List;

public interface OnItemClickListenerFirebase {
//    void onItemClick(int position, List<StudentInfo> studentInfo);

    //TODO: In interface by default all methods are public, they need to be changed if required.
    void onItemClick(int position, List<AttendenceInformation> attendenceInformations);
}
