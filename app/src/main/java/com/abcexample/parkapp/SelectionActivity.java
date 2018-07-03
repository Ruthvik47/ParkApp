package com.abcexample.parkapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SelectionActivity extends AppCompatActivity {

    private TextView Two_Wheelers_slotsleft, Four_Wheeler_slotsleft;
    private Button TwoWheelers_BookButton, FourWheelers_BookButton;
    private ImageView TwoWheelerImage, FourWheelerImage;
    DatabaseReference mallreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Two_Wheelers_slotsleft = (TextView) findViewById(R.id.TwoWheelerslefttextView);
        Four_Wheeler_slotsleft = (TextView) findViewById(R.id.FourWheelersLefttextView);
        TwoWheelers_BookButton = (Button) findViewById(R.id.TwoWheelerBookButton);
        FourWheelers_BookButton = (Button) findViewById(R.id.FourWheelerBookButton);
        TwoWheelerImage = (ImageView) findViewById(R.id.twowheelerimageView);
        FourWheelerImage = (ImageView) findViewById(R.id.FourWhelerimageView);

        Intent i = getIntent();
        final String CategoryID = i.getStringExtra("Categoryid");
        final String Category=i.getStringExtra("Category");

        mallreference= FirebaseDatabase.getInstance().getReference(Category).child(CategoryID);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Picasso.with(getApplicationContext())
                .load("http://www.clipartlord.com/wp-content/uploads/2016/02/motorcycle10.png")
                .placeholder(R.drawable.placeholder)
                .into(TwoWheelerImage);
        Picasso.with(getApplicationContext())
                .load("http://image.fg-a.com/cars/green-convertible.jpg")
                .placeholder(R.drawable.placeholder2)
                .into(FourWheelerImage);

        mallreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Two_Wheelers_slotsleft.setText("No.of Slots Left:" + dataSnapshot.getValue(CustomList.class).getTwoWheelersleft());
                Four_Wheeler_slotsleft.setText("No.of Slots Left:"+ dataSnapshot.getValue(CustomList.class).getFourWheelersleft());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        TwoWheelers_BookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), SlotsNumberActivity.class);
                i.putExtra("Categoryid", CategoryID);
                i.putExtra("Category", Category);
                i.putExtra("TwoWheeler", 1);
                i.putExtra("FourWheeler", 0);
                startActivity(i);

            }
        });

        FourWheelers_BookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), SlotsNumberActivity.class);
                i.putExtra("Categoryid", CategoryID);
                i.putExtra("Category", Category);
                i.putExtra("FourWheeler", 1);
                i.putExtra("TwoWheeler", 0);
                startActivity(i);
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