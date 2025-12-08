package com.example.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.PokemonResult
import kotlinx.coroutines.launch
class PokemonListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)

    // LiveData: A Activity vai "observar" essa variável e quando ela mudar, a tela atualiza automaticamente.
    private val _pokemonList = MutableLiveData<List<PokemonResult>>()
    val pokemonList: LiveData<List<PokemonResult>> = _pokemonList

    // Controle de erro e carregamento
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Variáveis para controlar a paginação
    private var currentOffset = 0
    private val PAGE_SIZE = 20

    // Bloco de inicialização: carrega os primeiros pokémons assim que a ViewModel nasce

    init {
        loadPokemons()
    }
    fun loadPokemons() {

        // Evita carregar se já estiver carregando (útil para paginação)

        if (_loading.value == true) return

        _loading.value = true

        // Lança uma Coroutine (processo em segundo plano)

        viewModelScope.launch {
            try {

                // Chama o repositório passando o limite e o deslocamento (offset)

                val response = repository.getPokemons(PAGE_SIZE, currentOffset)

                // Atualiza a lista
                // Nota: Para paginação real, precisamos somar a lista antiga com a nova.

                val currentList = _pokemonList.value.orEmpty().toMutableList()
                currentList.addAll(response.results)

                _pokemonList.value = currentList

                // Atualiza o offset para a próxima página

                currentOffset += PAGE_SIZE

            } catch (e: Exception) {

                // Tratamento de erro

                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}