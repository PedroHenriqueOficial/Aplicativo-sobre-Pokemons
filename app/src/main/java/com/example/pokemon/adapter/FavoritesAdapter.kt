package com.example.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.databinding.ItemPokemonFavoritoBinding
import com.example.pokemon.model.PokemonEntity
class FavoritesAdapter(
    private val favoritesList: List<PokemonEntity>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemPokemonFavoritoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoritesList[position])
    }

    override fun getItemCount() = favoritesList.size
    inner class FavoriteViewHolder(private val binding: ItemPokemonFavoritoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonEntity) {
            // Nome
            binding.tvPokemonNameFav.text = pokemon.name.replaceFirstChar { it.uppercase() }

            // Imagem

            Glide.with(itemView.context)
                .load(pokemon.imageUrl)
                .into(binding.ivPokemonThumb)

            // Clique

            itemView.setOnClickListener {
                onItemClick(pokemon.id)
            }
        }
    }
}