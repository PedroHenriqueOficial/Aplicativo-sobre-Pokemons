package com.example.pokemon.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemon.model.PokemonEntity

@Dao
interface PokemonDao {

    // Salvar favorito

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    // Remover favorito

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    // Listar todos os favoritos (retorna LiveData para a tela atualizar sozinha)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<PokemonEntity>>

    // Verificar se um pokémon específico já é favorito (para pintar o coração)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}