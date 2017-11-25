package com.esl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		DBConnect db = new DBConnect(this);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.help_recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		ArrayList<String> tutorArrayList;
		tutorArrayList = db.getTutorDetails();
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tutorArrayList);
		recyclerView.setAdapter(adapter);
	}
}
