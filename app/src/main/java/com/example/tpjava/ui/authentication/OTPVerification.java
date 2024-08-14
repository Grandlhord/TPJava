package com.example.tpjava.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.chaos.view.PinView;
import com.example.tpjava.R;
import com.example.tpjava.storageHelper.StorageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OTPVerification extends AppCompatActivity {

Button btnVerifyOtp;
StorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverfication);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        storageHelper = new StorageHelper(this);

        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        PinView otp = findViewById(R.id.otpCode);

        btnVerifyOtp.setOnClickListener(v -> {
            String otpCode = Objects.requireNonNull(otp.getText()).toString().trim();

            if (otpCode.isEmpty()) {
                Toast.makeText(OTPVerification.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            storageHelper.saveString("OtpCode", otpCode);
            VerifyOTP(otpCode);
        });

    }
        private void VerifyOTP(String pin) {
            String url = "http://64.23.200.185:8080/v1/users/otp/verify";
            RequestQueue queue = Volley.newRequestQueue(OTPVerification.this);

            String phoneNumber = storageHelper.getString("phoneNumber", " ");
            System.out.println("Verifying phone number" + phoneNumber);


            Map<String, String> verifyOtpData = new HashMap<>();
            verifyOtpData.put("phoneNumber", phoneNumber);
            verifyOtpData.put("otp", pin);

            System.out.println(verifyOtpData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(verifyOtpData), response -> {
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(response);

                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");

                    if (success) {
                        Toast.makeText(OTPVerification.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTPVerification.this, PinSetUp.class));
                    } else {
                        Toast.makeText(OTPVerification.this, "Failed to verify OTP: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(OTPVerification.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                System.out.println(error.toString());
                Toast.makeText(OTPVerification.this, "Failed to verify OTP: " + error.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(OTPVerification.this, "Something terrible happened, try again.", Toast.LENGTH_SHORT).show();
            });
            queue.add(request);
        }
    }