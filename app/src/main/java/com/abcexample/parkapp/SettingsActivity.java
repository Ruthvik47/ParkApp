package com.abcexample.parkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class SettingsActivity extends AppCompatActivity {

    private ImageView SettingsImage;
    private FirebaseAuth firebasehome;
    private EditText FirstName;
    private EditText LastName;
    private EditText PhoneNumber;
    private EditText DateofBirth;
    private Button SettingsSavebutton;
    private RadioGroup radiogroup;
    private DatabaseReference accountdata;
    private RadioButton btn_Gender, malebutton, femalebutton;
    private FirebaseUser user;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        SettingsImage = (ImageView) findViewById(R.id.SettingsImage);
        FirstName = (EditText) findViewById(R.id.editTextFirstName);
        LastName = (EditText) findViewById(R.id.editTextlastname);
        PhoneNumber = (EditText) findViewById(R.id.phonenumber);
        DateofBirth = (EditText) findViewById(R.id.DateofBirth);
        SettingsSavebutton = (Button) findViewById(R.id.settingssave);
        radiogroup = (RadioGroup) super.findViewById(R.id.radiogroup1);
        malebutton = (RadioButton) super.findViewById(R.id.male);
        femalebutton = (RadioButton) super.findViewById(R.id.female);
        malebutton.setChecked(true);
        int selectedId = radiogroup.getCheckedRadioButtonId();
        btn_Gender = (RadioButton) findViewById(selectedId);


        firebasehome = FirebaseAuth.getInstance();
        user = firebasehome.getInstance().getCurrentUser();
        accountdata = FirebaseDatabase.getInstance().getReference("Accounts");

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SettingsSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });
    }


    public void savedata() {

        String fname = FirstName.getText().toString().trim();
        String lname = LastName.getText().toString().trim();
        String Pno = PhoneNumber.getText().toString().trim();
        String Dob = DateofBirth.getText().toString().trim();
        String gender = btn_Gender.getText().toString().trim();
        progressDialog = new ProgressDialog(SettingsActivity.this);

        if (TextUtils.isEmpty(fname)) {
            Toast.makeText(SettingsActivity.this, "Enter Firstname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lname)) {
            Toast.makeText(SettingsActivity.this, "Enter lastname", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Pno)) {
            Toast.makeText(SettingsActivity.this, "Enter PhoneNumber", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Dob)) {
            Toast.makeText(SettingsActivity.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(SettingsActivity.this, "Enter gender", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Updating...");
            progressDialog.show();
            AccountInfo a = new AccountInfo();
            a.setFirstName(fname);
            a.setLastName(lname);
            a.setDateofBirth(Dob);
            a.setGender(gender);
            a.setPhoneNumber(Pno);
            a.setAccountid(user.getUid());
            accountdata.child(user.getUid()).setValue(a);
            accountdata.child(user.getUid()).child("CurrentBookings").setValue("false");
            progressDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), ParkAppHomeActivity.class);
            startActivity(i);

        }
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


