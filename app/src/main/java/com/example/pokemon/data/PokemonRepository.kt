package com.example.pokemon.data

import android.content.Context
import com.example.pokemon.model.PokemonEntity
import com.example.pokemon.network.RetrofitClient

// O repositório recebe o Contexto para criar o banco
class PokemonRepository(context: Context) {
    private val api = RetrofitClient.service
    private val db = PokemonDatabase.getDatabase(context)
    private val dao = db.pokemonDao()

    // API
    suspend fun getPokemons(limit: Int, offset: Int) = api.getPokemonList(limit, offset)
    suspend fun getPokemonDetail(id: String) = api.getPokemonDetail(id)
    suspend fun getPokemonDetail(id: Int) = api.getPokemonDetail(id.toString())

    // BANCO DE DADOS (FAVORITOS)

    suspend fun insertFavorite(pokemon: PokemonEntity) = dao.insert(pokemon)

    suspend fun deleteFavorite(pokemon: PokemonEntity) = dao.delete(pokemon)

    // Retorna LiveData (não precisa de suspend) pois o Room já trata isso

    fun getAllFavorites() = dao.getAllFavorites()

    suspend fun isFavorite(id: Int) = dao.isFavorite(id)
}