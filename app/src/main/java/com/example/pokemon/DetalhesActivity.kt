package com.example.pokemon

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemon.adapter.PokemonStatsAdapter
import com.example.pokemon.databinding.ActivityBuscaBinding
import com.example.pokemon.model.PokemonStat
import com.example.pokemon.viewmodel.PokemonDetailViewModel
class DetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuscaBinding
    private lateinit var viewModel: PokemonDetailViewModel
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura Toolbar (Botão Voltar)

        binding.toolbarReport.setNavigationOnClickListener { finish() }

        // Pegar o ID enviado pela lista

        val pokemonName = intent.getStringExtra("POKEMON_KEY") ?: "bulbasaur"

        // Inicializa ViewModel e pedir dados

        viewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]
        viewModel.loadDetails(pokemonName)

        // Observa o estado do Favorito (Mudar a cor do ícone)

        viewModel.isFavorite.observe(this) { isFav ->
            if (isFav) {
                binding.ivFavorite.setImageResource(R.drawable.ic_heart_filled)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_heart_outline)
            }

            // Lógica do Snackbar

            if (!isFirstLoad) {
                val mensagem = if (isFav) "Adicionado aos favoritos" else "Removido dos favoritos"

                com.google.android.material.snackbar.Snackbar.make(
                    binding.root,
                    mensagem,
                    com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
                ).setAction("Desfazer") {

                    // Ação para desfazer o clique

                    viewModel.toggleFavorite()
                }.show()
            }

            isFirstLoad = false
        }

        // Configura o Clique no Coração

        binding.ivFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        // Observa a resposta e preencher a tela

        viewModel.pokemonDetails.observe(this) { details ->

            // Imagem

            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png"
            Glide.with(this).load(imageUrl).into(binding.ivPokemonImage)

            val type1 = binding.containerTypes.getChildAt(0) as TextView
            val type2 = binding.containerTypes.getChildAt(1) as TextView

            if (details.types.isNotEmpty()) {
                type1.text = details.types[0].type.name.capitalize()
                type1.visibility = View.VISIBLE
            }

            if (details.types.size > 1) {
                type2.text = details.types[1].type.name.capitalize()
                type2.visibility = View.VISIBLE
            } else {
                type2.visibility = View.GONE
            }

            // Stats (RecyclerView)

            val statsList = details.stats.map { slot ->

                // Mapeia o nome da API (hp, attack) para o seu visual (PS, Ataque)

                val (name, color) = when (slot.stat.name) {
                    "hp" -> "PS" to R.color.light_red
                    "attack" -> "Ataque" to R.color.light_orange
                    "defense" -> "Defesa" to R.color.light_yellow
                    "special-attack" -> "Atq. Esp." to R.color.light_blue
                    "special-defense" -> "Def. Esp." to R.color.light_green
                    "speed" -> "Veloc." to R.color.light_pink
                    else -> slot.stat.name to android.R.color.darker_gray
                }
                PokemonStat(name, slot.baseStat, color)
            }

            binding.rvStats.layoutManager = LinearLayoutManager(this)
            binding.rvStats.adapter = PokemonStatsAdapter(statsList)
        }

        // Observa se deu erro (Pokémon não encontrado)

        viewModel.error.observe(this) { hasError ->
            if (hasError) {
                Toast.makeText(this, "Pokémon não encontrado ou nome inválido!", Toast.LENGTH_LONG).show()

                // Fecha a tela de detalhes e volta para a anterior

                finish()
            }
        }
    }
}