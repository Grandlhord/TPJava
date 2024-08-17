package com.example.tpjava.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class accountRegistration extends AppCompatActivity {

    EditText phoneNumber, fullName,  ghanaNumber;
    Button btnRegisterButton;
    StorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        storageHelper = new StorageHelper(this);

        phoneNumber = findViewById(R.id.editPhoneNumber);
        fullName = findViewById(R.id.editFullName);
        ghanaNumber = findViewById(R.id.editGhanaNumber);

        String storedPhoneNumber = storageHelper.getString("phoneNumber", "");
        phoneNumber.setText(storedPhoneNumber);
        System.out.println("this is the phone number" + storedPhoneNumber);

        btnRegisterButton = findViewById(R.id.btnRegisterButton);
        btnRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the most recent values entered by the user
                String name = fullName.getText().toString().trim();
                String ghanaCardNumber = ghanaNumber.getText().toString().trim();
                registerAccount(name, ghanaCardNumber, storedPhoneNumber);

               storageHelper.saveString("user", name);
            }
        });
    }

    public void registerAccount(String fullName, String ghanaCardNumber, String phoneNumber) {
        String url= "http://64.23.200.185:8080/v1/users/register";

        RequestQueue queue = Volley.newRequestQueue(this);

        String storedPhoneNumber = storageHelper.getString("phoneNumber", "");
        String storedPin = storageHelper.getString("userPin", " ");
        String storedConfirmPin = storageHelper.getString("confirmPin", "");
        String otpCode = storageHelper.getString("OtpCode", " ");

        System.out.println("received otp code: " + otpCode);

        Map<String, String> registerAccount = new HashMap<>();
        registerAccount.put("phoneNumber", phoneNumber);
        registerAccount.put("name", fullName);
        registerAccount.put("ghanaCardNumber", ghanaCardNumber);
        registerAccount.put("pin", storedPin);
        registerAccount.put("pinConfirmation", storedConfirmPin);
        registerAccount.put("otp", otpCode);

        System.out.println("this is the body" +  registerAccount);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(registerAccount),
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");

                        if (success) {
                            // Check if the "data" key exists
                            if (response.has("data")) {
                                JSONObject data = response.getJSONObject("data");
                                String externalId = data.getString("externalId");
                                String name = data.getString("name");
                                String userGhanaCardNumber = data.getString("ghanaCardNumber");
                                String userPhoneNumber = data.getString("phoneNumber");
                                String type = data.getString("type");
                                String status = data.getString("status");
                                String kycStatus = data.getString("kycStatus");
                                int actualBalance = data.getInt("actualBalance");
                                int availableBalance = data.getInt("availableBalance");
                                String bearerToken = data.getString("bearerToken");

                                // Process the data as needed
                            }
                            Toast.makeText(accountRegistration.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(accountRegistration.this, KYCSetup.class));
                        } else {
                            Toast.makeText(accountRegistration.this, "Failed to verify OTP: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(accountRegistration.this, "Failed to register account", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        }
        );
        queue.add(request);
    }
}