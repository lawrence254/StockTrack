package com.lawrence254.stocktrack.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lawrence254.stocktrack.DB.DBHelper;
import com.lawrence254.stocktrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class profile extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    @BindView(R.id.ufn)
    TextView mfirst;
    @BindView(R.id.uln) TextView mlast;
    @BindView(R.id.uem) TextView memail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        String ID = intent.getStringExtra("UID");
        Log.d("ID", ""+ID);
        openHelper=new DBHelper(this);
        db = openHelper.getReadableDatabase();

//        String query = "SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+DBHelper.u_id +" =?";

        cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+DBHelper.u_id +" =?",new String[]{ID});
        Log.d("Query", ""+cursor);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.d(MainActivity.class.getSimpleName(), "cursor count: " + cursor.getCount());
                String fname = cursor.getString(cursor.getColumnIndex("fname"));
                String lname = cursor.getString(cursor.getColumnIndex("lname"));
                String email = cursor.getString(cursor.getColumnIndex("email"));

                mfirst.setText(fname);
                mlast.setText(lname);
                memail.setText(email);

                Log.d("User ", "Details: " + fname + " " + lname + "," + email);

            }
        }
        cursor.close();
        db.close();
    }
}