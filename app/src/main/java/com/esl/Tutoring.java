package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tutoring extends AppCompatActivity {
    Button mark,print;
    Spinner student;
	Spinner tutor;
	TextView subject;
    EditText comment;
    DBConnect db;
	String[] arr1;
	String[] arr2;
	TextView studentNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoring);

        studentNameTextView = (TextView) findViewById(R.id.student_name_textView);
        db = new DBConnect(this);
        final ArrayList<String> list = db.getStudent();
        student = (Spinner) findViewById(R.id.spinner);
        String arr[] = new String[list.size()];
        for (int i = 0; i < arr.length; i++) {
            String info = list.get(i);
            String id = info.substring(0, info.indexOf(","));
            info = info.replace(info.substring(0, info.indexOf(",")+1),"");
            arr[i] = id;
        }
        ArrayList<String> list1 = db.getTutor();

		arr1 = new String[list1.size()];
		arr2 = new String[list1.size()];
		for (int i = 0; i < arr1.length; i++) {
            String str[] = list1.get(i).split(",");
            arr1[i] = str[0];
            arr2[i] = str[1];
        }
        tutor = (Spinner) findViewById(R.id.spinner1);
        subject = (TextView) findViewById(R.id.subject_textView);
        comment = (EditText) findViewById(R.id.comment);

        mark = (Button) findViewById(R.id.mark);
        mark.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mark();
            }
        });

        print = (Button) findViewById(R.id.print);
        print.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                print();
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        student.setAdapter(dataAdapter);

        student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String info = list.get(i);
				String  id  =  info.substring(0, info.indexOf(","));
				info = info.replace(info.substring(0, info.indexOf(",")+1),"");
				String studentName = info;

				if(student.getSelectedItem().equals(id)){
					studentNameTextView.setText(studentName);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        tutor.setAdapter(dataAdapter1);

        tutor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if(tutor.getSelectedItem().equals(arr1[i])){
					subject.setText(arr2[i]);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
//        // Drop down layout style - list view with radio button
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
//        subject.setAdapter(dataAdapter2);
    }

    public void print() {
        //read values from fields
        String sname = student.getSelectedItem().toString();
        boolean flag = db.print(sname,"Tutoring Class");
        if (flag) {
            Toast.makeText(Tutoring.this, "Print request successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Tutoring.this,StudentPage.class);
            startActivity(intent);
        } else {
            //show login fail
            Toast.makeText(Tutoring.this, "Error occured while printing", Toast.LENGTH_LONG).show();
        }
    }

    public void mark() {
        //read values from fields
        String sname = student.getSelectedItem().toString();
        String tname = tutor.getSelectedItem().toString();
        String classname = String.valueOf(subject.getText());
        String comments = comment.getText().toString();
        if (comments.trim().length() == 0 || comments == null) {
            Toast.makeText(Tutoring.this, "Please enter comments", Toast.LENGTH_LONG).show();
            comment.requestFocus();
            return;
        }
        boolean flag = db.tutoringAttendence(sname,tname,classname,comments);
        if (flag) {
            Toast.makeText(Tutoring.this, "Tutoring marking successfull", Toast.LENGTH_LONG).show();
            finish();
        } else {
            //show login fail
            Toast.makeText(Tutoring.this, "Error occured while tutoring marking", Toast.LENGTH_LONG).show();
        }
    }
}


