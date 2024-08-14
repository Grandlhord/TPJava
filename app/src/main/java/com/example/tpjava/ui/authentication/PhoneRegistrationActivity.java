package com.example.tpjava.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpjava.R;
import com.example.tpjava.storageHelper.StorageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PhoneRegistrationActivity extends AppCompatActivity {

    Button btnSendOTP;
    EditText phoneNumberText;
    StorageHelper storageHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        storageHelper = new StorageHelper(this);

        btnSendOTP = findViewById(R.id.btnSendOTP);
        phoneNumberText = findViewById(R.id.editPhoneNumber);

        btnSendOTP.setOnClickListener(v -> {
            // Validating if the text field is empty or not.
            if (phoneNumberText.getText().toString().isEmpty()) {
                Toast.makeText(PhoneRegistrationActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            SendOTP(phoneNumberText.getText().toString());
            storageHelper.saveString("phoneNumber", phoneNumberText.getText().toString());

            String storedPhoneNumber = storageHelper.getString("phoneNumber"," ");

            System.out.println(storedPhoneNumber);
        });
    }

    private void SendOTP(String phoneNumber) {
        String url = "http://64.23.200.185:8080/v1/users/otp";
        RequestQueue queue = Volley.newRequestQueue(PhoneRegistrationActivity.this);

        Map<String, String> postData = new HashMap<>();
        postData.put("phoneNumber", phoneNumber);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postData), response -> {
            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(response);

            try {
                boolean success = response.getBoolean("success");
                String message = response.getString("message");

                if (success) {
                    Toast.makeText(PhoneRegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhoneRegistrationActivity.this, OTPVerification.class));
                } else {
                    Toast.makeText(PhoneRegistrationActivity.this, "Failed to send OTP: " + message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(PhoneRegistrationActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            System.out.println(error.toString());
            Toast.makeText(PhoneRegistrationActivity.this, "Failed to send OTP: " + error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(PhoneRegistrationActivity.this, "Something terrible happened, try again.", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }
}
