package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button goPet, goOrders;
    private ImageView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goPet = (Button) findViewById(R.id.goPets);
        account = (ImageView) findViewById(R.id.account_btn);

        account.setOnClickListener(v -> {
            Intent petWindow = new Intent(this, PetNavigationActivity.class);
            startActivity(petWindow);
        });

        goPet.setOnClickListener(v -> {
            Intent petWindow = new Intent(this, PetNavigationActivity.class);
            startActivity(petWindow);
            finish();
        });
    }
}