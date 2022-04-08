package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminRegistration extends AppCompatActivity {

    EditText etAdminEmailR,etAdminPasswordR;
    TextView tvAdminLoginHere;
    Button buttonRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminRegistration.this,R.color.black));
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etAdminEmailR = findViewById(R.id.editTextAdminEmailRegister);
        etAdminPasswordR = findViewById(R.id.editTextAdminPasswordRegister);
        tvAdminLoginHere = findViewById(R.id.textViewLoginHere);
        buttonRegister = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(view ->{
            createUser();
        });

        tvAdminLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(AdminRegistration.this,MainActivity.class));
        });
    }

    private void createUser(){
        String email = etAdminEmailR.getText().toString();
        String password = etAdminPasswordR.getText().toString();

        if(TextUtils.isEmpty(email)){
            etAdminEmailR.setError("Email cannot be empty");
            etAdminEmailR.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            etAdminPasswordR.setError("Password cannot be empty");
            etAdminPasswordR.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AdminRegistration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminRegistration.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(AdminRegistration.this, "Registration Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}