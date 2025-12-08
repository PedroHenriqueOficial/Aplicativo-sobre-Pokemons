package com.example.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")

data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
