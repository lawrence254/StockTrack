package com.lawrence254.stocktrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lawrence254.stocktrack.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.name)TextView mname;
    @BindView(R.id.email) TextView memail;
    @BindView(R.id.provider) TextView prov;
    @BindView(R.id.img)ImageView mImg;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,root);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String fname =user.getDisplayName();
        List<String> providers = user.getProviders();
        String email = user.getEmail();
        String img = String.valueOf(user.getPhotoUrl());

        StringBuilder builder = new StringBuilder();
        for (String details : providers) {
            builder.append(details + "");
        }

        prov.setText("Account: "+builder.toString());

        mname.setText("Name: "+fname);
        memail.setText("Email: "+email);

        Picasso.get().load(img).into(mImg);

        return root;
    }

}
