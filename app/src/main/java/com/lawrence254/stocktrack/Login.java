package com.lawrence254.stocktrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.username)EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.btnlogin)Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == mLogin){

        }
    }
}
