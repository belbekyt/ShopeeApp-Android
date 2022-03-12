package com.example.projektshopee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int [] computers;
    String [] descriptions;
    LayoutInflater layoutInflater;
    ImageView imageView;
    TextView textView;

    public MyAdapter(Context context, int [] computers, String[] descriptions){
        super();
        this.context = context;
        this.computers = computers;
        this.descriptions = descriptions;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return  computers.length;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.my_spinner_items, null);
        imageView = convertView.findViewById(R.id.imageView);
        textView = convertView.findViewById(R.id.textView);
        imageView.setImageResource(computers[position]);
        textView.setText(descriptions[position]);
        return convertView;
    }
}
