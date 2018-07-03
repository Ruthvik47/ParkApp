package com.abcexample.parkapp;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static com.abcexample.parkapp.R.*;

public class MallActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText nameeditText, urleditText, addresseditText;
    private Button saveButton;
    private FirebaseAuth firebasehome;
    private FirebaseUser user;
    private ListView MallListView;
    private FirebaseClient firebaseClient;
    private String DB_URL="Malls";
    private String Temp="100";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_mall);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        MallListView =(ListView) findViewById(R.id.MallsContentListView);
        firebaseClient=new FirebaseClient(this, MallListView, DB_URL);
        firebaseClient.referencedata();

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MallListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                if(position==0) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid", "-KnOM-EHVIdKgt8MOb93");
                    i.putExtra("Category", "Malls");
                    startActivity(i);
                }
                if(position==1) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid","-KnOMArKK6enyZ2TzALb");
                    i.putExtra("Category", "Malls");
                    startActivity(i);
                }
                if(position==2) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid","-KnOMNypg5aG_cK5_Y4W");
                    i.putExtra("Category", "Malls");
                    startActivity(i);
                }
                if(position==3) {
                    Intent i = new Intent(getApplicationContext(), SelectionActivity.class);
                    i.putExtra("Categoryid", "-KnOMZ2dV5Cif-tdsgXV");
                    i.putExtra("Category", "Malls");
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
                        addresseditText.getText().toString(), urleditText.getText().toString(),Temp, Temp, Temp, Temp);

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
            NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            user=firebasehome.getInstance().getCurrentUser();
            View header = navigationView.getHeaderView(0);
            TextView emailtext= (TextView) header.findViewById(id.textViewuseremail);
            emailtext.setText(user.getEmail());
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mall, menu);
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
            Intent i = new Intent(this, StadiumActivity.class);
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
