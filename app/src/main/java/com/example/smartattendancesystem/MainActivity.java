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

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {
    private Button abutton;
    private Button bbutton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abutton = findViewById(R.id.reg_Button);
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegPage();
            }
        });

        mauth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.usernameEmail);
        passwordEditText = findViewById(R.id.passwordLoginPage);
        bbutton = findViewById(R.id.loginButton);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignIn();
            }
        });

    }

    private void doSignIn() {
        String EmailsString = emailEditText.getText().toString().trim();
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
                    Toast.makeText(MainActivity.this, "Loged in!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, TeachersPageAfterLogin.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Login failed! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void openRegPage() {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
}