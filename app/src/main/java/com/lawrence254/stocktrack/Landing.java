package com.lawrence254.stocktrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Landing extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnLogin) Button mButtonLogin;
    @BindView(R.id.btnRegister) Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        if(v == mButtonLogin){
            Intent intent = new Intent(Landing.this,Login.class);
            startActivity(intent);
        }else if(v == mButtonRegister){
            Intent intent = new Intent(Landing.this,Register.class);
            startActivity(intent);
        }
    }
}
