package com.example.pokemon.model

import androidx.annotation.ColorRes
data class PokemonStat(
    val name: String,
    val value: Int,
    @ColorRes val color: Int
)