package com.example.pokemon.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.adapter.PokemonListAdapter
import com.example.pokemon.databinding.ActivityListaPokemonBinding
class ListaPokemonFragment : Fragment() {
    private var _binding: ActivityListaPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PokemonListViewModel

    // Inflar o layout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityListaPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configura a lógica

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        binding.btnCarregarMais.setOnClickListener {
            viewModel.loadPokemons()
        }
    }
    private fun setupRecyclerView() {
        binding.rvListaPokemon.layoutManager = GridLayoutManager(context, 3)
    }
    private fun observeViewModel() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { listaPokemons ->
            // Atualiza o adapter sem passar função de clique
            val adapter = PokemonListAdapter(listaPokemons)
            binding.rvListaPokemon.adapter = adapter
        }

        // Observa erros ou carregamento

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                Toast.makeText(requireContext(), "Carregando...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}