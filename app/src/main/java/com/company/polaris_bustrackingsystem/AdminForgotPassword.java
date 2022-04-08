package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminForgotPassword extends AppCompatActivity {

    EditText etEmailFP;
    Button buttonResetPassword;
    TextView tvLoginPage;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forgot_password);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminForgotPassword.this, R.color.black));
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etEmailFP = findViewById(R.id.etAdminFP);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        tvLoginPage = findViewById(R.id.textViewLoginPage);

        auth = FirebaseAuth.getInstance();

        buttonResetPassword.setOnClickListener(view -> {
            resetPassword();
        });

        tvLoginPage.setOnClickListener(view -> {
            startActivity(new Intent(AdminForgotPassword.this,MainActivity.class));
        });
    }

    private void resetPassword(){
        String email = etEmailFP.getText().toString();

        if(email.isEmpty()){
            etEmailFP.setError("Email cannot be empty");
            etEmailFP.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailFP.setError("Please provide valid email");
            etEmailFP.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AdminForgotPassword.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}