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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ManageBus extends AppCompatActivity {

    RecyclerView recyclerView;
    BusAdapter busAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<BusModel> options =
                new FirebaseRecyclerOptions.Builder<BusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bus"), BusModel.class)
                        .build();

        busAdapter = new BusAdapter(options);
        busAdapter.startListening();
        recyclerView.setAdapter(busAdapter);

      /*  floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonDriver);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddDriver.class));
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        busAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        busAdapter.stopListening();
    }

    /*@Override
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
        FirebaseRecyclerOptions<BusModel> options =
                new FirebaseRecyclerOptions.Builder<BusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bus").orderByChild("stops").startAt(str).endAt(str+"~"), BusModel.class)
                        .build();

        busAdapter = new BusAdapter(options);
        busAdapter.startListening();
        recyclerView.setAdapter(busAdapter);
    }*/
}