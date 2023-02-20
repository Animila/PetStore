package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PetNavigationActivity extends AppCompatActivity {

    private Button btnCreate, btnFind, btnChange, btnDelete, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_navigation);

        btnHome = (Button) findViewById(R.id.btnHome);

        btnFind = (Button) findViewById(R.id.btnFind);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnChange = (Button) findViewById(R.id.btnChange);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnHome.setOnClickListener(v -> {
            Intent startActivity = new Intent(this, MainActivity.class);
            startActivity(startActivity);
            finish();
        });
        btnFind.setOnClickListener(v -> {
            Intent findActivity = new Intent(this, PetGetActivity.class);
            startActivity(findActivity);
            finish();
        });
        btnCreate.setOnClickListener(v -> {
            Intent createActivity = new Intent(this, PetCreateActivity.class);
            startActivity(createActivity);
            finish();
        });
        btnDelete.setOnClickListener(v -> {

        });
        btnChange.setOnClickListener(v -> {

        });
    }
}