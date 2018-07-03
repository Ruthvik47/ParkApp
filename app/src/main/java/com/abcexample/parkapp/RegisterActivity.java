package com.abcexample.parkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextView textviewName;
    private EditText editTextemail;
    private EditText editTextpassword;
    private EditText editTextrepassword;
    private Button   Registerbutton;
    private TextView textViewAlreadyuser;
    private FirebaseAuth firebaseregister;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseregister=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        textviewName=(TextView) findViewById(R.id.textviewName);
        editTextemail=(EditText)findViewById(R.id.editTextemail);
        editTextpassword=(EditText) findViewById(R.id.editTextpassword);
        editTextrepassword=(EditText) findViewById(R.id.editTextrepassword);
        Registerbutton=(Button) findViewById(R.id.Registerbutton);
        textViewAlreadyuser=(TextView) findViewById(R.id.textViewAlreadyuser);


        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeruser();
            }
        });

        textViewAlreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
    public void Registeruser(){
        String Name=textviewName.getText().toString().trim();
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        String repassword=editTextrepassword.getText().toString().trim();

        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Name Field should not be Empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email Field should not be Empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Email Field should not be Empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(repassword)){
            Toast.makeText(this, "Email Field should not be Empty", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();
        firebaseregister.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        }
                    });


    }
}
