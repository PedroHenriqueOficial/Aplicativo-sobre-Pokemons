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

    // Passando o contexto da aplicação para o repositório
    private val repository = PokemonRepository(application)
    private val _pokemonDetails = MutableLiveData<PokemonDetailResponse>()
    val pokemonDetails: LiveData<PokemonDetailResponse> = _pokemonDetails
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    // LiveData para controlar se é favorito (true/false)
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    fun loadDetails(idOrName: String) {
        viewModelScope.launch {
            try {
                _error.value = false
                val details = repository.getPokemonDetail(idOrName)
                _pokemonDetails.value = details

                // Assim que carregar os detalhes, checa se ele já é favorito pelo ID

                checkFavoriteStatus(details.id)

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            }
        }
    }

    // Função interna para verificar no banco
    private fun checkFavoriteStatus(id: Int) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(id)
            _isFavorite.value = isFav
        }
    }

    // Função para quando clicar no coração

    fun toggleFavorite() {
        val details = _pokemonDetails.value ?: return // Se não tiver detalhes, não faz nada
        val currentStatus = _isFavorite.value ?: false

        val entity = PokemonEntity(
            id = details.id,
            name = details.name,

            // Monta a URL da imagem manualmente para salvar no banco

            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png"
        )

        viewModelScope.launch {
            if (currentStatus) {

                // Se já era favorito, remove

                repository.deleteFavorite(entity)
                _isFavorite.value = false
            } else {

                // Se não era, salva

                repository.insertFavorite(entity)
                _isFavorite.value = true
            }
        }
    }
}