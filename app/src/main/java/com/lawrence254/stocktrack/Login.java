package com.lawrence254.stocktrack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.username)EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.btnlogin)Button mLogin;

    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        openHelper=new DBHelper(this);
        db = openHelper.getReadableDatabase();

        mLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == mLogin){
            String email = mEmail.getText().toString();
            String pass = mPassword.getText().toString();

            cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.uemail + "=? AND " + DBHelper.upass + "=?", new String[]{email, pass});
            if (cursor != null && cursor.moveToFirst()) {
                Log.d(Login.class.getSimpleName(), "cursor count: " + cursor.getCount());
                if (cursor.getCount() > 0) {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    Toast.makeText(getApplicationContext(), "Login Success: ID"+id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra("UID",id);
                    startActivity(intent);

                } else if(cursor.getCount() < 1){
                    Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        }
    }

