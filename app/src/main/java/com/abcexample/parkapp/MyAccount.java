package com.abcexample.parkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class MyAccount extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth firebasehome;
    FirebaseUser user;
    TextView firstname, lastname, email, phoneno, dob;
    ImageView Myaccountimage;
    Button Myaccountbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        firebasehome=FirebaseAuth.getInstance();
        user = firebasehome.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());
        Myaccountimage=(ImageView)findViewById(R.id.myaccountimage);
        firstname=(TextView) findViewById(R.id.firstnametextview);
        lastname=(TextView)findViewById(R.id.lastnametextview);
        email=(TextView)findViewById(R.id.textviewemail);
        phoneno=(TextView)findViewById(R.id.phonenumbertextview);
        dob=(TextView)findViewById(R.id.dateofbirthtextview);
        Myaccountbutton=(Button) findViewById(R.id.myaccountupdatebutton);

        Myaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstname.setText(dataSnapshot.getValue(AccountInfo.class).getFirstName());
                lastname.setText(dataSnapshot.getValue(AccountInfo.class).getLastName());
                email.setText(user.getEmail());
                phoneno.setText(dataSnapshot.getValue(AccountInfo.class).getPhoneNumber());
                dob.setText(dataSnapshot.getValue(AccountInfo.class).getDateofBirth());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent i=new Intent(this, ParkAppHomeActivity.class);
        startActivity(i);
    }
}
