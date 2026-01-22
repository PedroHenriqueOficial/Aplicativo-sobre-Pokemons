package com.example.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.PokemonResult
import kotlinx.coroutines.launch
class PokemonListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)

    // LiveData para a lista que vai para a tela
    private val _pokemonList = MutableLiveData<List<PokemonResult>>()
    val pokemonList: LiveData<List<PokemonResult>> = _pokemonList

    // Controle de carregamento (loading)
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Guarda todos os pokémons carregados até agora na memória (lista mestra)
    private val listaAcumulada = mutableListOf<PokemonResult>()

    // Variáveis de paginação
    private var currentOffset = 0
    private val PAGE_SIZE = 20

    fun loadPokemons() {

        // Evita chamadas duplicadas se já estiver carregando

        if (_loading.value == true) return

        _loading.value = true

        viewModelScope.launch {
            try {

                // Busca os novos dados no Repositório

                val response = repository.getPokemons(PAGE_SIZE, currentOffset)

                // Adiciona os novos itens na nossa lista Mestra

                listaAcumulada.addAll(response.results)

                // Publica a lista mestra completa (Antigos + Novos) para a tela

                _pokemonList.value = listaAcumulada

                // Prepara o offset para a próxima página

                currentOffset += PAGE_SIZE

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}