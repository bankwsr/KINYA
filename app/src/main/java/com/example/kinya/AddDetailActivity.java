package com.example.kinya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDetailActivity extends AppCompatActivity {

    TextView textViewDrugName;
    EditText editTextDetailName;
    Button buttonAddDetail;
    ListView listViewDetails;
    DatabaseReference databaseDetails;
    List<Detail> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        textViewDrugName = (TextView) findViewById(R.id.textViewDrugName);
        editTextDetailName = (EditText) findViewById(R.id.editTextDetailName);

        buttonAddDetail = (Button) findViewById(R.id.buttonAddDetail);

        listViewDetails = (ListView) findViewById(R.id.listViewDetail);

        Intent intent = getIntent();

        details = new ArrayList<>();

        String id = intent.getStringExtra(SearchFragment.DRUG_ID);
        String name = intent.getStringExtra(SearchFragment.DRUG_NAME);

        textViewDrugName.setText(name);

        databaseDetails = FirebaseDatabase.getInstance().getReference("details").child(id);

        buttonAddDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetail();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Detail detail = trackSnapshot.getValue(Detail.class);
                    details.add(detail);
                }

                DetailList detailListAdapter = new DetailList(AddDetailActivity.this, details);
                listViewDetails.setAdapter(detailListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveDetail(){
        String detailName = editTextDetailName.getText().toString().trim();

        if (!TextUtils.isEmpty(detailName)) {
            String id = databaseDetails.push().getKey();

            Detail detail = new Detail(id, detailName);

            databaseDetails.child(id).setValue(detail);

            Toast.makeText(this, "บันทึกข้อมูลเสำเร็จ", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "ไม่ได้กรอกข้อมูล", Toast.LENGTH_LONG).show();

        }
    }
}