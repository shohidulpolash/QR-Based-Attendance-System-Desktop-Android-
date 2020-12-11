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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPageForStudent extends AppCompatActivity {
    private Button abutton, doReg;
    private FirebaseAuth mAuth;
    private EditText nameEditText, emailEditText, passEditText,idEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_for_student);
        abutton = findViewById(R.id.regPageToLoginPageForStudent);
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openloginPageForStudent();
            }
        });
        doReg=findViewById(R.id.regButtonRegPageForstudent);
        doReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoReg();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        nameEditText=findViewById(R.id.usernameRegPageForStudent);
        passEditText=findViewById(R.id.passwordRegPageForStudent);
        emailEditText=findViewById(R.id.emailRegPageForStudent);
        idEditText=findViewById(R.id.studentId);


    }

    private void openDoReg() {
        String emailsStudent=emailEditText.getText().toString().trim();
        String passStringStudent=passEditText.getText().toString().trim();
        String nameStudent=nameEditText.getText().toString().trim();
        String IdStudent=idEditText.getText().toString().trim();


        if(IdStudent.isEmpty()){
            idEditText.setError("Must be fill up");
            idEditText.requestFocus();
            return;
        }

        if(nameStudent.isEmpty()){
            nameEditText.setError("Must be fill up");
            nameEditText.requestFocus();
            return;
        }

        if(emailsStudent.isEmpty()){
            emailEditText.setError("Must be fill up");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailsStudent).matches()){
            emailEditText.setError("Please provide valid email !");
            emailEditText.requestFocus();
            return;
        }
        if(passStringStudent.isEmpty()){
            passEditText.setError("Must be fill up");
            passEditText.requestFocus();
            return;
        }
        if(passStringStudent.length()<6){
            passEditText.setError("Password must be at least 6 characters !");
            passEditText.requestFocus();
            return;
        }



        //

        mAuth.createUserWithEmailAndPassword(emailsStudent,passStringStudent).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    user user=new user(nameStudent,emailsStudent,IdStudent);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterPageForStudent.this,"User Has been registerd Successfully !",Toast.LENGTH_LONG).show();
                                FirebaseDatabase.getInstance().getReference("StudentIdEmails").child(IdStudent).setValue(emailsStudent);
                                Intent i = new Intent(RegisterPageForStudent.this, LoginPageForStudent.class);
                                startActivity(i);

                            }
                            else {
                                Toast.makeText(RegisterPageForStudent.this,"Registration failed! Try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(RegisterPageForStudent.this,"Registration failed! Try again!",Toast.LENGTH_LONG).show();
                }

            }
        });


      //
    }


    private void openloginPageForStudent() {
        Intent i = new Intent(this, LoginPageForStudent.class);
        startActivity(i);
    }
}