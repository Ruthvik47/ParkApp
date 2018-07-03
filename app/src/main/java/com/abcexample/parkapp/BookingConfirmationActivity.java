package com.abcexample.parkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookingConfirmationActivity extends AppCompatActivity {

    private TextView no_ofslotsbooked, categoryname, categoryaddress;
    private ImageView categoryimage;
    private DatabaseReference categoryref;
    private FirebaseAuth categoryauth;
    private FirebaseUser categoryuser;
    private DatabaseReference accountref;
    private String CategoryName;
    private String CategoryAddress;
    private String CategoryUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        no_ofslotsbooked=(TextView) findViewById(R.id.numberofslotsbooked);
        categoryname=(TextView)findViewById(R.id.confirmcategory);
        categoryaddress=(TextView)findViewById(R.id.confirmaddress);
        categoryimage=(ImageView)findViewById(R.id.categoryimageview);

        Intent i=getIntent();
        final String CategoryID=i.getStringExtra("Categoryid");
        final String Category=i.getStringExtra("Category");
        final int slots=i.getIntExtra("Slots", 0);
        final int TwoWheeler=i.getIntExtra("TwoWheeler", 0);
        final int FourWheeler=i.getIntExtra("FourWheeler", 0);

        categoryref= FirebaseDatabase.getInstance().getReference(Category).child(CategoryID);
        categoryauth=FirebaseAuth.getInstance();
        categoryuser=categoryauth.getInstance().getCurrentUser();
        accountref=FirebaseDatabase.getInstance().getReference("Accounts").child(categoryuser.getUid());

        categoryref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Picasso.with(getApplicationContext())
                        .load(dataSnapshot.getValue(CustomList.class).getUrl())
                        .placeholder(R.drawable.placeholder)
                        .into(categoryimage);

                if(TwoWheeler==1&&FourWheeler==0) {
                    int Slots_left = Integer.parseInt(dataSnapshot.getValue(CustomList.class).getTwoWheelersleft());
                    int new_Slots_left = Slots_left-slots;
                    String new_slots= Integer.toString(new_Slots_left);
                    categoryref.child("twoWheelersleft").setValue(new_slots);
                    final String Slotsnew= Integer.toString(slots);
                    no_ofslotsbooked.setText(Slotsnew +" Two Wheeler Slots");
                    categoryname.setText(dataSnapshot.getValue(CustomList.class).getName());
                    categoryaddress.setText(dataSnapshot.getValue(CustomList.class).getAddress());

                    CategoryName=dataSnapshot.getValue(CustomList.class).getName();
                    CategoryAddress=dataSnapshot.getValue(CustomList.class).getAddress();
                    CategoryUrl=dataSnapshot.getValue(CustomList.class).getUrl();

                    accountref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            accountref.child("SlotsBooked").setValue(Slotsnew +" Two Wheeler/s Slots");
                            accountref.child("CategoryName").setValue(CategoryName);
                            accountref.child("CategoryAddress").setValue(CategoryAddress);
                            accountref.child("CategoryImageURL").setValue(CategoryUrl);
                            accountref.child("VehicleCategory").setValue("Two Wheeler");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                if(TwoWheeler==0&&FourWheeler==1) {
                    int Slots_left = Integer.parseInt(dataSnapshot.getValue(CustomList.class).getFourWheelersleft());
                    int new_Slots_left = Slots_left-slots;
                    String new_slots= Integer.toString(new_Slots_left);
                    categoryref.child("fourWheelersleft").setValue(new_slots);
                    final String Slotsnew= Integer.toString(slots);
                    no_ofslotsbooked.setText(Slotsnew +" Four Wheeler Slots");
                    categoryname.setText(dataSnapshot.getValue(CustomList.class).getName());
                    categoryaddress.setText(dataSnapshot.getValue(CustomList.class).getAddress());

                    CategoryName=dataSnapshot.getValue(CustomList.class).getName();
                    CategoryAddress=dataSnapshot.getValue(CustomList.class).getAddress();
                    CategoryUrl=dataSnapshot.getValue(CustomList.class).getUrl();

                    accountref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            accountref.child("SlotsBooked").setValue(Slotsnew +" Four Wheeler/s Slots");
                            accountref.child("CategoryName").setValue(CategoryName);
                            accountref.child("CategoryAddress").setValue(CategoryAddress);
                            accountref.child("CategoryImageURL").setValue(CategoryUrl);
                            accountref.child("VehicleCategory").setValue("Four Wheeler");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
