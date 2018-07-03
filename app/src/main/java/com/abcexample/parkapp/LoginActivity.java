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

public class LoginActivity extends AppCompatActivity {

    private EditText editTextemail;
    private EditText editTextpassword;
    private Button Loginbutton;
    private TextView textViewRegister;
    private ProgressDialog progressdialogue;
    private FirebaseAuth firebaseauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth=FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), ParkAppHomeActivity.class));
        }

        editTextemail=(EditText) findViewById(R.id.editTextemail);
        editTextpassword=(EditText)findViewById(R.id.editTextpassword);
        Loginbutton=(Button)findViewById(R.id.Loginbutton);
        textViewRegister=(TextView)findViewById(R.id.textviewRegister);
        progressdialogue=new ProgressDialog(this);

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addlogin();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    public void addlogin(){
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email Field Should not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password Field Should not be Empty", Toast.LENGTH_SHORT).show();
            return;

        }
        progressdialogue.setMessage("Logging In...");
        progressdialogue.show();

        firebaseauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialogue.dismiss();
                        if(task.isComplete()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ParkAppHomeActivity.class));
                        }


                    }
                });

    }
}
