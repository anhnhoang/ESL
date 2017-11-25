package com.esl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Coder on 17/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

	Context context;
	ArrayList<String> tutorList;

	public RecyclerViewAdapter(Context context, ArrayList<String> turorList) {
		this.context = context;
		this.tutorList = turorList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false );
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {


		TextView tutorNameTextView = (TextView) holder.myView.findViewById(R.id.title_textView);
		TextView phoneNoTextView = (TextView) holder.myView.findViewById(R.id.sub_title_textView);
		TextView subjectTextView = (TextView) holder.myView.findViewById(R.id.side_textView);

		String info = tutorList.get(position);
		String  tutorName  =  info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String subject = info.substring(0, info.indexOf(","));
		info = info.replace(info.substring(0, info.indexOf(",")+1),"");
		String phoneNo = info;

		tutorNameTextView.setText(tutorName);
		subjectTextView.setText(subject);
		phoneNoTextView.setText(phoneNo);
	}

	@Override
	public int getItemCount() {
		return tutorList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder{

		View myView;
		public ViewHolder(View itemView) {
			super(itemView);
			myView = itemView;
		}
	}
}
