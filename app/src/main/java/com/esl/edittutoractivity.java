package com.esl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edittutoractivity extends AppCompatActivity {

    private Button editphone;
    private EditText editText;
    DBConnect db;

    String phone_no;
    String id;
//    private int selectedID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittutoractivity);

        editphone = (Button) findViewById(R.id.savephone);
        editText = (EditText) findViewById(R.id.phone_numbertext);

        db = new DBConnect(this);

        Intent receviedIntent = getIntent();
        id = receviedIntent.getStringExtra("name");
//        phone_no = receviedIntent.getStringExtra("phone_no");



        editText.setText(id);
        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editText.getText().toString();
                try {
                    db.updatePhone(item,id);
                    Toast.makeText(edittutoractivity.this, "Edited phone number", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(edittutoractivity.this,edittutor.class);
                    startActivity(intent);
                } catch (Exception name) {

                }
            }
        });
    }

}
