package com.omdeep.myattendenceapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.Database.FirebaseDb;
import com.omdeep.myattendenceapp.Utility.Utility;
import com.omdeep.myattendenceapp.databinding.ActivityAddAttendenceBinding;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddAttendenceActivity extends AppCompatActivity {
    private ActivityAddAttendenceBinding binding;
    private Calendar calendar;
    private int DAYOFMONTH, MONTH, YEAR;
    String FinalDate;
    private FirebaseDb firebase;
    private String imageInBase64;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        DAYOFMONTH = calendar.get(Calendar.DAY_OF_MONTH);
        MONTH = calendar.get(Calendar.MONTH);
        YEAR = calendar.get(Calendar.YEAR);

        firebase = new FirebaseDb();

        binding.joiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAttendenceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        FinalDate = date;
                        binding.joiningDate.setText(date);
//                        Log.d("DATE:", dayOfMonth+"/"+month+"/"+year);
                    }
                }, YEAR, MONTH, DAYOFMONTH);
                datePickerDialog.show();
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddAttendenceActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    callCamera();
                }else {
                    ActivityCompat.requestPermissions(AddAttendenceActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        });
binding.submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AttendenceInformation attendenceInformation = new AttendenceInformation();
        attendenceInformation.setJoiningData(FinalDate);
//        attendenceInformation.setJoiningData(binding.joiningDate.getText().toString());
        attendenceInformation.setStudentFullName(binding.fullName.getText().toString());
        attendenceInformation.setMobileNumber(binding.mobileNumber.getText().toString());
        attendenceInformation.setEmailId(binding.EmailId.getText().toString());
        attendenceInformation.setHighestQualification(binding.highestQualification.getText().toString());
        attendenceInformation.setCommunicationbAddress(binding.communicationAddress.getText().toString());
        attendenceInformation.setProfileImage(imageInBase64);

        firebase.add(attendenceInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Utility.showLongToast(AddAttendenceActivity.this, "Successfully Inserted...");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utility.showLongToast(AddAttendenceActivity.this, e.getMessage());
            }
        });
    }
});

binding.cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});

    }
    private void callCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // TODO: DO UR JOB
                callCamera();
            } else {
                Utility.showLongToast(getApplicationContext(), "Permission Denied!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageInBase64 = Utility.convertBitmaptoBase64(bitmap);
//            if (isUpdate){
//                binding.profileImage.setImageBitmap(bitmap);
//            }

                binding.profileImage.setImageBitmap(bitmap);

        }
    }

}