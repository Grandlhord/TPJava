package com.example.tpjava.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.example.tpjava.R;

public class P2PTransactionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_ptransaction_detail);

        // Set up window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button showDialogButton = findViewById(R.id.btnP2PContinue);
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
        View dialogView = inflater.inflate(R.layout.activity_transaction_summary, null);

        // Find and set the TextViews with the data
        TextView transactionAmount = dialogView.findViewById(R.id.amount);
        transactionAmount.setText("GH¢2,540.00");

        TextView transactionRecipient = dialogView.findViewById(R.id.transaction_recipient);
        transactionRecipient.setText("Audrey Sam");

        TextView transactionAccountNumber = dialogView.findViewById(R.id.transaction_account_number);
        transactionAccountNumber.setText("1234567890");

        TextView transactionAccountNetwork = dialogView.findViewById(R.id.transaction_account_network);
        transactionAccountNetwork.setText("BDP");

        TextView transactionNote = dialogView.findViewById(R.id.transaction_note);
        transactionNote.setText("For fees");

        // Set up the button click listeners
        Button confirmButton = dialogView.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(P2PTransactionDetail.this, TransactionOTP.class));
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
