package com.example.tpjava;

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

public class TopUpNoCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up_no_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnTopUpNoCard = (Button) findViewById(R.id.btnAddCard);
        btnTopUpNoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModalDialog();
            }
        });
        }

        private void showModalDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_card_modal, null);

            // Find and set the TextViews with the data
            EditText cardNumber = dialogView.findViewById(R.id.cardNumber);
            cardNumber.getText();

            EditText cardName = dialogView.findViewById(R.id.cardName);
            cardName.getText();

            EditText expiryDate = dialogView.findViewById(R.id.expiryDate);
            expiryDate.getText();

            EditText cvv = dialogView.findViewById(R.id.cvv);
            cvv.getText();

            // Set up the button click listeners
            Button saveCard = dialogView.findViewById(R.id.btnSaveCard);
            saveCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TopUpNoCard.this,  cardName.getText().toString() +   cardNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TopUpNoCard.this, SelectCard.class));
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