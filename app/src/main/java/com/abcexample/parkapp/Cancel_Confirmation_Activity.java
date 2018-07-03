package com.abcexample.parkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Cancel_Confirmation_Activity extends AppCompatActivity {

    private Button gohome;
    private TextView successtext;
    private FirebaseAuth auth;
    private FirebaseUser user;

    final class MailThread implements Runnable {

        
        public MailThread() {

        }

        @Override
        public void run() {
            auth=FirebaseAuth.getInstance();
            user=auth.getInstance().getCurrentUser();

            SendMailSSL sm = new SendMailSSL(getApplicationContext(), user.getEmail(),
                    "parkapp17@gmail.com", "saiprasad@123", "Dear Customer," +
                    "\n\n Your have cancelled your Booking" + "\n Duration: 30 secounds");
            sm.sendmail();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel__confirmation_);

        Thread thread=new Thread(new Cancel_Confirmation_Activity.MailThread());
        thread.start();
        
        gohome=(Button)findViewById(R.id.gotohomebutton);

        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getApplicationContext(), ParkAppHomeActivity.class);
                startActivity(i);
            }
        });
    }
}
