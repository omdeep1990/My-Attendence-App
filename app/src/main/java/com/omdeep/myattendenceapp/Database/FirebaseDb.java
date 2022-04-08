package com.omdeep.myattendenceapp.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.omdeep.myattendenceapp.AttendenceInfo.AttendenceInformation;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDb {

    public DatabaseReference databaseReference;

    public FirebaseDb() {
        databaseReference = FirebaseDatabase.getInstance().getReference(AttendenceInformation.class.getSimpleName());
    }

    //TODO: Query to add items to firebase
    public Task<Void> add(AttendenceInformation attendenceInformation) {
        return databaseReference.push().setValue(attendenceInformation);


    }
    //TODO: Query to display items from firebase
    public Query get(){
        return databaseReference;
    }

    //TODO: Query to delete items from firebase
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    //TODO: Query to update items from firebase
    public Task<Void> update(String key, HashMap<String, Object> data){
        return databaseReference.child(key).updateChildren(data);
    }
}
