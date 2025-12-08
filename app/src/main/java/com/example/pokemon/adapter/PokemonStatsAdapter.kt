package com.example.pokemon.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.ItemPokemonStatBinding
import com.example.pokemon.model.PokemonStat
class PokemonStatsAdapter(
    private val statsList: List<PokemonStat>
) : RecyclerView.Adapter<PokemonStatsAdapter.StatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val binding = ItemPokemonStatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(statsList[position])
    }

    override fun getItemCount(): Int = statsList.size
    class StatViewHolder(private val binding: ItemPokemonStatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: PokemonStat) {
            binding.tvStatName.text = stat.name
            binding.tvStatValue.text = stat.value.toString()

            // Configura a barra de progresso

            binding.progressStat.progress = stat.value

            // Muda a cor da barra dinamicamente

            val color = ContextCompat.getColor(itemView.context, stat.color)
            binding.progressStat.progressTintList = ColorStateList.valueOf(color)
        }
    }
}