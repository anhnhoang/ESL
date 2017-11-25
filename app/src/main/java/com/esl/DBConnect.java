package com.esl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedHashMap;
public class DBConnect extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "esldb.db";
	private Context context;
	private HashMap hp;

    public DBConnect(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table tutor(tutorname text primary key,subject text,phone_no text)");
        db.execSQL("create table student(studentid text primary key,studentname text)");
        db.execSQL("create table labattendence(studentid text,enter_time long)");
        db.execSQL("create table tutoringattendence(studentname text,tutorname text,subjectname text,enter_time long,comments text)");
        db.execSQL("create table print(studentid text,print_time long,print_type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tutor");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS labattendence");
        db.execSQL("DROP TABLE IF EXISTS tutoringattendence");
        db.execSQL("DROP TABLE IF EXISTS print");
        onCreate(db);
    }

    public boolean print(String student,String type){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", student);
        contentValues.put("print_time", time.getTime());
        contentValues.put("print_type", type);
        db.insert("print", null, contentValues);
        return true;
    }

    public boolean addStudent(String id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", id);
        contentValues.put("studentname", name);
        db.insert("student", null, contentValues);
        return true;
    }

    public boolean labAttendence(String name){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", name);
        contentValues.put("enter_time", time.getTime());
		Log.d("TAG", String.valueOf(time.getTime()));
		db.insert("labattendence", null, contentValues);
        return true;
    }

    public boolean tutoringAttendence(String name,String tutor,String subject,String comment){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentname", name);
        contentValues.put("tutorname", tutor);
        contentValues.put("subjectname", subject);
        contentValues.put("enter_time", time.getTime());
        contentValues.put("comments", comment);
        db.insert("tutoringattendence", null, contentValues);
        return true;
    }

    public boolean addTutor(String tutor,String subject,String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tutorname",tutor);
        contentValues.put("subject",subject);
        contentValues.put("phone_no",phone);
        db.insert("tutor", null, contentValues);
        return true;
    }



    public ArrayList<String> getStudent() {
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student",null);
        while(res.moveToNext()) {
        	String id = res.getString(res.getColumnIndex("studentid"));
        	String studentName = res.getString(res.getColumnIndex("studentname"));
            sname.add(id+","+studentName);
        }
        return sname;
    }

    public ArrayList<String> getTutor() {
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tutor",null);
        while(res.moveToNext()) {
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            String subject = res.getString(res.getColumnIndex("subject"));
            sname.add(tutor+","+subject);
        }
        return sname;
    }

	public ArrayList<String> getTutorDetails() {
		ArrayList<String> sname = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from tutor",null);
		while(res.moveToNext()) {
			String tutor = res.getString(res.getColumnIndex("tutorname"));
			String subject = res.getString(res.getColumnIndex("subject"));
			String phoneNo = res.getString(res.getColumnIndex("phone_no"));
			sname.add(tutor+","+subject+ ","+ phoneNo);
		}
		return sname;
	}

    public LinkedHashMap<String,Integer> getAttendedTutor() {
        LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select tutorname from tutoringattendence",null);
        while(res.moveToNext()) {
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            if(map.containsKey(tutor)){
                map.put(tutor,(map.get(tutor)+1));
            }else {
                map.put(tutor, 1);
            }
        }
        return map;
    }
    //Student Login function

    public String getStudentLogin(String studentname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query("student", null, " studentname=?", new String[]{studentname}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("studentid"));
        cursor.close();
        return password;
    }
    //edittutor Gettuor

    public ArrayList<String> updateTutor() {
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tutor",null);
        while(res.moveToNext()) {
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            String subject = res.getString(res.getColumnIndex("subject"));
            String phone_no = res.getString(res.getColumnIndex("phone_no"));
            sname.add(tutor+","+subject+","+phone_no);
        }
        return sname;
    }
    //delete student
    public boolean delete_Student(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("student", "studentid" + "="+id,null);
        return true;
    }

//delete tutor

    public boolean delete_Tutor(String tutor){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("tutor", "tutorname = ?", new String[] {"tutor"});
        db.delete("tutor", "tutorname" + " = '" + tutor + "'", null);
        return true;
    }
    //edittutor
    private static final String TAG = "DBConnect";

    private static final String TABLE_NAME = "tutor";
    private static final String COL1 = "tutorname";
    private static final String COL4 = "phone_no";

    public void updatePhone(String newName, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newName + "' where "  + COL1 + " = '" + id + "'";
//               + " AND " + COL1 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public ArrayList<String> getLabReport(long start,long end){
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from labattendence",null);
        while(res.moveToNext()) {
            String student = res.getString(res.getColumnIndex("studentid"));
            long time = res.getLong(res.getColumnIndex("enter_time"));
            if(time >= start && time <= end) {
                java.util.Date dd = new java.util.Date(time);
//                java.sql.Date date = new java.sql.Date(dd.getTime());
				String formattedDate  = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(dd);
                sname.add(student + "," + formattedDate);
            }
        }
        return sname;
    }

    public ArrayList<String> getTutoringReport(long start,long end){
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tutoringattendence",null);
        while(res.moveToNext()) {
            String student = res.getString(res.getColumnIndex("studentname"));
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            String subject = res.getString(res.getColumnIndex("subjectname"));
            String comment = res.getString(res.getColumnIndex("comments"));

            long time = res.getLong(res.getColumnIndex("enter_time"));
            if(time >= start && time <= end) {
                java.util.Date dd = new java.util.Date(time);
//                java.sql.Timestamp date = new java.sql.Timestamp(dd.getTime());
				String formattedDate  = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(dd);
				sname.add(student + ","  +tutor+ "," +subject+ ","+ comment+ ","   + formattedDate);
            }
        }
        return sname;
    }

    public LinkedHashMap<String, Integer> getTutoringGraph(long startTime, long endTime){
		LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from tutoringattendence",null);
		while(res.moveToNext()) {
			String tutor = res.getString(res.getColumnIndex("tutorname"));
			long time = res.getLong(res.getColumnIndex("enter_time"));
			if(time >= startTime && time <= endTime) {

				if (map.containsKey(tutor)) {
					map.put(tutor, (map.get(tutor) + 1));
				} else {
					map.put(tutor, 1);
				}
			}
		}
		return map;
	}

	public LinkedHashMap<String, Integer> getLabGraph(long startTime, long endTime){
		LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from labattendence",null);
		while(res.moveToNext()) {
			String tutor = res.getString(res.getColumnIndex("studentid"));
			long time = res.getLong(res.getColumnIndex("enter_time"));
			if(time >= startTime && time <= endTime) {

				if (map.containsKey(tutor)) {
					map.put(tutor, (map.get(tutor) + 1));
				} else {
					map.put(tutor, 1);
				}
			}
		}
		return map;
	}
}
