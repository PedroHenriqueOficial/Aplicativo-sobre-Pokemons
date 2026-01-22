package com.example.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.model.PokemonEntity
import kotlinx.coroutines.launch
class PokemonDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)
    private val _pokemonDetails = MutableLiveData<PokemonDetailResponse>()
    val pokemonDetails: LiveData<PokemonDetailResponse> = _pokemonDetails
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    fun loadDetails(idOrName: String) {
        viewModelScope.launch {
            try {
                _error.value = false
                val details = repository.getPokemonDetail(idOrName)
                _pokemonDetails.value = details

                // Assim que carrega, verifica no Firebase se é favorito

                checkFavoriteStatus(details.id)

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            }
        }
    }
    private fun checkFavoriteStatus(id: Int) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(id)
            _isFavorite.value = isFav
        }
    }
    fun toggleFavorite() {
        val details = _pokemonDetails.value ?: return
        val currentStatus = _isFavorite.value ?: false

        // Cria o objeto para salvar/remover

        val entity = PokemonEntity(
            id = details.id,
            name = details.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png"
        )

        viewModelScope.launch {
            if (currentStatus) {
                repository.deleteFavorite(entity)
                _isFavorite.value = false
            } else {
                repository.insertFavorite(entity)
                _isFavorite.value = true
            }
        }
    }
}