package com.company.polaris_bustrackingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class AdminHomepage extends AppCompatActivity {

    FirebaseAuth mAuth;
    GridView gvAdminDash;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomepage.this);
        builder.setTitle("Log Out");
        builder.setTitle("Do you want to log out?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logOut();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));

        mAuth = FirebaseAuth.getInstance();
        gvAdminDash = findViewById(R.id.gvAdminDash);

        ArrayList<AdminDash> list = new ArrayList<>();

        list.add(new AdminDash("Manage Students",R.drawable.students_icon));
        list.add(new AdminDash("Manage Drivers", R.drawable.driver_icon));
        list.add(new AdminDash("Manage Buses", R.drawable.bus_icon));
        list.add(new AdminDash("Logout", R.drawable.logout_icon));

        AdminDashAdapter adminDashAdapter = new AdminDashAdapter(this,list);
        gvAdminDash.setAdapter(adminDashAdapter);

        gvAdminDash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)startActivity(new Intent(AdminHomepage.this,ManageStudents.class));
                if(i == 1)startActivity(new Intent(AdminHomepage.this,ManageDrivers.class));
                if(i == 2)startActivity(new Intent(AdminHomepage.this,ManageBus.class));
                if(i == 3) logOut();
            }
        });


    }

    private void logOut(){
        mAuth.signOut();
        startActivity(new Intent(AdminHomepage.this,MainActivity.class));
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(AdminHomepage.this,MainActivity.class));
        }
    }
}