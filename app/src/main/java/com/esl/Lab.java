package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lab extends AppCompatActivity {
    Button mark,print;
    Spinner spinner;
    DBConnect db;
	TextView studentNameTextView;
	ArrayList<String> list;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);

		studentNameTextView = (TextView) findViewById(R.id.student_name);
		db = new DBConnect(this);
		list = db.getStudent();
		spinner = (Spinner) findViewById(R.id.spinner);
        final String arr[] = new String[list.size()];
        for (int i = 0; i < arr.length; i++) {
            String info= list.get(i);
			String  id  =  info.substring(0, info.indexOf(","));
			info = info.replace(info.substring(0, info.indexOf(",")+1),"");
			String studentName = info;

			arr[i] = id;

		}
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
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String info = list.get(i);
				String  id  =  info.substring(0, info.indexOf(","));
				info = info.replace(info.substring(0, info.indexOf(",")+1),"");
				String studentName = info;

				if(spinner.getSelectedItem().equals(id)){
					studentNameTextView.setText(getString(R.string.stedent_name) + studentName);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
    }

    public void print() {
        //read values from fields
        String sname = spinner.getSelectedItem().toString();
        boolean flag = db.print(sname,"Lab");
        if (flag) {
            Toast.makeText(Lab.this, "Print request successfull", Toast.LENGTH_LONG).show();
            finish();
        } else {
            //show login fail
            Toast.makeText(Lab.this, "Error occured while printing", Toast.LENGTH_LONG).show();
        }
    }
    public void mark() {
        //read values from fields
		String sname = spinner.getSelectedItem().toString();
		String student = String.valueOf(studentNameTextView.getText());
        boolean flag = db.labAttendence(sname+","+student);
        if (flag) {
            Toast.makeText(Lab.this, "Lab marking successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Lab.this,StudentPage.class);
            startActivity(intent);
            finish();
        } else {
            //show login fail
            Toast.makeText(Lab.this, "Error occured while lab marking", Toast.LENGTH_LONG).show();
        }
    }
}


