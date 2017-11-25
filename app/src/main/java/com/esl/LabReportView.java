package com.esl;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LabReportView extends Activity {
    ListView list;//list view refrence
    ArrayList<String> name;
	ViewActivity adapter;
	DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);

        EditText searchEditText = (EditText) findViewById(R.id.search_editText);
		db = new DBConnect(this);
		String stime = getIntent().getExtras().getString("stime");
		String etime = getIntent().getExtras().getString("etime");
		ArrayList<String> report = db.getLabReport(Long.parseLong(stime), Long.parseLong(etime));
		if(report.size() > 0) {
			name = report;

			adapter = new ViewActivity(LabReportView.this, name);
			list = (ListView) findViewById(R.id.list);
			list.setAdapter(adapter);
		}else{
			Toast.makeText(LabReportView.this, "No record found", Toast.LENGTH_LONG).show();
			finish();
		}
		searchEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				adapter.getFilter().filter(charSequence);

			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		ImageView printButton = (ImageView) findViewById(R.id.print_Button);
		printButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				DisplayMetrics displaymetrics = new DisplayMetrics();
				LabReportView.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

				float hight = displaymetrics.heightPixels ;
				float width = displaymetrics.widthPixels ;
				int convertHighet = (int) hight, convertWidth = (int) width;


				// crate a page description
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
					PdfDocument document = new PdfDocument();
					PdfDocument.PageInfo pageInfo = null;
					pageInfo = new PdfDocument.PageInfo.Builder(convertWidth,convertHighet, 1).create();
					PdfDocument.Page page = document.startPage(pageInfo);
					Canvas canvas = page.getCanvas();


					final float STUDENT_COLUMN = 100;
					final float DATE_COLUMN = 550;
					final float TIME_COLUMN = 1100;
					Paint paint = new Paint();
					paint.setColor(Color.WHITE);
					paint.setStyle(Paint.Style.FILL);
					canvas.drawPaint(paint);


					paint.setColor(Color.BLACK);
					canvas.drawRect(70, 270, 1300, 420,paint);

					paint.setTextSize(120);
					canvas.drawText("LAB REPORT", 350, 200, paint);
					paint.setTextSize(80);
					paint.setColor(Color.WHITE);
					canvas.drawText("Student", STUDENT_COLUMN, 400, paint);
					canvas.drawText("Date", DATE_COLUMN, 400, paint);
					canvas.drawText("Time", TIME_COLUMN, 400, paint);

					paint.setColor(Color.BLACK);
					paint.setTextSize(50);

					float lineSpace = 400;
					int i = 0;
					while(i<name.size()){
						lineSpace += 80;
						String info = name.get(i);
						String  studentName  =  info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String date = info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String time = info;
						canvas.drawText(studentName, STUDENT_COLUMN, lineSpace, paint);
						canvas.drawText(date, DATE_COLUMN, lineSpace, paint);
						canvas.drawText(time, TIME_COLUMN, lineSpace, paint);

						i++;
					}



					document.finishPage(page);


					// write the document content
					String targetPdf = "/sdcard/test.pdf";
					File filePath = new File(targetPdf);
					try {
						document.writeTo(new FileOutputStream(filePath));
						Toast.makeText(LabReportView.this, "Report Saved", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(LabReportView.this, "Something wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}

					// close the document
					document.close();

				}

				// start a page

				// draw something on the page


				// finish the page

				// write the document content

				// close the document

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

    	switch (item.getItemId()){
			case R.id.action_search:
				Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.action_print:
				Toast.makeText(this, "print", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);


		}
	}
}
