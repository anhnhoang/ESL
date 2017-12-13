package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClickListner();
    }
    private void initClickListner()	{
        Button director = (Button) findViewById(R.id.director);
        director.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });
        Button students = (Button) findViewById(R.id.students);
        students.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StudentPage.class);
                startActivity(intent);
            }
        });

        Button helpButton  = (Button) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, HelpActivity.class));
			}
		});

        //Signup for student
        Button studentsignup = (Button) findViewById(R.id.studentsignup_btn);
        studentsignup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,logincheck.class);
                startActivity(intent);
            }
        });
        //LoginStudent
        Button loginstudent = (Button) findViewById(R.id.studentloginbtn);
        loginstudent.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,loginstudent.class);
                startActivity(intent);
            }
        });

    }




}
