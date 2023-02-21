package com.example.petshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetCreateActivity extends AppCompatActivity {

    private Button sendBtn, stepBtn;
    private EditText sendId, sendName, sendTag, sendCategory, sendUrl, sendStatus;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_create);
        setElements();


        stepBtn.setOnClickListener(v -> {
            Intent step = new Intent(PetCreateActivity.this, PetNavigationActivity.class);
            startActivity(step);
            finish();
        });



        sendBtn.setOnClickListener(v -> {
            sendBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

//            Проверка на поля
            Boolean idIsZeroSymbol = sendId.getText().toString().length() == 0;
            Boolean idIsNull = sendId.getText().toString().isEmpty();
            Boolean idNotIsInteger = !TextUtils.isDigitsOnly(sendId.getText().toString());
            Boolean inputsIsNull =
                    sendName.getText().toString().isEmpty() ||
                            sendCategory.getText().toString().isEmpty() ||
                            sendStatus.getText().toString().isEmpty() ||
                            sendTag.getText().toString().isEmpty() ||
                            sendUrl.getText().toString().isEmpty();

//            Установка Id
            int id;
            if ( idIsZeroSymbol || idIsNull || idNotIsInteger ) {
                Random rand = new Random();
                id = rand.nextInt(100);
            } else {
                id = Integer.parseInt(sendId.getText().toString());
            }

            if(inputsIsNull) {
                Toast.makeText(this, "Заполните все нужные поля", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                sendBtn.setEnabled(true);
                return;
            }

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

//            Добавляем животное
            Pet new_pet = new Pet();
            new_pet.setId(id);
            new_pet.setCategory(new_category);
            new_pet.setName(sendName.getText().toString());
            new_pet.setPhotoUrls(photos);
            new_pet.setTags(new_tags);
            new_pet.setStatus(sendStatus.getText().toString());

//            Сохраняем идентификатор животного
            SharedPreferences sharedPreferences = getSharedPreferences("Pets", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("new_pet", ""+new_pet.getId());
            editor.apply();

//            Загрузка
            PetService petService = PetService.retrofit.create(PetService.class);
            Call<Pet> call = petService.createPet(new_pet);
            call.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(PetCreateActivity.this, "Создание завершено успешно.\n Идентификатор животного: "+new_pet.getId(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent getActivity = new Intent(PetCreateActivity.this, PetGetActivity.class);
                        startActivity(getActivity);
                        finish();
                    } else {
                        Toast.makeText(PetCreateActivity.this, "Ошибка при создании: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        sendBtn.setEnabled(true);
                    }

                }

                @Override
                public void onFailure(Call<Pet> call, Throwable t) {
                    Toast.makeText(PetCreateActivity.this, "Ошибка отправки: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Git", "Ошибка: "+t.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    sendBtn.setEnabled(true);
                }
            });


        });

    }

    private void setElements() {
        //  Поля
        sendId = (EditText) findViewById(R.id.sendID);
        sendName = (EditText) findViewById(R.id.sendName);
        sendTag = (EditText) findViewById(R.id.sendTag);
        sendCategory = (EditText) findViewById(R.id.sendCategory);
        sendUrl = (EditText) findViewById(R.id.sendUrl);
        sendStatus = (EditText) findViewById(R.id.sendStatus);

        //  Кнопки
        stepBtn = (Button) findViewById(R.id.btnStep2);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.INVISIBLE);
    }
}