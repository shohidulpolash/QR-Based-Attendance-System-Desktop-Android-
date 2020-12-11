package com.example.smartattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeachersPageAfterLogin extends AppCompatActivity {
    private Button abutton,bbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_page_after_login);
        abutton=findViewById(R.id.takeAttendanceTeacherAfterLoginPage);
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTakeAttendace();
            }
        });
        bbutton=findViewById(R.id.showAttendanceTeacherAfterLoginPage);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowAttendance();
            }
        });
    }

    private void openShowAttendance() {
        Intent i=new Intent(this,show_Attendance.class);
        startActivity(i);
    }

    private void openTakeAttendace() {
        Intent i=new Intent(this,TakeAttendance.class);
        startActivity(i);
    }
}