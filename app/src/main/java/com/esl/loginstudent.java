package com.esl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginstudent extends AppCompatActivity {

    EditText user, pass;
    Button sign;
    DBConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstudent);
        user = (EditText) findViewById(R.id.snamelogintext);
        pass = (EditText) findViewById(R.id.studentpasswordlogintext);
        db = new DBConnect(this);

        sign = (Button) findViewById(R.id.buttonlogin);
        sign.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName=user.getText().toString();
                String password=pass.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=db.getStudentLogin(userName);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {
                    Intent intent = new Intent(loginstudent.this,Lab.class);
                    Toast.makeText(loginstudent.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
//                    dialog.dismiss();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(loginstudent.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
