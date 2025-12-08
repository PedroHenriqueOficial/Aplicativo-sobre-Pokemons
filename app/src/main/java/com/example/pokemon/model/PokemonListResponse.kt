package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

// Representa a resposta da lista (página)

data class PokemonListResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<PokemonResult>
)

// Representa cada item simples da lista (apenas nome e url)
data class PokemonResult(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
    // Lógica para extrair o ID da URL (ex: .../pokemon/1/) para pegar a imagem

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }

    fun getId(): Int {
        return url.split("/".toRegex()).dropLast(1).last().toInt()
    }
}