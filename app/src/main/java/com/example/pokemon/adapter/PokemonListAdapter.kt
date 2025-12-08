package com.example.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.databinding.ItemPokemonGridBinding
import com.example.pokemon.model.PokemonResult
class PokemonListAdapter(
    private val pokemonList: List<PokemonResult>,
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    // Cria o visual do item (infla o layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PokemonViewHolder(binding)
    }

    // Preenche os dados em cada posição

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size
    inner class PokemonViewHolder(private val binding: ItemPokemonGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonResult) {
            binding.tvGridName.text = pokemon.name.replaceFirstChar { it.uppercase() }

            Glide.with(itemView.context)
                .load(pokemon.getImageUrl())
                .into(binding.ivGridImage)
        }
    }
}