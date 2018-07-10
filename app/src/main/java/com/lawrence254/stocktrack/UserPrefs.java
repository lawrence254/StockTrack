package com.lawrence254.stocktrack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lawrence254.stocktrack.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPrefs extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "News";

    @BindView(R.id.sources)ListView mSourceList;
    @BindView(R.id.saveSel)Button mSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prefs);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getResources().getStringArray(R.array.newsId));
        mSourceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mSourceList.setAdapter(adapter);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mSave){
            String selected = "";
            int cntChoice = mSourceList.getCount();
            SparseBooleanArray sparseBooleanArray = mSourceList.getCheckedItemPositions();
//            String[] newsId = getResources().getStringArray(R.array.news);

//            TODO: Enable looping through the second array and get values based on the indexes of the selected values in the checked-values arraylist.

            for(int i = 0; i < cntChoice; i++){
                    if (sparseBooleanArray.get(i)) {
                        selected += mSourceList.getItemAtPosition(i).toString() + "\n";
                        System.out.println("Checking list while adding:" + mSourceList.getItemAtPosition(i).toString());
                        SaveSelections();
                    }
            }
//            Toast.makeText(UserPrefs.this, selected, Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveSelections() {
// save the selections in the shared preference in private mode for the user
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        String savedItems = getSavedItems();
        prefEditor.putString(MyPREFERENCES.toString(), savedItems);
        prefEditor.apply();
    }
    private String getSavedItems() {
        String savedItems = "";
        int count = this.mSourceList.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (this.mSourceList.isItemChecked(i)) {
                if (savedItems.length() >=1) {
                    savedItems += "," + this.mSourceList.getItemAtPosition(i);
                    Toast.makeText(this, "Saved items: "+savedItems, Toast.LENGTH_LONG).show();

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("user");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    ref.child(user.getUid()).setValue(savedItems);

                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                } else {
                    savedItems += this.mSourceList .getItemAtPosition(i);
                }
            }
        }
        return savedItems;
    }

}
