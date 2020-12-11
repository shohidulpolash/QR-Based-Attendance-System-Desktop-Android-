package com.example.smartattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {
    private  Button abutton,regButton;
    private FirebaseAuth mAuth;
    private EditText nameEditText,emailEditText,passEditText,teacherIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        abutton=findViewById(R.id.log_btn);
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogPage();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        regButton=findViewById(R.id.regButtonRegPage);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterUser();
            }
        });


        nameEditText=findViewById(R.id.usernameRegPage);
        passEditText=findViewById(R.id.passwordRegPage);
        emailEditText=findViewById(R.id.emailRegPage);
        teacherIdEditText=findViewById(R.id.teacherId);


    }

    private void openRegisterUser() {
        String emailsString=emailEditText.getText().toString().trim();
        String passString=passEditText.getText().toString().trim();
        String fullnames=nameEditText.getText().toString().trim();
        String IdTeacher=teacherIdEditText.getText().toString().trim();
        if(IdTeacher.isEmpty()){
            teacherIdEditText.setError("Must be fill up");
            teacherIdEditText.requestFocus();
            return;
        }
        if(fullnames.isEmpty()){
            nameEditText.setError("Must be fill up");
            nameEditText.requestFocus();
            return;
        }
        if(emailsString.isEmpty()){
            emailEditText.setError("Must be fill up");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailsString).matches()){
            emailEditText.setError("Please provide valid email !");
            emailEditText.requestFocus();
            return;
        }
        if(passString.isEmpty()){
            passEditText.setError("Must be fill up");
            passEditText.requestFocus();
            return;
        }
        if(passString.length()<6){
            passEditText.setError("Password must be at least 6 characters !");
            passEditText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailsString,passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    user user=new user(fullnames,emailsString,IdTeacher);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(RegisterPage.this,"User Has been registerd Successfully !",Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                             startActivity(intent);
                         }
                         else {
                             Toast.makeText(RegisterPage.this,"Registration failed! Try again!",Toast.LENGTH_LONG).show();
                         }
                        }
                    });
                }else
                {
                    Toast.makeText(RegisterPage.this,"Registration failed! Try again!",Toast.LENGTH_LONG).show();
                }

            }
        });





    }

    private void openLogPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}