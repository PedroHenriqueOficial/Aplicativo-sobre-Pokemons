package com.example.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.PokemonEntity
class FavoritosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)
    val favoritesList = repository.getAllFavorites()
    fun removeFavorite(pokemon: PokemonEntity) {
            repository.deleteFavorite(pokemon)
    }
}