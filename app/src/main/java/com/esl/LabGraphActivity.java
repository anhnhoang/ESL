package com.esl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LabGraphActivity extends AppCompatActivity implements View.OnClickListener, OnChartValueSelectedListener{
	EditText start,end;
	DatePickerDialog start_date,end_date;
	Button submit;
	SimpleDateFormat format;
	long stime,etime;
	DBConnect db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lab_graph);


		format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

		start = (EditText) findViewById(R.id.fdate);
		start.setInputType(InputType.TYPE_NULL);

		end = (EditText) findViewById(R.id.tdate);
		end.setInputType(InputType.TYPE_NULL);

		submit =(Button) findViewById(R.id.fetch);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try{
					apply();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		setDateTimeField();
	}
	public void apply() {

		db = new DBConnect(this);
		LinkedHashMap<String,Integer> map = db.getLabGraph(stime, etime);
		PieChart pieChart = new PieChart(this);
		pieChart.clearAnimation();
		if(map.size() != 0) {
			pieChart = (PieChart) findViewById(R.id.piechart);
			pieChart.setUsePercentValues(true);
			ArrayList<Entry> yvalues = new ArrayList<Entry>();
			ArrayList<String> xVals = new ArrayList<String>();
			for (Map.Entry<String, Integer> me : map.entrySet()) {
				String key = me.getKey();
				String value = me.getValue() + "";
				xVals.add(key);
				yvalues.add(new Entry(Float.parseFloat(value), 0));
			}
			PieDataSet dataSet = new PieDataSet(yvalues, "Lab Graph");

			PieData data = new PieData(xVals, dataSet);
			data.setValueFormatter(new PercentFormatter());
			pieChart.setData(data);


			pieChart.setDrawHoleEnabled(true);
			pieChart.setTransparentCircleRadius(25f);
			pieChart.setHoleRadius(25f);

			dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
			data.setValueTextSize(13f);
			data.setValueTextColor(Color.DKGRAY);
			pieChart.setOnChartValueSelectedListener(this);

			pieChart.animateXY(1400, 1400);
		}else{
			Toast.makeText(this, "No records found for selected dates.", Toast.LENGTH_SHORT).show();
		}
	}
	private void setDateTimeField() {
		start.setOnClickListener(this);
		end.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();
		start_date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				stime = newDate.getTimeInMillis();
				start.setText(format.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		end_date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				etime = newDate.getTimeInMillis();
				end.setText(format.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}
	@Override
	public void onClick(View view) {
		if(view == start) {
			start_date.show();
		} else if(view == end) {
			end_date.show();
		}
	}
	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

		if (e == null)
			return;
		Log.i("VAL SELECTED",
				"Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
						+ ", DataSet index: " + dataSetIndex);
	}

	@Override
	public void onNothingSelected() {
		Log.i("PieChart", "nothing selected");
	}

}
