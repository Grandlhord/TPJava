package com.example.tpjava.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.tpjava.HomeScreen;
import com.example.tpjava.R;
import com.example.tpjava.storageHelper.StorageHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    TextView signUpText;
    Button loginButton;
    EditText name, pin;
    StorageHelper storageHelper;

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

        storageHelper = new StorageHelper(this);

        signUpText = findViewById(R.id.dontHaveAccountTextView);
        loginButton = findViewById(R.id.loginButton);
        name = findViewById(R.id.editName);
        pin = findViewById(R.id.editPin);

        signUpText.setOnClickListener(view -> {
            Toast.makeText(Login.this, "clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this, PhoneRegistrationActivity.class));
        });

        loginButton.setOnClickListener(view -> {
            login(name.getText().toString(), pin.getText().toString());
        });
    }

    public void login(String phoneNumber, String pin) {
        String url = "http://64.23.200.185:8080/v1/users/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        String storedUser = storageHelper.getString("phoneNumber", "");
        String storedPin = storageHelper.getString("confirmPin", "");

        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", phoneNumber);
        loginData.put("pin", pin);

        System.out.println("Login data: " + loginData);
        System.out.println("Stored user: " + storedUser);
        System.out.println("Stored pin: " + storedPin);

        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(loginData), response -> {

                // Print the full response
                System.out.println("Response: " + response.toString());

                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");

                    if (success) {
                        if (response.has("data")) {
                            JSONObject data = response.getJSONObject("data");
                            // Print all the data received
                            System.out.println("Data: " + data.toString());

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

                        }
                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, HomeScreen.class));
                    } else if (response.has("error")) {
                        String errorMessage = response.getString("message");
                        JSONObject errors = response.getJSONObject("error");

                        // Print the error message
                        System.out.println("Error message: " + errorMessage);
                        System.out.println("Errors: " + errors.toString());

                        JSONArray phoneNumbers = errors.getJSONArray("phoneNumbers");
                        JSONArray pins = errors.getJSONArray("pins");

                        Toast.makeText(Login.this, Objects.equals(storedUser, phoneNumbers) ? phoneNumbers.toString() : pins.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, error -> {
                // Detailed error handling
                System.out.println("Volley error: " + error.toString());

                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    String responseBody = new String(error.networkResponse.data);

                    System.out.println("Status Code: " + statusCode);
                    System.out.println("Response Body: " + responseBody);

                    if (statusCode == 500) {
                        Toast.makeText(Login.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Error: " + statusCode + " - " + responseBody, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Network error. Please check your connection.", Toast.LENGTH_LONG).show();
                }
            });

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
