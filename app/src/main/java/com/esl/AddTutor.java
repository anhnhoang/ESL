package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddTutor extends AppCompatActivity {
    EditText tutorname, classname,phone;
    Button save;
    DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtutor);
        tutorname = (EditText) findViewById(R.id.tutorname);
        classname = (EditText) findViewById(R.id.classname);
        phone = (EditText) findViewById(R.id.phone);
        db = new DBConnect(this);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void save() {
        //read values from fields
        String s1 = tutorname.getText().toString();
        String s2 = classname.getText().toString();
        String s3 = phone.getText().toString();
        if (s1.trim().length() == 0 || s1 == null) {
            Toast.makeText(AddTutor.this, "Please enter tutor name", Toast.LENGTH_LONG).show();
            tutorname.requestFocus();
            return;
        }
        if (s2.trim().length() == 0 || s2 == null) {
            Toast.makeText(AddTutor.this, "Please enter class name", Toast.LENGTH_LONG).show();
            classname.requestFocus();
            return;
        }
        if (s3.trim().length() == 0 || s3 == null) {
            Toast.makeText(AddTutor.this, "Please enter phone no", Toast.LENGTH_LONG).show();
            phone.requestFocus();
            return;
        }
        boolean flag = db.addTutor(s1,s2,s3);
        if (flag) {
            Toast.makeText(AddTutor.this, "Tutor details added", Toast.LENGTH_LONG).show();
            finish();
        } else {
            //show login fail
            Toast.makeText(AddTutor.this, "Tutor name already exists", Toast.LENGTH_LONG).show();
        }
    }
}

