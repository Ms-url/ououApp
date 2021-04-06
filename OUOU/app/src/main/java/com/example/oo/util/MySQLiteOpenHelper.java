package com.example.oo.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static String CREATE_BOOK = "create table Cities("
            + "id integer primary key autoincrement,"   //id integer //primary key主键 //autoincrement自增长
            + "cityName text,"
            + "aid integer)";//real 浮点数

    private Context mContext;

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Cities");
        onCreate(db);
    }

    private void putIn(MySQLiteOpenHelper dbhelp,String name,int aid){
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cityName",name);
        values.put("aid",aid);
        db.insert("Cities",null,values);
        //db.update("Cities",null,values);
        //db.
        values.clear();
    }
}
