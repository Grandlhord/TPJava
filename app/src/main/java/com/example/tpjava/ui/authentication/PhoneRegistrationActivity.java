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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpjava.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhoneRegistrationActivity extends AppCompatActivity {

    Button btnSendOTP;
    EditText phoneNumberText;

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

        btnSendOTP = findViewById(R.id.btnSendOTP);
        phoneNumberText = findViewById(R.id.editPhoneNumber);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validating if the text field is empty or not.
                if (phoneNumberText.getText().toString().isEmpty()) {
                    Toast.makeText(PhoneRegistrationActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Calling a method to post the data and passing our phone number.
                postDataUsingVolley(phoneNumberText.getText().toString());
            }
        });
    }

    private void postDataUsingVolley(String phoneNumber) {
        // URL to post our data
        String url = "http://api.bsl.com.gh:8080/rest/members/registration/generate-otp";

        // Creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PhoneRegistrationActivity.this);

        // Creating the JSON object to be sent to the server
        JSONObject postData = new JSONObject();
        try {
            postData.put("medium", phoneNumber);
            postData.put("actionType", "Registration");
            postData.put("otpToMobile", true);

            Toast.makeText(this, postData.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create JSON data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creating a JsonObjectRequest method to post the data to our API
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postData, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Setting data to edit text as empty
                phoneNumberText.setText("");
                Toast.makeText(PhoneRegistrationActivity.this, "the response is " + response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    // Extracting data from JSON object.
                    boolean status = response.getBoolean("status");
                    boolean sessionExpired = response.getBoolean("sessionExpired");
                    boolean failed = response.getBoolean("failed");

                    // Extracting messages array.
                    JSONArray messages = response.getJSONArray("messages");
                    JSONObject messageObj = messages.getJSONObject(0);
                    String shortMessage = messageObj.getString("shortMessage");

                    // Displaying a success toast message.
                    if (status && !failed) {
                        Toast.makeText(PhoneRegistrationActivity.this, shortMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PhoneRegistrationActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PhoneRegistrationActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PhoneRegistrationActivity.this, postData.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(PhoneRegistrationActivity.this, "Failed to get response", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PhoneRegistrationActivity.this, OTPVerification.class));
            }
        });

        // Adding the request to the request queue.
        queue.add(request);
    }
}
