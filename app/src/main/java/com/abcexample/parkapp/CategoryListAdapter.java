package com.abcexample.parkapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseAdapter {

    Context c;
    public ArrayList<CustomList> category= new ArrayList<>();
    LayoutInflater inflater;


    public CategoryListAdapter(Context c, ArrayList<CustomList> category) {
        this.c = c;
        this.category = category;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int position) {
        return category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.custom_list_layout, parent, false);
        }
        Myholder holder=new Myholder(convertView);
        holder.NameText.setText(category.get(position).getName());
        holder.AddressText.setText(category.get(position).getAddress());
        PicassoClient.downloading(c, category.get(position).getUrl(), holder.UrlImage);

        return convertView;
    }
}
