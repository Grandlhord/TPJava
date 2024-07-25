package com.example.tpjava.ui.transaction;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tpjava.R;
import com.example.tpjava.ui.top_up.TopUpAccount;


public class wallet extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle("");
            }
        }
        // Find the button and set an OnClickListener
        Button btnMomo = view.findViewById(R.id.btnMomo);
        btnMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getActivity(), "Button clicked!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), P2PTransfer.class));
            }
        });

        Button btnTopUp = view.findViewById(R.id.top_up_button);
        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getActivity(), "Top Up Button clicked!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), TopUpAccount.class));
            }
        });
    }

}