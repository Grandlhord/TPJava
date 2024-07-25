package com.example.tpjava.ui.top_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tpjava.R;

public class TopUpAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnMomo =  findViewById(R.id.btnMomo);
        btnMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement top-up logic
                startActivity(new Intent(TopUpAccount.this, TopUpNoWallet.class));
            }
        });

        Button btnCreditDebitCard =  findViewById(R.id.btnCreditDebitCard);
        btnCreditDebitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement top-up logic
                startActivity(new Intent(TopUpAccount.this, TopUpNoCard.class));
            }
        });

        Button btnBankTransfer =  findViewById(R.id.btnBank);
        btnBankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement top-up logic
                startActivity(new Intent(TopUpAccount.this, BankTransfer.class));
            }
        });
    }
}