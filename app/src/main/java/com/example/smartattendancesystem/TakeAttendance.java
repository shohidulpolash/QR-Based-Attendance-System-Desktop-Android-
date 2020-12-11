package com.example.smartattendancesystem;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.text.DateFormat;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TakeAttendance extends AppCompatActivity {
   private EditText courseEditText,sectionEditText;
   private Button pl;
   private ImageView qrImage;
    private DatabaseReference mDatabase;
    private DatePickerDialog datepickerdialog;
    private TextView textView;
    String monthString1,finalDate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        courseEditText=findViewById(R.id.editTextForCourseCode);
        courseEditText.requestFocus();
        sectionEditText=findViewById(R.id.editTextForSection);

        qrImage=findViewById(R.id.QrView);

        pl=findViewById(R.id.ShowQR);
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenQRgenarateTask();
            }
        });


        textView=findViewById(R.id.textViewForDateTakeAttendancePage);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengetDate();
            }
        });


    }

    private void opengetDate() {

        DatePicker dp = new DatePicker(TakeAttendance.this);
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year = dp.getYear();
        datepickerdialog = new DatePickerDialog(TakeAttendance.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        if (i1 == 1)
                            monthString1 = "JANUARY ";
                        else if (i1 == 2)
                            monthString1 = "FEBRUARY ";
                        else if (i1 == 3)
                            monthString1 = "MARCH ";
                        else if (i1 == 4)
                            monthString1 = "APRIL ";
                        else if (i1 == 5)
                            monthString1 = "MAY ";
                        else if (i1 == 6)
                            monthString1 = "JUNE ";
                        else if (i1 == 7)
                            monthString1 = "JULY ";
                        else if (i1 == 8)
                            monthString1 = "AUGUST ";
                        else if (i1 == 9)
                            monthString1 = "SEPTEMBER ";
                        else if (i1 == 10)
                            monthString1 = "OCTOBER ";
                        else if (i1 == 11)
                            monthString1 = "NOVEMBER ";
                        else if (i1 == 12)
                            monthString1 = "DECEMBER ";


                        String date1 = monthString1.concat(Integer.toString(i2)).concat(", ").concat(Integer.toString(i));
                        finalDate1 = date1;
                        //finalDate = date.toUpperCase();
                        textView.setText(finalDate1);
                    }
                }, year, month, day);

        datepickerdialog.show();
    }

    private void OpenQRgenarateTask() {
        String secString,courseCodeString;
        secString=sectionEditText.getText().toString().toUpperCase();
        courseCodeString=courseEditText.getText().toString().toUpperCase();

        String finalStr=finalDate1.concat(courseCodeString);
        String finalString=finalStr.concat(secString).toUpperCase();

        QRGEncoder qrgEncoder=new QRGEncoder(finalString,null, QRGContents.Type.TEXT,1000);
        Bitmap bitmap = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(bitmap);


    }
}