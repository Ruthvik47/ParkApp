package com.abcexample.parkapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class TimerService extends Service {


    private DatabaseReference timerref;
    private DatabaseReference accountref;
    private DatabaseReference mailref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    public String current_bookings="false";

    final class MyTimerThread implements Runnable {

        int service_id, Slots, TwoWheelers, FourWheelers;
        String Category, CategoryID;

        public MyTimerThread(int service_id, int slots, int twoWheelers, int fourWheelers, String category, String categoryID) {
            this.service_id = service_id;
            Slots = slots;
            TwoWheelers = twoWheelers;
            FourWheelers = fourWheelers;
            Category = category;
            CategoryID = categoryID;
        }

        @Override
        public void run() {
            int c = 0;
            while (c < 1) {
                auth = FirebaseAuth.getInstance();
                user = auth.getInstance().getCurrentUser();
                mailref = FirebaseDatabase.getInstance().getReference(Category).child(CategoryID);

                synchronized (this) {

                    auth = FirebaseAuth.getInstance();
                    user = auth.getInstance().getCurrentUser();
                    accountref = FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());

                    accountref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            accountref.child("CurrentBookings").setValue("true");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    SendMailSSL sm = new SendMailSSL(getApplicationContext(), user.getEmail(),
                            "parkapp17@gmail.com", "saiprasad@123", "Dear Customer," +
                            "\n\n Your Booking is been confirmed" + "\n No.of Slots Booked:" + Slots + "\n Duration: 30 secounds"
                            + "\n\n Note: Check in your Current Bookings for More Details");
                    sm.sendmail();

                    try {
                        wait(30000);
                        c++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    timerref = FirebaseDatabase.getInstance().getReference(Category).child(CategoryID);
                    timerref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (TwoWheelers == 1 && FourWheelers == 0) {
                                int Slots_left = Integer.parseInt(dataSnapshot.getValue(CustomList.class).getTwoWheelersleft());
                                int new_Slots_left = Slots_left + Slots;
                                String new_slots = Integer.toString(new_Slots_left);
                                timerref.child("twoWheelersleft").setValue(new_slots);
                            }
                            if (TwoWheelers == 0 && FourWheelers == 1) {
                                int Slots_left = Integer.parseInt(dataSnapshot.getValue(CustomList.class).getFourWheelersleft());
                                int new_Slots_left = Slots_left + Slots;
                                String new_slots = Integer.toString(new_Slots_left);
                                timerref.child("fourWheelersleft").setValue(new_slots);
                            }
                            Toast.makeText(getApplicationContext(), "Your Booking Duration has been Completed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                    SendMailSSL sssm = new SendMailSSL(getApplicationContext(), user.getEmail(),
                            "parkapp17@gmail.com", "saiprasad@123", "Dear Customer," +
                            "\n\n Your Booking Duration is been Completed" + "\n No.of Slots Booked:" + Slots + "\n Duration: 30 secounds");
                    sssm.sendmail();


                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                accountref = FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());

                accountref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        accountref.child("CurrentBookings").setValue("false");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Slot/ Slots are Booked", Toast.LENGTH_SHORT).show();
        String Category=intent.getStringExtra("Category");
        String CategoryID=intent.getStringExtra("Categoryid");
        int Slots=intent.getIntExtra("Slots",0);
        int TwoWheeler=intent.getIntExtra("TwoWheeler", 0);
        int FourWheeler=intent.getIntExtra("FourWheeler",0);
        Thread thread=new Thread(new MyTimerThread(startId, Slots, TwoWheeler, FourWheeler, Category, CategoryID));
        thread.start();
        return START_NOT_STICKY;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
