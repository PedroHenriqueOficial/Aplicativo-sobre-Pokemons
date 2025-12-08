package com.example.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.viewmodel.FavoritosFragment
import com.example.pokemon.viewmodel.HomeFragment
import com.example.pokemon.viewmodel.ListaPokemonFragment
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carrega o Fragmento inicial (Home/Busca)

        replaceFragment(HomeFragment())

        // Configura os cliques da barra superior

        binding.busca.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        binding.lista.setOnClickListener {
            replaceFragment(ListaPokemonFragment())
        }

        binding.favorito.setOnClickListener {
            replaceFragment(FavoritosFragment())
        }
    }

    // Função auxiliar para trocar de Fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
