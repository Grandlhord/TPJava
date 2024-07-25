package com.example.tpjava.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tpjava.MainActivity;
import com.example.tpjava.R;

public class Login extends AppCompatActivity {

    TextView signUpText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpText = findViewById(R.id.dontHaveAccountTextView);
        loginButton = findViewById(R.id.loginButton);

        signUpText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, PhoneRegistrationActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }
}