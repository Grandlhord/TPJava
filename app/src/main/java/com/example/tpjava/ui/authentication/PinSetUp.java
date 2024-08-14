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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpjava.MainActivity;
import com.example.tpjava.R;
import com.example.tpjava.storageHelper.StorageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PinSetUp extends AppCompatActivity {

    Button btnPinSetUp;
    TextView pinText;
    TextView confirmPinText;

    StorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pin_set_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        storageHelper = new StorageHelper(this);

        btnPinSetUp = findViewById(R.id.btnPinSetup);
        pinText = findViewById(R.id.editPin);
        confirmPinText = findViewById(R.id.editPinConfirm);


        btnPinSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pinText.getText().toString();
                String confirmPin = confirmPinText.getText().toString();
                storageHelper.saveString("userPin", pin);
                storageHelper.saveString("confirmPin", confirmPin);
                // TODO: Implement PIN setup logic
                setPin(pin, confirmPin);
            }
        });
    }

    public void setPin(String pin, String confirmPin) {
        String url = "http://64.23.200.185:8080/v1/users/change-pin";
        RequestQueue queue = Volley.newRequestQueue(this);

        String phoneNumber = storageHelper.getString("phoneNumber", " ");
        String otpCode = storageHelper.getString("OtpCode", " ");

        Map<String, String> setPin = new HashMap<>();
        setPin.put("phoneNumber", phoneNumber);
        setPin.put("otp", otpCode);
        setPin.put("pin", pin);
        setPin.put("newPin", confirmPin);

        JsonObjectRequest request = null;
        try {
            if (Objects.equals(pin, confirmPin)) {
                request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(setPin), response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        if (success) {
                            startActivity(new Intent(this, accountRegistration.class));
                            finish();
                        } else {
                            Toast.makeText(PinSetUp.this, "Failed to set pin: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(PinSetUp.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    System.out.println(error.toString());
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    // Handle error
                });
            } else {
                Toast.makeText(this, "Pins do not match", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something happened", Toast.LENGTH_SHORT).show();
        }
        queue.add(request);
    }
}