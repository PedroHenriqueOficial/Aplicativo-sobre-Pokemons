package com.example.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokemon.data.PokemonRepository

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)

    // O Room retorna um LiveData, então a View pode observar isso diretamente.
    // Sempre que você salvar/deletar um favorito, essa lista atualiza sozinha!

    val favoritesList = repository.getAllFavorites()
}