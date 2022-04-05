package com.company.polaris_bustrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminHomepage extends AppCompatActivity {

    Button buttonLogout;

    FirebaseAuth mAuth;
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