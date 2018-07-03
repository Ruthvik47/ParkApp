package com.abcexample.parkapp;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseClient {

    Context c;
    ListView listView;
    DatabaseReference mref;
    ArrayList<CustomList> categorylist= new ArrayList<>();
    CategoryListAdapter Listadapter;

    public FirebaseClient(Context c, ListView listView, String DB_URL) {
        this.c = c;
        this.listView = listView;
        mref=FirebaseDatabase.getInstance().getReference(DB_URL);
    }

    public void savedata(String name, String address, String url, String TwoTotal, String Twoleft, String FourTotal, String Fourleft){
        String CategoryID=mref.push().getKey();
        CustomList m=new CustomList();
        m.setMallid(CategoryID);
        m.setName(name);
        m.setAddress(address);
        m.setUrl(url);
        m.setFourWheelersleft(Fourleft);
        m.setFourWheelersTotal(FourTotal);
        m.setTwoWheelersleft(Twoleft);
        m.setTwoWheelersTotal(TwoTotal);
        mref.child(CategoryID).setValue(m);

    }

    public void referencedata() {
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                categorylist.clear();
                for(DataSnapshot categorysnapshot : dataSnapshot.getChildren()){
                    CustomList m= new CustomList();
                    m.setName(categorysnapshot.getValue(CustomList.class).getName());
                    m.setAddress(categorysnapshot.getValue(CustomList.class).getAddress());
                    m.setUrl(categorysnapshot.getValue(CustomList.class).getUrl());

                    categorylist.add(m);
                    if(categorylist.size()>0){
                        Listadapter =new CategoryListAdapter(c, categorylist);
                        listView.setAdapter((ListAdapter) Listadapter);
                    }
                    else{
                        Toast.makeText(c, "No Data to Show", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}



