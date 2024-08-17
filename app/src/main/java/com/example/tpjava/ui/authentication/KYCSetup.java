package com.example.tpjava.ui.authentication;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpjava.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class KYCSetup extends AppCompatActivity {

    private RequestQueue requestQueue;

    private static final int REQUEST_IMAGE_CAPTURE_SELFIE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_DOC1 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_DOC2 = 3;

    private ImageView selfieImage, ghanaCardFront, ghanaCardBack;
    private Bitmap selfieBitmap, cardFrontBitmap, cardBackBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kycsetup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);

        selfieImage = findViewById(R.id.upload_selfie);
        ghanaCardFront = findViewById(R.id.ghanaCardBack);
        ghanaCardBack = findViewById(R.id.ghanaCardFront);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // Request Camera Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        selfieImage.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_SELFIE));

        ghanaCardFront.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_DOC1));

        ghanaCardBack.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_DOC2));

        buttonSubmit.setOnClickListener(v -> {
            uploadSelfie();
            uploadGhCardFront();
            uploadGhCardBack();
        });
    }

    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE_SELFIE:
                    selfieBitmap = imageBitmap;
                    selfieImage.setImageBitmap(selfieBitmap);
                    break;
                case REQUEST_IMAGE_CAPTURE_DOC1:
                    cardFrontBitmap = imageBitmap;
                    ghanaCardFront.setImageBitmap(cardFrontBitmap);
                    break;
                case REQUEST_IMAGE_CAPTURE_DOC2:
                    cardBackBitmap = imageBitmap;
                    ghanaCardBack.setImageBitmap(cardBackBitmap);
                    break;
            }
        }
    }

    private void uploadSelfie() {
        String url = "http://64.23.200.185:8080/v1/users/kyc";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            JSONObject fileInfo = data.getJSONObject(0);

                            String externalId = fileInfo.getString("externalId");
                            String name = fileInfo.getString("name");
                            String type = fileInfo.getString("type");
                            String fileUrl = fileInfo.getString("url");
                            String createdAt = fileInfo.getString("createdAt");

                            // Log or display the response details
                            Log.d("Upload Response", "External ID: " + externalId);
                            Log.d("Upload Response", "File Name: " + name);
                            Log.d("Upload Response", "File Type: " + type);
                            Log.d("Upload Response", "File URL: " + fileUrl);
                            Log.d("Upload Response", "Created At: " + createdAt);

                            Toast.makeText(KYCSetup.this, "Selfie uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = jsonResponse.getString("message");
                            Toast.makeText(KYCSetup.this, "Failed to upload selfie: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(KYCSetup.this, "Failed to parse server response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(KYCSetup.this, "Failed to upload selfie.", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("selfie", bitmapToString(selfieBitmap));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void uploadGhCardFront() {
        String url = "http://64.23.200.185:8080/v1/users/kyc";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            JSONObject fileInfo = data.getJSONObject(0);

                            String externalId = fileInfo.getString("externalId");
                            String name = fileInfo.getString("name");
                            String type = fileInfo.getString("type");
                            String fileUrl = fileInfo.getString("url");
                            String createdAt = fileInfo.getString("createdAt");

                            // Log or display the response details
                            Log.d("Upload Response", "External ID: " + externalId);
                            Log.d("Upload Response", "File Name: " + name);
                            Log.d("Upload Response", "File Type: " + type);
                            Log.d("Upload Response", "File URL: " + fileUrl);
                            Log.d("Upload Response", "Created At: " + createdAt);

                            Toast.makeText(KYCSetup.this, "Selfie uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = jsonResponse.getString("message");
                            Toast.makeText(KYCSetup.this, "Failed to upload selfie: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(KYCSetup.this, "Failed to parse server response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(KYCSetup.this, "Failed to upload selfie.", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ghana-card-front", bitmapToString(selfieBitmap));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void uploadGhCardBack() {
        String url = "http://64.23.200.185:8080/v1/users/kyc";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(KYCSetup.this, "Back of Ghana Card uploaded successfully!", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(KYCSetup.this, "Failed to upload back of Ghana Card.", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ghana-card-back", bitmapToString(cardBackBitmap));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
