package com.example.pokemon.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.DetalhesActivity
import com.example.pokemon.adapter.PokemonListAdapter
import com.example.pokemon.databinding.ActivityListaPokemonBinding
class ListaPokemonFragment : Fragment() {
    private var _binding: ActivityListaPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PokemonListViewModel

    // Declara o adapter como variável da classe para usar em vários lugares
    private lateinit var adapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityListaPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        // Carrega dados iniciais apenas se a lista estiver vazia

        if (viewModel.pokemonList.value.isNullOrEmpty()) {
            viewModel.loadPokemons()
        }

        binding.btnCarregarMais.setOnClickListener {
            viewModel.loadPokemons()
        }
    }
    private fun setupRecyclerView() {

        // Inicializa o adapter uma única vez com lista vazia e a lógica de clique

        adapter = PokemonListAdapter(emptyList()) { url ->

            // Lógica para extrair o ID e abrir os detalhes

            val pokemonId = url.split("/").dropLast(1).last()

            val intent = Intent(requireContext(), DetalhesActivity::class.java)
            intent.putExtra("POKEMON_ID", pokemonId)
            startActivity(intent)
        }

        binding.rvListaPokemon.layoutManager = GridLayoutManager(context, 3)

        binding.rvListaPokemon.adapter = adapter
    }
    private fun observeViewModel() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { listaPokemons ->
            adapter.updateList(listaPokemons)
        }

        // Observa o estado de carregamento

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {

                // Se estiver carregando, mostra a barra e esconde o botão "Carregar Mais"

                binding.progressBar.visibility = View.VISIBLE
                binding.btnCarregarMais.isEnabled = false // Evita cliques duplos
            } else {

                // Quando terminar, esconde a barra e libera o botão

                binding.progressBar.visibility = View.GONE
                binding.btnCarregarMais.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}