package com.example.tpjava.ui.top_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tpjava.R;

public class TopUpNoWallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up_no_wallet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button showDialogButton = findViewById(R.id.btnAddWallet);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModalDialog();
            }
        });
    }


    private void showModalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_wallet_modal, null);

        // Find and set the TextViews with the data
        EditText walletName = dialogView.findViewById(R.id.edtName);
        walletName.getText();

        Spinner mobileNetwork = dialogView.findViewById(R.id.selectNetwork);
        mobileNetwork.getSelectedItem();

        EditText mobileNumber = dialogView.findViewById(R.id.editMobileNumber);
        mobileNumber.getText();

        // Set up the button click listeners
        Button confirmButton = dialogView.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TopUpNoWallet.this,  walletName.getText().toString() +   mobileNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TopUpNoWallet.this, SelectWallet.class));
                // Handle confirm button click
                // For example, navigate to another activity or perform some action
            }
        });

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
}