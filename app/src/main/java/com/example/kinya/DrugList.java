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

public class DrugList extends ArrayAdapter<Drug> {

    private Activity context;
    List<Drug> drugs;

    public DrugList(Activity context, List<Drug> drugs) {
        super(context, R.layout.activity_drug_list, drugs);
        this.context = context;
        this.drugs = drugs;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_drug_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDetail = (TextView) listViewItem.findViewById(R.id.textViewDetail);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.textViewType);

        Drug drug = drugs.get(position);
        textViewName.setText(drug.getDrugName());
        textViewDetail.setText(drug.getDrugDetail());
        textViewType.setText(drug.getDrugType());

        return listViewItem;
    }
}
