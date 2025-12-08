package com.example.pokemon.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pokemon.DetalhesActivity
import com.example.pokemon.databinding.FragmentHomeBinding
class HomeFragment : Fragment() {

    // Configuração do ViewBinding para Fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o clique do botão "Buscar"

        binding.buttonBuscar.setOnClickListener {
            val nomePokemon = binding.nomePokemon.text.toString().trim().lowercase()

            if (nomePokemon.isNotEmpty()) {
                val intent = Intent(requireContext(), DetalhesActivity::class.java)

                // Passamos o nome digitado para a próxima tela

                intent.putExtra("POKEMON_KEY", nomePokemon)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Digite um nome!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Limpeza do binding para evitar memory leaks

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}