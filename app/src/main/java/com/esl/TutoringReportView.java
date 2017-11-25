package com.esl;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TutoringReportView extends Activity {
    ListView list;//list view refrence
    ArrayList<String> name;
	TutoringReportAdapter adapter;
    DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);
        TextView titleTextView = (TextView) findViewById(R.id.toolbar_title);
        titleTextView.setText("Tutor Report");
        db = new DBConnect(this);
        String stime = getIntent().getExtras().getString("stime");
        String etime = getIntent().getExtras().getString("etime");
        ArrayList<String> report = db.getTutoringReport(Long.parseLong(stime), Long.parseLong(etime));
        if(report.size() > 0) {
            name = report;

            adapter = new TutoringReportAdapter(TutoringReportView.this, name);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
        }else{
            Toast.makeText(TutoringReportView.this, "No record found", Toast.LENGTH_LONG).show();
            finish();
        }

		EditText searchEditText = (EditText) findViewById(R.id.search_editText);
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
				TutoringReportView.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

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


					final float STUDENT_COLUMN = 70;
					final float TUTOR_COLUMN = 420;
					final float SUBJECT_COLUMN = 770;
					final float COMMENT_COLUMN = 1100;
					final float DATE_COLUMN = 1500;
					Paint paint = new Paint();
					paint.setColor(Color.WHITE);
					paint.setStyle(Paint.Style.FILL);
					canvas.drawPaint(paint);


					paint.setColor(Color.BLACK);
					canvas.drawRect(40, 300, 1999, 420,paint);

					paint.setTextSize(120);
					canvas.drawText("TUTOR REPORT", 350, 200, paint);
					paint.setTextSize(60);
					paint.setColor(Color.WHITE);
					canvas.drawText("Student", STUDENT_COLUMN, 400, paint);
					canvas.drawText("Tutor", TUTOR_COLUMN, 400, paint);
					canvas.drawText("Subject", SUBJECT_COLUMN, 400, paint);
					canvas.drawText("Comment", COMMENT_COLUMN, 400, paint);
					canvas.drawText("Date", DATE_COLUMN, 400, paint);

					paint.setColor(Color.BLACK);
					paint.setTextSize(50);

					float lineSpace = 400;
					int i = 0;
					while(i<name.size()){
						lineSpace += 80;
						String info = name.get(i);
						String  studentName  =  info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String tutorName = info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String subject =  info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String comment =  info.substring(0, info.indexOf(","));
						info = info.replace(info.substring(0, info.indexOf(",")+1),"");
						String time =  info;

						canvas.drawText(studentName, STUDENT_COLUMN, lineSpace, paint);
						canvas.drawText(tutorName, TUTOR_COLUMN, lineSpace, paint);
						canvas.drawText(subject, SUBJECT_COLUMN, lineSpace, paint);
						canvas.drawText(comment, COMMENT_COLUMN, lineSpace, paint);
						canvas.drawText(time, DATE_COLUMN, lineSpace, paint);

						i++;
					}



					document.finishPage(page);


					// write the document content
					String targetPdf = "/sdcard/tutorRepot.pdf";
					File filePath = new File(targetPdf);
					try {
						document.writeTo(new FileOutputStream(filePath));
						Toast.makeText(TutoringReportView.this, "Report Saved", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(TutoringReportView.this, "Something wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}

					// close the document
					document.close();

				}


			}
		});
    }
}
