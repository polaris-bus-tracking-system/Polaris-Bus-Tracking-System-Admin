package com.company.polaris_bustrackingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminHomepage extends AppCompatActivity {

    Button buttonLogout;
    Button manageStudents,manageDrivers,manageBuses;

    FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomepage.this);
        builder.setTitle("Log Out");
        builder.setTitle("Do you want to log out?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                startActivity(new Intent(AdminHomepage.this,MainActivity.class));
                Toast.makeText(AdminHomepage.this, "Logged Out", Toast.LENGTH_SHORT).show();
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

        buttonLogout = findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();

        buttonLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(AdminHomepage.this,MainActivity.class));
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        });



        manageStudents = findViewById(R.id.ManageStudents);
        manageBuses = findViewById(R.id.ManageBuses);
        manageDrivers = findViewById(R.id.ManageDrivers);

        manageStudents.setOnClickListener(view -> {
        startActivity(new Intent(AdminHomepage.this,ManageStudents.class));
        });

        manageDrivers.setOnClickListener(view -> {
            startActivity(new Intent(AdminHomepage.this,ManageDrivers.class));
        });

        manageBuses.setOnClickListener(view -> {
            startActivity(new Intent(AdminHomepage.this,ManageDrivers.class));
        });

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