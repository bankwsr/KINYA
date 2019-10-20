package com.example.kinya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public static final String DRUG_NAME = "drugname";
    public static final String DRUG_ID = "drugid";

    EditText editTextName, editTextDetail;
    Spinner spinnerType;
    Button buttonAddDrug;
    ListView listViewDrug;

    List<Drug> drugs;

    DatabaseReference databaseDrugs;

    private ImageButton imageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        databaseDrugs = FirebaseDatabase.getInstance().getReference("Drug");

        editTextName = v.findViewById(R.id.editTextName);
        editTextDetail = v.findViewById(R.id.editTextDetail);
        spinnerType = v.findViewById(R.id.spinnerType);
        listViewDrug = v.findViewById(R.id.listViewDrug);

        buttonAddDrug = v.findViewById(R.id.buttonAddDrug);

        drugs = new ArrayList<>();

        buttonAddDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDrug();
            }
        });

        listViewDrug.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Drug drug = drugs.get(i);

                Intent intent = new Intent(getActivity().getApplicationContext(), AddDetailActivity.class);

                intent.putExtra(DRUG_ID, drug.getDrugId());
                intent.putExtra(DRUG_NAME, drug.getDrugName());

                startActivity(intent);
            }
        });

        listViewDrug.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Drug drug = drugs.get(i);

                showUpdateDialog(drug.getDrugId(), drug.getDrugName(), drug.getDrugDetail(), drug.getDrugType());
                return false;
            }
        });


//////////////////////////////////////////////////////////////////////////////////////
        /*imageButton = v.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityCloud();
            }
        });*/
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseDrugs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                drugs.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Drug drug = postSnapshot.getValue(Drug.class);

                    drugs.add(drug);
                }

                DrugList drugAdapter = new DrugList(getActivity(), drugs);

                listViewDrug.setAdapter(drugAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateDrug(String id,String name, String detail, String type){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drug").child(id);

        Drug drug = new Drug(id, name, detail, type);

        databaseReference.setValue(drug);

        Toast.makeText(getActivity(), "อัพเดทข้อมูลสำเร็จ", Toast.LENGTH_LONG).show();

        return true;
    }

    private void deleteDrug(String drugId){
        DatabaseReference drDrug = FirebaseDatabase.getInstance().getReference("Drug").child(drugId);
        DatabaseReference drDetail = FirebaseDatabase.getInstance().getReference("details").child(drugId);

        drDrug.removeValue();
        drDetail.removeValue();

        Toast.makeText(getActivity(), "ลบข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
    }

    private void addDrug() {
        String name = editTextName.getText().toString().trim();
        String detail = editTextDetail.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {

            String id = databaseDrugs.push().getKey();

            Drug drug = new Drug(id, name, detail, type);

            databaseDrugs.child(id).setValue(drug);

            editTextName.setText("");
            editTextDetail.setText("");

            Toast.makeText(getActivity(), "เพิ่มยาแล้ว", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "กรุณากรอกข้อมูล", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(final String drugId, String drugName, String drugDetail, String drugType){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextDetail = (EditText) dialogView.findViewById(R.id.editTextDetail);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerType = (Spinner) dialogView.findViewById(R.id.spinnerType);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Update Drug " +drugName+drugDetail);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String detail = editTextDetail.getText().toString().trim();
                String type = spinnerType.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)){
                    editTextName.setError("กรุณากรอกข้อมูล");
                    editTextDetail.setError("กรุณากรอกข้อมูล");
                    return;
                }

                updateDrug(drugId, name, detail, type);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDrug(drugId);
            }
        });
    }

}
