package com.abcexample.parkapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SlotsNumberActivity extends AppCompatActivity {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots_number);

        String[] slots={"1 Slot", "2 Slots", "3 Slots", "4 Slots", "5 Slots", "6 Slots"};
        ListAdapter slotadapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, slots);
        ListView slotslistview=(ListView)findViewById(R.id.Numberofslots);
        slotslistview.setAdapter(slotadapter);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        int TwoWheelerdefault=0;
        int FourWheelerdefault=0;

        Intent i = getIntent();
        final String CategoryID=i.getStringExtra("Categoryid");
        final String Category=i.getStringExtra("Category");
        final int TwoWheeler=i.getIntExtra("TwoWheeler", TwoWheelerdefault);
        final int FourWheeler=i.getIntExtra("FourWheeler", FourWheelerdefault);


        slotslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",1);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",1);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }

                if(position==1){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",2);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",2);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }
                if(position==2){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",3);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",3);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }
                if(position==3){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",4);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",4);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }
                if(position==4){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",5);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",5);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }
                if(position==5){
                    Intent s=new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                    s.putExtra("Categoryid", CategoryID);
                    s.putExtra("Category", Category);
                    s.putExtra("Slots",6);
                    s.putExtra("TwoWheeler", TwoWheeler);
                    s.putExtra("FourWheeler", FourWheeler);
                    startActivity(s);

                    Intent t=new Intent(getApplicationContext(), TimerService.class);
                    t.putExtra("Categoryid", CategoryID);
                    t.putExtra("Category", Category);
                    t.putExtra("Slots",6);
                    t.putExtra("TwoWheeler", TwoWheeler);
                    t.putExtra("FourWheeler", FourWheeler);
                    startService(t);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
