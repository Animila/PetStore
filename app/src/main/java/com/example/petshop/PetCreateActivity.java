package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetCreateActivity extends AppCompatActivity {

    private Button sendBtn;
    private EditText sendName, sendTag, sendCategory, sendUrl, sendStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_create);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendName = (EditText) findViewById(R.id.sendName);
        sendTag = (EditText) findViewById(R.id.sendTag);
        sendCategory = (EditText) findViewById(R.id.sendCategory);
        sendUrl = (EditText) findViewById(R.id.sendUrl);
        sendStatus = (EditText) findViewById(R.id.sendStatus);

        sendBtn.setOnClickListener(v -> {
            PetService petService = PetService.retrofit.create(PetService.class);


            //Добавляем теги
            Tags new_tag = new Tags();
            new_tag.setId(145672);
            new_tag.setName(sendTag.getText().toString());

            ArrayList<Tags> new_tags = new ArrayList<>();
            new_tags.add(new_tag);

            //Добавляем категорию
            Category new_category = new Category();
            new_category.setId(2235451);
            new_category.setName(sendCategory.getText().toString());

            // Добавляем фотки
            List<String> photos = new ArrayList<>();
            photos.add(sendUrl.getText().toString());

            Pet new_pet = new Pet();
            Random rand = new Random();
            new_pet.setId(rand.nextInt(56));
            new_pet.setCategory(new_category);
            new_pet.setName(sendName.getText().toString());
            new_pet.setPhotoUrls(photos);
            new_pet.setTags(new_tags);
            new_pet.setStatus(sendStatus.getText().toString());

            SharedPreferences sharedPreferences = getSharedPreferences("Pets", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("new_pet", ""+new_pet.getId());
            editor.apply();


            Call<Pet> call = petService.createPet(new_pet);
            call.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    Log.d("Git", "Отправка: "+response.body());
                }

                @Override
                public void onFailure(Call<Pet> call, Throwable t) {
                    Log.d("Git", "Отправка: "+t.getMessage());
                }
            });

            Intent getActivity = new Intent(this, PetGetActivity.class);
            startActivity(getActivity);
        });

    }
}