package com.abcexample.parkapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StadiumActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebasehome;
    private FirebaseUser user;
    private String DB_URL="Stadiums";
    private ListView StadiumListView;
    private EditText nameeditText, urleditText, addresseditText;
    private Button saveButton;
    private FirebaseClient firebaseClient;
    private String Temp="100";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        StadiumListView =(ListView) findViewById(R.id.StadiumContentListView);
        firebaseClient=new FirebaseClient(this, StadiumListView, DB_URL);
        firebaseClient.referencedata();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StadiumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid", "-KnQ7j_Df37HZjGY1vBo");
                    i.putExtra("Category", "Stadiums");
                    startActivity(i);
                }
                if(position==1) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid", "-KnQ7n8av2jmspFwEVGe");
                    i.putExtra("Category", "Stadiums");
                    startActivity(i);
                }
                if(position==2) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid","-KnQ86be765oXUnki6tD");
                    i.putExtra("Category", "Stadiums");
                    startActivity(i);
                }
                if(position==3) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid","-KnQ891VM7ToH8EqHL8r");
                    i.putExtra("Category", "Stadiums");
                    startActivity(i);
                }
                if(position==4) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid", "-KnQ8CbLbsdzag0_E0cV");
                    i.putExtra("Category", "Stadiums");
                    startActivity(i);
                }


            }
        });
    }

    private void displayDialog(){
        Dialog d=new Dialog(this);
        d.setTitle("Save Data");
        d.setContentView(R.layout.customviewdialog);
        nameeditText=(EditText) d.findViewById(R.id.nameEditText);
        addresseditText=(EditText) d.findViewById(R.id.AddressEditText);
        urleditText=(EditText) d.findViewById(R.id.UrlEditText);
        saveButton=(Button) d.findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseClient.savedata(nameeditText.getText().toString(),
                        addresseditText.getText().toString(), urleditText.getText().toString(), Temp, Temp, Temp,Temp);

                nameeditText.setText("");
                addresseditText.setText("");
                urleditText.setText("");
            }
        });
        d.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebasehome= FirebaseAuth.getInstance();
        if(firebasehome.getCurrentUser()==null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        else{
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            user=firebasehome.getInstance().getCurrentUser();
            View header = navigationView.getHeaderView(0);
            TextView emailtext= (TextView) header.findViewById(R.id.textViewuseremail);
            emailtext.setText(user.getEmail());
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stadium, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
}
