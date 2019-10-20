package com.example.kinya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailList extends ArrayAdapter<Detail> {

    private Activity context;
    List<Detail> details;

    public DetailList(Activity context, List<Detail> details) {
        super(context, R.layout.activity_detail_list, details);
        this.context = context;
        this.details = details;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_detail_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Detail detail = details.get(position);
        textViewName.setText(detail.getDetailName());

        return listViewItem;
    }
}
