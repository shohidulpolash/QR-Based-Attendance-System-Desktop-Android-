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

public class LoginPageForStudent extends AppCompatActivity {
private Button abutton;
    private Button bbutton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mauth;
    String EmailsString;
    public static final String EXTRA_TEXT="com.example.smartattendancesystem.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_for_student);
        abutton=findViewById(R.id.reg_ButtonForStudent);
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegPageForStudent();
            }
        });
        mauth = FirebaseAuth.getInstance();


        emailEditText = findViewById(R.id.EmailForStudent);
        passwordEditText = findViewById(R.id.passwordLoginPageForStudent);
        bbutton = findViewById(R.id.loginButtonForStudent);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignIn();
            }
        });
    }
    private void doSignIn() {
        EmailsString = emailEditText.getText().toString().trim();
        String PassString = passwordEditText.getText().toString().trim();


        if (EmailsString.isEmpty()) {
            emailEditText.setError("Must be fill up");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(EmailsString).matches()) {
            emailEditText.setError("Please provide valid email !");
            emailEditText.requestFocus();
            return;
        }
        if (PassString.isEmpty()) {
            passwordEditText.setError("Must be fill up");
            passwordEditText.requestFocus();
            return;
        }
        if (PassString.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters !");
            passwordEditText.requestFocus();
            return;
        }

        mauth.signInWithEmailAndPassword(EmailsString, PassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginPageForStudent.this, "Loged in!", Toast.LENGTH_LONG).show();
                    openTask();
                } else {
                    Toast.makeText(LoginPageForStudent.this, "Login failed! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void openTask() {
        Intent i = new Intent(LoginPageForStudent.this, StudentPageAfterLogIn.class);
        i.putExtra(EXTRA_TEXT,EmailsString);
        startActivity(i);
    }

    private void openRegPageForStudent() {
        Intent i=new Intent(this,RegisterPageForStudent.class);
        startActivity(i);
    }
}