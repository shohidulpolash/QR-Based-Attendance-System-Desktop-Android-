package com.example.smartattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_Attendance extends AppCompatActivity {
    private TextView DTextView;
    private DatePickerDialog datepickerdialog;
    String monthString, finalDate;
    private ListView listView;
    private EditText courseCode, section;
    private Button Nbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attendance);

        DTextView = findViewById(R.id.textViewForDateShowAttendancePage);
        DTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDateSelector();
            }
        });


        courseCode = findViewById(R.id.editTextForCourseCodeShowAttendancePAge);
        section = findViewById(R.id.editTextForSectionShowAttendancePAge);


        Nbutton = findViewById(R.id.goButtonShowAttendance);
        Nbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTakeData();
            }
        });





    }


    private void openDateSelector() {

        DatePicker dp = new DatePicker(show_Attendance.this);
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year = dp.getYear();
        datepickerdialog = new DatePickerDialog(show_Attendance.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        if (i1 == 1)
                            monthString = "JANUARY ";
                        else if (i1 == 2)
                            monthString = "FEBRUARY ";
                        else if (i1 == 3)
                            monthString = "MARCH ";
                        else if (i1 == 4)
                            monthString = "APRIL ";
                        else if (i1 == 5)
                            monthString = "MAY ";
                        else if (i1 == 6)
                            monthString = "JUNE ";
                        else if (i1 == 7)
                            monthString = "JULY ";
                        else if (i1 == 8)
                            monthString = "AUGUST ";
                        else if (i1 == 9)
                            monthString = "SEPTEMBER ";
                        else if (i1 == 10)
                            monthString = "OCTOBER ";
                        else if (i1 == 11)
                            monthString = "NOVEMBER ";
                        else if (i1 == 12)
                            monthString = "DECEMBER ";


                        String date = monthString.concat(Integer.toString(i2)).concat(", ").concat(Integer.toString(i));
                        finalDate = date;
                        //finalDate = date.toUpperCase();
                        DTextView.setText(finalDate);
                    }
                }, year, month, day);

        datepickerdialog.show();
    }


    private void openTakeData() {
        String course = courseCode.getText().toString().toUpperCase();
        String sec = section.getText().toString().toUpperCase();
        String finalPath = finalDate.concat(course).concat(sec).toString();
       // DTextView.setText(finalPath);
        listView=findViewById(R.id.listViewshowStudent);
        ArrayList<String> list= new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(finalPath);

        reference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
              for(DataSnapshot snapshot:datasnapshot.getChildren()){
                 // list.add(snapshot.getKey().toString());
                  list.add(snapshot.getValue().toString());
              }
              adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}