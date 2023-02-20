package com.example.petshop;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PetService {

    @GET("pet/{petId}")
    Call<Pet> getPet(@Path("petId") Integer petId);

    @POST("pet")
    Call<Pet> createPet(@Body Pet pet);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://petstore.swagger.io/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build();
}
