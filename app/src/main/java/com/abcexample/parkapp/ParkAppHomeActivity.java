package com.abcexample.parkapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;


public class ParkAppHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebasehome;
    private FirebaseUser user;
    private ProgressDialog progressdialogue;
    private DatabaseReference accountref;
    public String CurrentBookings="false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_app_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        progressdialogue=new ProgressDialog(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu=navigationView.getMenu();

    }

    public void onClick1(View view){
        Intent i = new Intent(this, MallActivity.class);
        startActivity(i);
    }

    public void onClick2(View view){
        Intent i = new Intent(this, StadiumActivity.class);
        startActivity(i);
    }
    public void onClick3(View view){
        Intent i = new Intent(this, TheathreActivity.class);
        startActivity(i);
    }
    public void onClick4(View view){
        Intent i = new Intent(this, StationActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        CurrentBookings="false";
        super.onStart();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            user=firebasehome.getInstance().getCurrentUser();
            View header = navigationView.getHeaderView(0);
            TextView emailtext= (TextView) header.findViewById(R.id.textViewuseremail);
            emailtext.setText(user.getEmail());


        accountref= FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());
        accountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentBookings=dataSnapshot.child("CurrentBookings").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.park_app_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        firebasehome=FirebaseAuth.getInstance();
        user=firebasehome.getCurrentUser();
        accountref= FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());
        accountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentBookings= dataSnapshot.child("CurrentBookings").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (id == R.id.action_settings) {
            finish();
            Intent i= new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        if(id == R.id.actions_SignOut){
            firebasehome.signOut();
            progressdialogue.setMessage("Signing Out...");
            progressdialogue.show();
            finish();
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.actions_MyAccount) {
            finish();
            Intent i= new Intent(this, MyAccount.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.actions_MyCurrentBookings) {

            accountref= FirebaseDatabase.getInstance().getReference("Accounts").child(user.getUid());
            accountref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    CurrentBookings=dataSnapshot.child("CurrentBookings").getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(CurrentBookings=="true"){
                finish();
                Intent i= new Intent(this, CurrentBookingsActivity.class);
                startActivity(i);
                return true;
            }
            else{
                finish();
                Intent i = new Intent(this, EmptyActivity.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Handle Actions Wrto Clicks
        if(id == R.id.nav_Home){
            Intent i=new Intent(this, ParkAppHomeActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_Malls) {
            Intent i=new Intent(this, MallActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_Stations) {
            Intent i = new Intent(this, StationActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_Theatres) {

            Intent i = new Intent(this, TheathreActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_stadiums) {

            Intent i = new Intent(this, StadiumActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hey! This is the new Parking App, " +
                    "Which is used to book a Parking slot in and around the City's popular places like Malls, Stadiums, Theatres, etc.,";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ParkApp");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_send) {

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "Hey! This is the new Parking App, " +
                    "Which is used to book a Parking slot in and around the City's popular places like Malls, Stadiums, Theatres, etc.,");
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
