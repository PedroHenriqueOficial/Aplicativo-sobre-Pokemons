package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: List<PokemonTypeSlot>,
    @SerializedName("stats") val stats: List<PokemonStatSlot>
)

// Estruturas auxiliares para navegar no JSON complexo da PokeAPI
data class PokemonTypeSlot(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: NamedResource
)
data class PokemonStatSlot(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("stat") val stat: NamedResource
)
data class NamedResource(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)