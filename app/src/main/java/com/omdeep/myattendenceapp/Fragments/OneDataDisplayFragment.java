package com.omdeep.myattendenceapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.omdeep.myattendenceapp.Activity.AddAttendenceActivity;
import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;
import com.omdeep.myattendenceapp.Database.FirebaseDb;
import com.omdeep.myattendenceapp.R;
import com.omdeep.myattendenceapp.Utility.Utility;
import com.omdeep.myattendenceapp.databinding.FragmentOneDataDisplayBinding;
import com.omdeep.myattendenceapp.databinding.UpdateStudentDataBinding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class OneDataDisplayFragment extends Fragment {
    private FragmentOneDataDisplayBinding binding;
    private AttendenceInformation attendenceInformation;
    private FirebaseDb firebase;
    private String imageInBase64;
    private boolean isUpdate = false;
    private Calendar calendar;
    private int DAYOFMONTH, MONTH, YEAR;
    String FinalDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOneDataDisplayBinding.inflate(inflater, container, false);

        firebase  = new FirebaseDb();
        calendar = Calendar.getInstance();
        DAYOFMONTH = calendar.get(Calendar.DAY_OF_MONTH);
        MONTH = calendar.get(Calendar.MONTH);
        YEAR = calendar.get(Calendar.YEAR);
        //TODO: Receiving data from another fragment using GSON method
        String name= getArguments().getString("_student_info_");
        attendenceInformation = new Gson().fromJson(name, AttendenceInformation.class);

        //TODO: Setting data onClick for a particular data
        binding.joiningDate.setText("Joining Data: " +attendenceInformation.getJoiningData());
        binding.fullName.setText("Full Name: " +attendenceInformation.getStudentFullName());
        binding.mobileNumber.setText("Mobile No.: " +attendenceInformation.getMobileNumber());
        binding.EmailId.setText("Email Id: " +attendenceInformation.getEmailId());
        binding.highestQualification.setText("Highest Qualification: " +attendenceInformation.getHighestQualification());
        binding.communicationAddress.setText("Communication Address: " +attendenceInformation.getCommunicationbAddress());
        Bitmap bitmap = Utility.convertBase64ToBitmap(attendenceInformation.getProfileImage());
        binding.profileImage.setImageBitmap(bitmap);

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUpdate = true;
                UpdateStudentDataBinding binding1;
                binding1 = UpdateStudentDataBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(binding1.getRoot());

                binding1.joiningDate.setText(attendenceInformation.getJoiningData());
                binding1.fullName.setText(attendenceInformation.getStudentFullName());
                binding1.mobileNumber.setText(attendenceInformation.getMobileNumber());
                binding1.EmailId.setText(attendenceInformation.getEmailId());
                binding1.highestQualification.setText(attendenceInformation.getHighestQualification());
                binding1.communicationAddress.setText(attendenceInformation.getCommunicationbAddress());
                Bitmap bitmap = Utility.convertBase64ToBitmap(attendenceInformation.getProfileImage());
                binding1.profileImage.setImageBitmap(bitmap);

                binding1.joiningDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                FinalDate = date;
                                binding1.joiningDate.setText(date);
                                Log.d("DATE:", dayOfMonth+"/"+month+"/"+year);
                            }
                        }, YEAR, MONTH, DAYOFMONTH);
                        datePickerDialog.show();
                    }
                });

                binding1.profileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                            callCamera();
                        }else {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);

                        }
                    }
                });

                binding1.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("communicationbAddress", binding1.communicationAddress.getText().toString());
                        hashMap.put("emailId", binding1.EmailId.getText().toString());

                        hashMap.put("highestQualification", binding1.highestQualification.getText().toString());
                        hashMap.put("joiningData", FinalDate);
                        hashMap.put("mobileNumber", binding1.mobileNumber.getText().toString());
                        hashMap.put("studentFullName", binding1.fullName.getText().toString());
                        hashMap.put("profileImage", imageInBase64);



                        firebase.update(attendenceInformation.getKey(), hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Utility.showLongToast(getContext(), "Updated....");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utility.showLongToast(getContext(), e.getMessage());
                            }
                        });
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Fragment removal complete code
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                for (Fragment fragment : fragmentManager.getFragments()) {
                FragmentTransaction fragmentTransactionRemove = fragmentManager.beginTransaction();
                fragmentTransactionRemove.remove(fragment);
                fragmentTransactionRemove.addToBackStack(null);
                fragmentTransactionRemove.commit();
            }}
        });


        return binding.getRoot();
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
                Utility.showLongToast(getContext(), "Permission Denied!");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageInBase64 = Utility.convertBitmaptoBase64(bitmap);
            if (isUpdate){
                binding.profileImage.setImageBitmap(bitmap);
            }else {
                binding.profileImage.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.delete_data, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Delete) {
            firebase.remove(attendenceInformation.getKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Utility.showLongToast(getContext(), "Data deleted successfully");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Utility.showLongToast(getContext(), e.getMessage());
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
