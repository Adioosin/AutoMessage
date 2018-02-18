package com.trojan_1.dbtry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guest11 on 29-08-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static  final String DATABASE_NAME="Event_Data.db";
    public static  final String TABLE_NAME="Eventtable";
    public static  final String COL_1="ID";
    public static  final String COL_2="DATE";
    public static  final String COL_3="TIME";
    public static  final String COL_4="MOBILE";
    public static  final String COL_5="EVENT_NAME";
    public static  final String COL_6="MESSAGE";
    public static  final String COL_7="DAYSLEFT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY,DATE TEXT,TIME TEXT,MOBILE TEXT,EVENT_NAME TEXT,MESSAGE TEXT,DAYSLEFT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String time,String mobile, String eventname, String message,int daysleft){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,mobile);
        contentValues.put(COL_5,eventname);
        contentValues.put(COL_6,message);
        contentValues.put(COL_7,daysleft);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
