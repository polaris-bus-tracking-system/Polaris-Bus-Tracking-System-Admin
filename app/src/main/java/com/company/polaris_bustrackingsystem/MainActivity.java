package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etAdminEmailL, etAdminPasswordL;
    TextView tvRegister, tvForgotPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));


        etAdminEmailL = findViewById(R.id.editTextAdminEmail);
        etAdminPasswordL = findViewById(R.id.editTextAdminPassword);
        tvRegister = findViewById(R.id.textViewRegister);
        tvForgotPassword = findViewById(R.id.textViewForgotPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(view -> {
            loginUser();
        });

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,AdminRegistration.class));
        });

        tvForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,AdminForgotPassword.class));
        });
    }

    private  void loginUser(){
        String email = etAdminEmailL.getText().toString().trim();
        String password = etAdminPasswordL.getText().toString();

        if(TextUtils.isEmpty(email)){
            etAdminEmailL.setError("Email cannot be empty");
            etAdminEmailL.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            etAdminPasswordL.setError("Password cannot be empty");
            etAdminPasswordL.requestFocus();
        }else {

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,AdminHomepage.class));
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Login Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}