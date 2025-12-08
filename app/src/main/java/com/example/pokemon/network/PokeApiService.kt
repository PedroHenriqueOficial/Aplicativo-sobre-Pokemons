package com.example.pokemon.network

import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.model.PokemonListResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface PokeApiService {

    // Endpoint para listar Pokémons (Paginação)
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    // Endpoint para detalhes de um Pokémon específico
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): PokemonDetailResponse
}