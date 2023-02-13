package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private TextView namePet;
    private ImageView photoPet;
    private Button getBtn;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        namePet = (TextView) findViewById(R.id.namePat);
        photoPet = (ImageView) findViewById(R.id.imageView);
        getBtn = (Button) findViewById(R.id.btnLoad);
        PetStoreService petStoreService = PetStoreService.retrofit.create(PetStoreService.class);

        getBtn.setOnClickListener(view -> {
            Call<Pet> pet = petStoreService.getPet();
            progress.setVisibility(View.VISIBLE);
            pet.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        progress.setVisibility(View.INVISIBLE);

                        Pet pet = response.body();
                        namePet.setText(pet.getName());
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
                    Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}