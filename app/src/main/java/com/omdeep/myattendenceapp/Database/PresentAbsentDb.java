package com.omdeep.myattendenceapp.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.omdeep.myattendenceapp.AttendenceInfo.PresentAbsentInfo;

import java.util.HashMap;

public class PresentAbsentDb {

    public DatabaseReference reference;

    public PresentAbsentDb() {
        reference = FirebaseDatabase.getInstance().getReference(PresentAbsentInfo.class.getSimpleName());
    }


        //TODO: Query to add items to firebase
        public  Task<Void> add1(PresentAbsentInfo presentAbsentInfo) {
        return reference.child("PresentAbsentInfo").push().setValue(presentAbsentInfo);
        }
    //TODO: Query to display items from firebase
    public Query get1(){
        return reference.orderByKey();
    }

    //TODO: Query to delete items from firebase
    public Task<Void> remove1(String key){
        return reference.child(key).removeValue();
    }

    //TODO: Query to update items from firebase
    public Task<Void> update1(String key, HashMap<String, Object> data){
        return reference.child(key).updateChildren(data);
    }
    }

