package com.abcexample.parkapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CurrentBookingsActivity extends AppCompatActivity {

    private TextView no_ofslotsbooked, categoryname, categoryaddress;
    private ImageView categoryimage;
    private Button refreshbutton, cancelbutton;
    private DatabaseReference accountref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String CurrentBooking;
    public int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bookings);

        no_ofslotsbooked=(TextView) findViewById(R.id.numberofslotsbooked);
        categoryname=(TextView)findViewById(R.id.confirmcategory);
        categoryaddress=(TextView)findViewById(R.id.confirmaddress);
        categoryimage=(ImageView)findViewById(R.id.categoryimageview);
        refreshbutton=(Button)findViewById(R.id.Refreshbutton);
        cancelbutton=(Button)findViewById(R.id.CancelButton);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        auth=FirebaseAuth.getInstance();
        user=auth.getInstance().getCurrentUser();

        accountref= FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());
        accountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentBooking =dataSnapshot.child("CurrentBookings").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CurrentBooking=="false") {
                    finish();
                    Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
                    startActivity(i);
                }
                if(CurrentBooking=="true")
                {
                    finish();
                    Intent i = new Intent(getApplicationContext(), CurrentBookingsActivity.class);
                    startActivity(i);
                }

            }
        });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });




        auth=FirebaseAuth.getInstance();
        user=auth.getInstance().getCurrentUser();

        accountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CurrentBooking =dataSnapshot.child("CurrentBookings").getValue().toString();

                if(CurrentBooking=="true")
                {

                    Picasso.with(getApplicationContext())
                            .load(dataSnapshot.child("CategoryImageURL").getValue().toString())
                            .placeholder(R.drawable.placeholder)
                            .into(categoryimage);

                    no_ofslotsbooked.setText(dataSnapshot.child("SlotsBooked").getValue().toString());
                    categoryname.setText(dataSnapshot.child("CategoryName").getValue().toString());
                    categoryaddress.setText(dataSnapshot.child("CategoryAddress").getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent i=new Intent(this, ParkAppHomeActivity.class);
        startActivity(i);
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Cancellation")
                .setMessage("Do you want to Cancel Booking")
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        auth=FirebaseAuth.getInstance();
                        user=auth.getCurrentUser();
                        accountref=FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());

                        accountref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                accountref.child("CurrentBookings").setValue("false");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        dialog.dismiss();

                        finish();
                        Intent i=new Intent(getApplicationContext(), Cancel_Confirmation_Activity.class);
                        startActivity(i);
                    }

                })


                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }

}

