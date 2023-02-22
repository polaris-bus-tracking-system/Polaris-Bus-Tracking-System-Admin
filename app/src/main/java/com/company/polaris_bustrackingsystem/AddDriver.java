package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.polaris_bustrackingsystem.Models.DriverModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddDriver extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText name,phone,imgurl,email,password;
    Button btnDriverAdd, btnDriverBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.addDrivername);
        phone = (EditText) findViewById(R.id.addDriverphone);
        imgurl = (EditText) findViewById(R.id.addDriverurl);
        email = (EditText) findViewById(R.id.addDriverId);
        password = (EditText) findViewById(R.id.addDriverPassword);

        btnDriverAdd = (Button) findViewById(R.id.btnDriverAdd);
        btnDriverBack = (Button) findViewById(R.id.btnDriverBack);

        btnDriverAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerDriver();
            }
        });

        btnDriverBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

        private void registerDriver(){
            String Dname = name.getText().toString();
            String Dphone = phone.getText().toString();
            String Dimgurl = imgurl.getText().toString();
            String Demail = email.getText().toString();
            String Dpassword = password.getText().toString();

            if (Dname.isEmpty()) {
                name.setError("Full name is required");
                name.requestFocus();
                return;
            }
            if (Demail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Demail).matches()) {
                email.setError("Valid email is required");
                email.requestFocus();
                return;
            }
            if (Dpassword.isEmpty() || Dpassword.length() < 6) {
                password.setError("Password length must be minimum 6 character long");
                password.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(Demail, Dpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DriverModel driverModel = new DriverModel(Dname,Dimgurl,Demail,Dphone,FirebaseAuth.getInstance().getCurrentUser().getUid());

                                FirebaseDatabase.getInstance().getReference("Driver")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(driverModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddDriver.this, "New Driver registered!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddDriver.this, "Registration failed! Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(AddDriver.this, "failed to Register Driver !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }




}

