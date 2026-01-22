package com.example.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.databinding.ItemPokemonGridBinding
import com.example.pokemon.model.PokemonResult
class PokemonListAdapter(
    private var pokemonList: List<PokemonResult>,
    private val onItemClick: (String) -> Unit
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

    fun updateList(newList: List<PokemonResult>) {
        pokemonList = newList // Atualiza a lista interna
        notifyDataSetChanged() // Avisa a tela para se atualizar (sem resetar o scroll)
    }
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