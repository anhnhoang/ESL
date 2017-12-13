package com.esl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class studentsignup extends AppCompatActivity {

    EditText sid, password;
    Button signup;
    DBConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup);

        sid = (EditText) findViewById(R.id.studentPassword);
        password = (EditText) findViewById(R.id.studentName_text);
        db = new DBConnect(this);
        signup = (Button) this.findViewById(R.id.signupbtn);

//        signup = (Button) findViewById(R.id.studentsignup_btn);

//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addstudent();
//            }
//        };
//
//        signup.setOnClickListener(listener);

        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    addstudent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void addstudent() {
        //read values from fields
        String s1 = sid.getText().toString();
        String s2 = password.getText().toString();

        if (s1.trim().length() == 0 || s1 == null) {
            Toast.makeText(studentsignup.this, "Please enter student id", Toast.LENGTH_LONG).show();
            sid.requestFocus();
            return;
        }
        if (s2.trim().length() == 0 || s2 == null) {
            Toast.makeText(studentsignup.this, "Please enter student name", Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        boolean flag = db.addStudent(s1,s2);
        if (flag) {
            Toast.makeText(studentsignup.this, "Student details added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(studentsignup.this,MainActivity.class);
            startActivity(intent);
        } else {
            //show login fail
            Toast.makeText(studentsignup.this, "Student id or name already exists", Toast.LENGTH_LONG).show();
        }
    }

    //please enter student id
    //check student id is match in database?
    //check password are equal
    //check it sign up for tutor or student signup



}
