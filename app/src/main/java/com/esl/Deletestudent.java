package com.esl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Deletestudent extends AppCompatActivity {
    EditText sid,tutorname;
    Button  delete,delete2;
    DBConnect db;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletestudent);
        sid = (EditText) findViewById(R.id.deletestudenttext);
        tutorname = (EditText) findViewById(R.id.delete_tutortext);
        db = new DBConnect(this);
        delete = (Button) findViewById(R.id.DeleteStudent);
        delete2 = (Button) findViewById(R.id.delete_tutor_button);





        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.DeleteStudent):
                        deletestudent();
                    case (R.id.delete_tutor_button):
                        try {
                            delete_Tutor();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
        };

        delete.setOnClickListener(listener);
        delete2.setOnClickListener(listener);

//        delete2.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    delete_Tutor();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
    }


    public void deletestudent(){
        Toast.makeText(Deletestudent.this, "Deleted Student", Toast.LENGTH_LONG).show();
        db.delete_Student(sid.getText().toString());
    }
    public void delete_Tutor(){
        Toast.makeText(Deletestudent.this, "Deleted Tutor", Toast.LENGTH_LONG).show();
        db.delete_Tutor(tutorname.getText().toString());

    }


}


