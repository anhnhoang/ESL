package com.esl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class edittutor extends AppCompatActivity {
    ListView list;
    //list view refrence
    String name[], subject[],tutorphone[];
    DBConnect db;

    //add more

    TextView edit;
    Button tutoredit;

    private static final String TAG = "edittutor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittutor);
        list = (ListView) findViewById(R.id.tutorlistlistview);
        edit = (TextView) findViewById(R.id.edittutorid_text);
        tutoredit = (Button) findViewById(R.id.tutoreditbutton);

        db = new DBConnect(this);


        //get the data and append to a list
//        Cursor data = db.getTutor();
        ArrayList<String> report = db.updateTutor();
        if (report.size() > 0) {
            name = new String[report.size()];
            for (int i = 0; i < report.size(); i++) {
                name[i] = report.get(i);
            }
//            ArrayList<String> listData = new ArrayList<>();
//        while(data.moveToNext()){
//            //get the value from the database in column 1
//            //then add it to the ArrayList
//            listData.add(data.getString(1));
//        }




            ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
            ListView listView = (ListView) findViewById(R.id.tutorlistlistview);
            listView.setAdapter(listAdapter);
//
//            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//            list.setAdapter(adapter);
//            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String name = edit.getText().toString();
////                    String name = adapterView.getItemAtPosition(i).toString();
//                    Log.d(TAG, "onItemClick: You Clicked on " + name);
//
//                    Cursor data = db.getItemID(name); //get the id associated with that name
//                    int itemID = 0;
//                    while (data.moveToNext()) {
//                        itemID = data.getInt(0);
//                    }
//                    if (itemID > 0) {
//                        Log.d(TAG, "onItemClick: The ID is: " + itemID);
//                        Intent editScreenIntent = new Intent(edittutor.this, edittutoractivity.class);
//                        editScreenIntent.putExtra("id", itemID);
//                        editScreenIntent.putExtra("name", name);
//                        startActivity(editScreenIntent);
//                    } else {
//                        toastMessage("No ID associated with that name");
//                    }
//                }
//            });

            tutoredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = edit.getText().toString();
//                    String name = adapterView.getItemAtPosition(i).toString();
                    Log.d(TAG, "onItemClick: You Clicked on " + id);

                    Cursor data = db.getItemID(id); //get the id associated with that name
                    int itemID = 0;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                    if (itemID > 0) {
                        Log.d(TAG, "onItemClick: The ID is: " + itemID);
                        Intent editScreenIntent = new Intent(edittutor.this, edittutoractivity.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("name", id);
                        startActivity(editScreenIntent);
                    } else {
                        toastMessage("No ID associated with that name");
                    }
                }

            });

        }
    }
        //create the list adapter and set the adapter


        //set an onItemClickListener to the ListView




    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
