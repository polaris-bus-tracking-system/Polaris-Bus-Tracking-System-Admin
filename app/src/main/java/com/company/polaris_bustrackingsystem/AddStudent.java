package com.company.polaris_bustrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.polaris_bustrackingsystem.Models.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AddStudent extends AppCompatActivity {
    private FirebaseAuth mAuth;

EditText name,enroll,busstop,imgurl,email,password;
Button btnStudentAdd, btnStudentBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        mAuth=FirebaseAuth.getInstance();

        name=(EditText) findViewById(R.id.etStudentName);
        enroll=(EditText) findViewById(R.id.etEnroll);
        busstop=(EditText) findViewById(R.id.etStudentBusStop);
        imgurl=(EditText) findViewById(R.id.addBusStops);
        email=(EditText) findViewById(R.id.etStudentEmail);
        password=(EditText) findViewById(R.id.etStudentPassword);

        btnStudentAdd=(Button) findViewById(R.id.btnStudentAdd);
        btnStudentBack=(Button) findViewById(R.id.btnStudentBack);

        btnStudentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             registerStud();
            }
        });

        btnStudentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void registerStud(){
        String Sname=name.getText().toString();
        String Senroll=enroll.getText().toString();
        String Sbusstop=busstop.getText().toString();
        String Simgurl=imgurl.getText().toString();
        String Semail=email.getText().toString();
        String Spassword=password.getText().toString();

        if(Sname.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
         }
        if(Semail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
            email.setError("Valid email is required");
            email.requestFocus();
            return;
        }
        if(Spassword.isEmpty() || Spassword.length()<6){
            password.setError("Password length must be minimum 6 character long");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Semail,Spassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            MainModel mainModel=new MainModel(Sname,Senroll,Sbusstop,Simgurl,Semail);

                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(mainModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddStudent.this, "New Student registered!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(AddStudent.this, "Registration failed! Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{ Toast.makeText(AddStudent.this, "failed to Register Student !", Toast.LENGTH_SHORT).show();}
                    }
                });
    }
}