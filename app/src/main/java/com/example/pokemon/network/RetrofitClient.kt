package com.example.pokemon.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {

    // URL base da API (sempre termina com /)
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    // Criação da instância do Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Converte JSON para Classes
            .build()
    }

    // Criação do serviço que usaremos no app inteiro

    val service: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}