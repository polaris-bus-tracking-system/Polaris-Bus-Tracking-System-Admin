package com.company.polaris_bustrackingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.company.polaris_bustrackingsystem.Adapters.DriverAdapter;
import com.company.polaris_bustrackingsystem.Models.DriverModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ManageDrivers extends AppCompatActivity {

    RecyclerView recyclerView;
    DriverAdapter driverAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_drivers);

        recyclerView = (RecyclerView) findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<DriverModel> options =
                new FirebaseRecyclerOptions.Builder<DriverModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Driver"), DriverModel.class)
                        .build();

        driverAdapter = new DriverAdapter(options);
        driverAdapter.startListening();
        recyclerView.setAdapter(driverAdapter);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonDriver);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddDriver.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        driverAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        driverAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        android.widget.SearchView searchView=(android.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtsearch(String str){
        FirebaseRecyclerOptions<DriverModel> options =
                new FirebaseRecyclerOptions.Builder<DriverModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Driver").orderByChild("name").startAt(str).endAt(str+"~"), DriverModel.class)
                        .build();

        driverAdapter = new DriverAdapter(options);
        driverAdapter.startListening();
        recyclerView.setAdapter(driverAdapter);
    }
}