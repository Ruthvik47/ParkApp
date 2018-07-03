package com.abcexample.parkapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by divya on 17-06-2017.
 */

public class Myholder {

    TextView NameText;
    TextView AddressText;
    ImageView UrlImage;

    public Myholder(View itemView)
    {
        NameText=(TextView)itemView.findViewById(R.id.HomeListImageName);
        AddressText = (TextView) itemView.findViewById(R.id.HomeListImageAddress);
        UrlImage=(ImageView)itemView.findViewById(R.id.HomeListImage);
    }
}
