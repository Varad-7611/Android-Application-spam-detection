package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carpriceprediction.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText inputMessage;
    Button btnPredict;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputMessage = findViewById(R.id.inputMessage);
        btnPredict = findViewById(R.id.btnPredict);
        tvResult = findViewById(R.id.tvResult);

        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputMessage.getText().toString().trim();

                if (message.isEmpty()) {
                    tvResult.setText("Please enter a message.");
                    return;
                }

                // ✅ Correct URL with query parameter
                String url = "https://flask-app-b4gg.onrender.com/predict?message=" + message.replace(" ", "%20");

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                String prediction = response.getString("prediction");
                                tvResult.setText("Prediction: " + prediction);
                            } catch (JSONException e) {
                                tvResult.setText("Parsing error: " + e.getMessage());
                            }
                        },
                        error -> tvResult.setText("API error: " + error.toString())
                );

                queue.add(jsonObjectRequest);
            }
        });
    }
}
