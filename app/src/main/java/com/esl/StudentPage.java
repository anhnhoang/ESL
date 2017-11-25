package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class StudentPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentpage);
        initClickListner();
    }
    private void initClickListner()	{
        Button lab = (Button) findViewById(R.id.lab);
        lab.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(StudentPage.this,Lab.class);
                startActivity(intent);
            }
        });
        Button tutoring = (Button) findViewById(R.id.tutoring);
        tutoring.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StudentPage.this,Tutoring.class);
                startActivity(intent);
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });

    }




}
