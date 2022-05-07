package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddBus extends AppCompatActivity {


    EditText busno,busrout,busDriverid,stops;
    Button busAdd,busback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        busno = (EditText) findViewById(R.id.addBusNo);
        busrout = (EditText) findViewById(R.id.addBusRoute);
        busDriverid = (EditText) findViewById(R.id.addDriverId);
        stops = (EditText) findViewById(R.id.addBusStops);

        busAdd = (Button) findViewById(R.id.btnBusAdd);
        busback = (Button) findViewById(R.id.btnBusBack);

        busAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        busback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    public void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("busno",busno.getText().toString());
        map.put("busroute",busrout.getText().toString());
        map.put("driverid",busDriverid.getText().toString());
        map.put("stops",stops.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Bus").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddBus.this,"Data added",Toast.LENGTH_SHORT);
                    }
                });
    }

}