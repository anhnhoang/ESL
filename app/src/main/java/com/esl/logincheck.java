package com.esl;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class logincheck extends AppCompatActivity {

    Button signup;
    EditText username, pass1, pass2;
    TextView nametv, pastv, pas2tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincheck);
//        DBConnect db = new DBConnect(this);

//        nametv = (TextView) findViewById(R.id.username_textview);
//        pastv = (TextView) findViewById(R.id.pass_textview);
//        pas2tx = (TextView) findViewById(R.id.repass_textview);
        signup = (Button) findViewById(R.id.buttoncheck);

        username = (EditText) findViewById(R.id.username_text);
        pass1 = (EditText) findViewById(R.id.pass_text);
        pass2 = (EditText) findViewById(R.id.repass_text);
        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    verifyuser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void verifyuser() throws IOException {
        String name = username.getText().toString();
        String password = pass1.getText().toString();
        String veripass = pass2.getText().toString();
        DBConnect db = new DBConnect(this);
        if (name.trim().length() != 0) {
            if (name.length() > 3 && password.length() > 3 && veripass.length() > 3) {
                if (password.equals(veripass)) {
                    boolean flag = true;
                    for (int p = 0; p < password.length(); p++) {
                        if (Character.isUpperCase(password.charAt(p))) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag) {
                        db.addStudent(password, name);
                        Toast.makeText(logincheck.this, "Student details added", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit();
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(logincheck.this, "Password must contain at least one upperCase", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(logincheck.this, "password not match ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(logincheck.this, "Username and password at least 3 chars", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(logincheck.this, "Please enter user name", Toast.LENGTH_LONG).show();
            nametv.requestFocus();
        }
    }
}

// 1-10-1
// 1-2-9-2
//1-2-3-8-3
//1-2-3-4-5-7-4
//1-2-3-4-5-6

//    int upperCase = 0;
//    int lowerCase = 0;
//    int numberCount = 0;
//    int specialCharCount = 0;
//                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
//                    for (char c : dataIn.readLine().toCharArray()) {
//                        if (Character.isUpperCase(c)) {
//                            upperCase++;
//                        } else if (Character.isLowerCase(c)) {
//                            lowerCase++;
//                        } else if (Character.isDigit(c)) {
//                            numberCount++;
//                        } else {
//                            specialCharCount++;
//                        }

//                    }
//                    Toast.makeText(logincheck.this, "Your password contains %d uppercases, %d lowercases, %d digits and %d special characters.\n\n" + upperCase + lowerCase + numberCount +
//                            specialCharCount, Toast.LENGTH_LONG).show();