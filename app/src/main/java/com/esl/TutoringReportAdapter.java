package com.esl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Coder on 18/11/2017.
 */

public class TutoringReportAdapter extends ArrayAdapter<String> {
	private final Activity context;
	ArrayList<String> names;

	private ArrayList<String> filteredData = null;
	private ItemFilter mFilter = new ItemFilter();

	public TutoringReportAdapter(Activity context, ArrayList<String> names) {
		super(context, R.layout.activity_list, names);
		//local to global value initialization
		this.context = context;
		this.names = names;
		this.filteredData = names;
	}

	//automatically call this method to show entire list of array in list view
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_item, null, true);
		TextView tutorNameTextView = (TextView) rowView.findViewById(R.id.title_textView);
		TextView dateTextView = (TextView) rowView.findViewById(R.id.sub_title_textView);
		TextView timeNameTextView = (TextView) rowView.findViewById(R.id.side_textView);

		String info = getItem(position);
		String  studentName  =  info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String tutorName = info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String subject =  info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String comment =  info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String time =  info;

		final String Detail =
						"Student Name:	"+ studentName + "\n"+
						"Tutor Name:	"+tutorName+ "\n"+
						"Subject:		"+subject+ "\n"+
						"Comment:		"+comment+ "\n"+
						"Date:			"+ time+ "\n";

		tutorNameTextView.setText(tutorName);
		dateTextView.setText(subject);
		timeNameTextView.setText(time);

		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(Detail).create().show();

			}
		});
		return rowView;
	}

	@Override
	public int getCount() {
		return filteredData.size();
	}

	@Nullable
	@Override
	public String getItem(int position) {
		return filteredData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@NonNull
	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase();

			FilterResults results = new FilterResults();

			final ArrayList<String> list = names;

			int count = list.size();
			final ArrayList<String> nlist = new ArrayList<String>(count);

			String filterableString ;

			for (int i = 0; i < count; i++) {
				filterableString = list.get(i);
				if (filterableString.toLowerCase().contains(filterString)) {
					nlist.add(filterableString);
				}
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filteredData = (ArrayList<String>) results.values;
			notifyDataSetChanged();
		}

	}
}
