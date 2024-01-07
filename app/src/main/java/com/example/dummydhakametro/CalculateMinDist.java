package com.example.dummydhakametro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CalculateMinDist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_min_dist);
        Intent intent = getIntent();
        String source = intent.getStringExtra("Source");
        String destination = intent.getStringExtra("Destination");

    }
}