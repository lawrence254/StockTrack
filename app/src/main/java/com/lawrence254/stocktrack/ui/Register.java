package com.lawrence254.stocktrack.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lawrence254.stocktrack.DB.DBHelper;
import com.lawrence254.stocktrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.fname) EditText mFirstName;
    @BindView(R.id.lname) EditText mLastName;
    @BindView(R.id.uemail) EditText mEmail;
    @BindView(R.id.upass) EditText mPassword;
    @BindView(R.id.upassrepeat) EditText mPassRepeat;
    @BindView(R.id.register) Button mRegister;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        openHelper = new DBHelper(this);
        mRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == mRegister) {

            db = openHelper.getWritableDatabase();
            String fname = mFirstName.getText().toString();
            String lname = mLastName.getText().toString();
            String pass = mPassword.getText().toString();
            String passrepeat = mPassRepeat.getText().toString();
            String email = mEmail.getText().toString();
//            String phone=_txtphone.getText().toString();
            if (fname != "" || lname != "" || pass != "" || passrepeat != "" && pass.equals(passrepeat)) {
                insertdata(fname, lname, pass, email);
                Toast.makeText(getApplicationContext(), "Registration successful. You can now login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Register.this,Landing.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please ensure that all fields are field", Toast.LENGTH_LONG).show();
            }
            }
        }

    public void insertdata(String fname, String lname, String pass, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ufname, fname);
        contentValues.put(DBHelper.ulname, lname);
        contentValues.put(DBHelper.upass, pass);
        contentValues.put(DBHelper.uemail, email);
        long u_id = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        Log.d("Registration ", ": "+u_id);
    }
}


