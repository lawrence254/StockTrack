package com.lawrence254.stocktrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.fname) EditText mFirstName;
    @BindView(R.id.lname) EditText mLastName;
    @BindView(R.id.uemail) EditText mEmail;
    @BindView(R.id.upass) EditText mPassword;
    @BindView(R.id.upassrepeat) EditText mPassRepeat;
    @BindView(R.id.register) Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == mRegister){

        }
    }

}
