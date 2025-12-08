package com.example.pokemon.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.DetalhesActivity
import com.example.pokemon.adapter.FavoritesAdapter
import com.example.pokemon.databinding.ActivityFavoritosBinding
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

            val adapter = FavoritesAdapter(listaFavoritos) { pokemonId ->

                val intent = Intent(requireContext(), DetalhesActivity::class.java)
                intent.putExtra("POKEMON_KEY", pokemonId.toString())
                startActivity(intent)
            }

            binding.rvFavoritos.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}