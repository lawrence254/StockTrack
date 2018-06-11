package com.lawrence254.stocktrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="stocktrack.db";
    public static final String TABLE_NAME="registeration";
    public static final String u_id="ID";
    public static final String ufname="fname";
    public static final String ulname="lname";
    public static final String upass="password";
    public static final String uemail="email";
//    public static final String COL_6="Phone";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,fname TEXT,lname TEXT,password TEXT,email TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME); //Drop older table if exists
        onCreate(db);
    }
}