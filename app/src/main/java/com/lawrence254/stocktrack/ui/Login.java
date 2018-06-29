package com.lawrence254.stocktrack.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lawrence254.stocktrack.DB.DBHelper;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.UserPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "News";
    ArrayList<String> selectedItems = new ArrayList<String>();
    String savedItems = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(sharedpreferences.contains(MyPREFERENCES)) {
                    savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
                    selectedItems.addAll(Arrays.asList(savedItems.split(",")));

                }

                Toast.makeText(this, "Welcome to StockTrack "+user.getDisplayName()+". Your news sources: "+savedItems, Toast.LENGTH_LONG).show();
                if(sharedpreferences.contains(MyPREFERENCES)){
//                    if(sharedpreferences.contains(MyPREFERENCES)) {
//                        String savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
//                        selectedItems.addAll(Arrays.asList(savedItems.split(",")));
//                        if (selectedItems.size() >= 1) {
//                            Intent intent = new Intent(this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(this, UserPrefs.class);
//                            startActivity(intent);
//                        }
//                    }
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(this,UserPrefs.class);
                    startActivity(intent);
                }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                response.getError().getErrorCode();
            }
        }
    }
}
