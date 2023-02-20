package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPetActivity extends AppCompatActivity {

    private TextView namePet, idPet;
    private ImageView photoPet;
    private Button getBtn;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pet);


        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        namePet = (TextView) findViewById(R.id.namePat);
        photoPet = (ImageView) findViewById(R.id.imageView);
        getBtn = (Button) findViewById(R.id.btnLoad);
        idPet = (TextView) findViewById(R.id.id);

        PetStoreService petStoreService = PetStoreService.retrofit.create(PetStoreService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("Pets", Context.MODE_PRIVATE);
        Integer myValue = Integer.parseInt(sharedPreferences.getString("new_pet", "123"));

        getBtn.setOnClickListener(view -> {

            Call<Pet> pet = petStoreService.getPet(myValue);
            progress.setVisibility(View.VISIBLE);
            pet.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        progress.setVisibility(View.INVISIBLE);

                        Pet pet = response.body();
                        idPet.setText("Идентификатор: "+ pet.getId());
                        namePet.setText("Имя: "+ pet.getName());
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
                    Toast.makeText(GetPetActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}