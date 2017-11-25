package com.esl;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LabReport extends Activity implements View.OnClickListener {
    EditText start,end;
    DatePickerDialog start_date,end_date;
    Button submit;
    SimpleDateFormat format;
    long stime,etime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labreport);
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        start = (EditText) findViewById(R.id.fdate);
        start.setInputType(InputType.TYPE_NULL);

        end = (EditText) findViewById(R.id.tdate);
        end.setInputType(InputType.TYPE_NULL);

        submit =(Button) findViewById(R.id.fetch);
        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try{
                    apply();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        setDateTimeField();
    }
    public void apply() {
        Intent intent = new Intent(LabReport.this, LabReportView.class);
        intent.putExtra("stime", Long.toString(stime));
        intent.putExtra("etime", Long.toString(etime));
        startActivity(intent);
    }
    private void setDateTimeField() {
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        start_date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                stime = newDate.getTimeInMillis();
                start.setText(format.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        end_date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etime = newDate.getTimeInMillis();
                end.setText(format.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if(view == start) {
            start_date.show();
        } else if(view == end) {
            end_date.show();
        }
    }
}
