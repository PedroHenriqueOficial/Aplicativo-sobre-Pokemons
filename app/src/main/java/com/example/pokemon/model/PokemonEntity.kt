package com.example.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")

data class PokemonEntity(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var imageUrl: String = ""
)
