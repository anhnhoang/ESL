package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class DirectorPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director);
        initClickListner();
    }
    private void initClickListner()	{
        Button newtutor = (Button) findViewById(R.id.newtutor);
        newtutor.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,AddTutor.class);
                startActivity(intent);
            }
        });
        Button newstudent = (Button) findViewById(R.id.newstudent);
        newstudent.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,AddStudent.class);
                startActivity(intent);
            }
        });

        Button labreport = (Button) findViewById(R.id.viewlabreport);
        labreport.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,LabReport.class);
                startActivity(intent);
            }
        });

        Button tutorreport = (Button) findViewById(R.id.viewtutorreport);
        tutorreport.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,TutoringReport.class);
                startActivity(intent);
            }
        });

       Button tutoringGraph = (Button) findViewById(R.id.tutoring_graph);
        tutoringGraph.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,TutorGraph.class);
                startActivity(intent);
            }
        });

        Button labGraph = (Button) findViewById(R.id.lab_graph);
        labGraph.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,LabGraphActivity.class);
                startActivity(intent);
            }
        });
        //Editt tutor
        Button edittutor = (Button) findViewById(R.id.edittutorbtn);
        edittutor.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,edittutor.class);
                startActivity(intent);
            }
        });

        //delete studenttutor
        Button deleteStudent = (Button) findViewById(R.id.deleteStudent);
        deleteStudent.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DirectorPage.this,Deletestudent.class);
                startActivity(intent);
            }
        });




        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });

    }






}
