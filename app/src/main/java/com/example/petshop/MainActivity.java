package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button goPet, goOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goPet = (Button) findViewById(R.id.goPets);

        goPet.setOnClickListener(v -> {
            Intent petWindow = new Intent(this, RegisterActivity.class);
            startActivity(petWindow);
        });
    }
}