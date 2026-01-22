package com.example.pokemon.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.adapter.FavoritesAdapter
import com.example.pokemon.databinding.ActivityFavoritosBinding
import com.example.pokemon.model.PokemonEntity
class FavoritosFragment : Fragment() {
    private var _binding: ActivityFavoritosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoritosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o ViewModel

        viewModel = ViewModelProvider(this)[FavoritosViewModel::class.java]

        // Configura a RecyclerView

        binding.rvFavoritos.layoutManager = LinearLayoutManager(requireContext())

        // Observa a lista do banco de dados

        viewModel.favoritesList.observe(viewLifecycleOwner) { listaFavoritos ->

            // Verifica se a lista está vazia e configura o Adapter

            val adapter = FavoritesAdapter(listaFavoritos,
                onItemClick = { pokemonId ->
                },
                onFavoriteClick = { pokemonEntity ->

                    // Alert Dialog

                    showDeleteDialog(pokemonEntity)
                }
            )
            binding.rvFavoritos.adapter = adapter
        }
    }

    // Função para exibir o Dialog
    private fun showDeleteDialog(pokemon: PokemonEntity) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Remover Favorito")
            .setMessage("Tem certeza que deseja remover ${pokemon.name} dos favoritos?")
            .setPositiveButton("Sim") { dialog, _ ->

                // Chama a ViewModel para deletar

                viewModel.removeFavorite(pokemon)
                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}