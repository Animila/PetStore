package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetGetActivity extends AppCompatActivity {

    private TextView namePet, idPet, categoryPet, statusPet, tagsPet;
    private EditText getId;
    private ImageView photoPet;
    private Button getBtn, backBtn;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_get);


        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        photoPet = (ImageView) findViewById(R.id.imageView);
        getBtn = (Button) findViewById(R.id.btnLoad);
        backBtn = (Button) findViewById(R.id.btnStep);
        getId = (EditText) findViewById(R.id.id_pet);

        namePet = (TextView) findViewById(R.id.namePat);
        idPet = (TextView) findViewById(R.id.id);
        categoryPet = (TextView) findViewById(R.id.categoryPet);
        statusPet = (TextView) findViewById(R.id.statusPet);
        tagsPet = (TextView) findViewById(R.id.tegsPet);

        PetService petService = PetService.retrofit.create(PetService.class);
//        SharedPreferences sharedPreferences = getSharedPreferences("Pets", Context.MODE_PRIVATE);
//        Integer myValue = Integer.parseInt(sharedPreferences.getString("new_pet", "123"));

        getBtn.setOnClickListener(view -> {

            Call<Pet> pet = petService.getPet(Integer.parseInt(getId.getText().toString()));
            progress.setVisibility(View.VISIBLE);
            pet.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        progress.setVisibility(View.INVISIBLE);

                        Pet pet = response.body();
                        idPet.setText("ID: "+ pet.getId());
                        namePet.setText("Имя: "+ pet.getName());
                        categoryPet.setText("Категория: "+pet.getCategory().getName());
                        statusPet.setText("Статус: "+ pet.getStatus());
                        tagsPet.setText("Теги: "+pet.getTags().get(0).getName());

                        List<String> petPhoto = pet.getPhotoUrls();
                        Picasso
                                .get()
                                .load(petPhoto.get(0))
                                .into(photoPet);
                    } else {
                        progress.setVisibility(View.INVISIBLE);

                        Log.d("GIT", ""+response.body());
                    }
                }

                @Override
                public void onFailure(Call<Pet> call, Throwable t) {
                    progress.setVisibility(View.INVISIBLE);
                    Log.d("GIT", ""+t.getMessage());
                    Toast.makeText(PetGetActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });


        backBtn.setOnClickListener(v -> {
            Intent step = new Intent(this, PetNavigationActivity.class);
            startActivity(step);
            finish();
        });
    }
}