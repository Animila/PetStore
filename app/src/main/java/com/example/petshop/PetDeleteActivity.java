package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDeleteActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnStep, btnDelete;
    private EditText deleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_delete);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        btnStep = (Button) findViewById(R.id.btnStepDelete);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        deleteId = (EditText) findViewById(R.id.id_pet_delete);

        progressBar.setVisibility(View.INVISIBLE);


        btnStep.setOnClickListener(v -> {
            Intent step = new Intent(this, PetNavigationActivity.class);
            startActivity(step);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            btnDelete.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            if(!TextUtils.isDigitsOnly(deleteId.getText().toString())) {
                Toast.makeText(this, "Введите только числовые значения!", Toast.LENGTH_SHORT).show();
                deleteId.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            PetService petService = PetService.retrofit.create(PetService.class);
            Call<Pet> pet = petService.deletePet(Integer.parseInt(deleteId.getText().toString()));
            pet.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(PetDeleteActivity.this, "Удаление завершено успешно", Toast.LENGTH_SHORT).show();
                        deleteId.setText("");
                        btnDelete.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(PetDeleteActivity.this, "Ошибка удаления: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                        btnDelete.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("Git", ""+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Pet> call, Throwable t) {
                    Toast.makeText(PetDeleteActivity.this, "Ошибка: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Git", ""+t.getMessage());
                    btnDelete.setEnabled(true);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        });
    }

}