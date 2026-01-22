package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.viewmodel.FavoritosFragment
import com.example.pokemon.viewmodel.HomeFragment
import com.example.pokemon.viewmodel.ListaPokemonFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carrega o Fragmento inicial (Home/Busca)

        replaceFragment(HomeFragment())

        // Configura os cliques da barra de navegação

        binding.busca.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        binding.lista.setOnClickListener {
            replaceFragment(ListaPokemonFragment())
        }

        binding.favorito.setOnClickListener {
            replaceFragment(FavoritosFragment())
        }

        // Configura o clique no Ícone de Perfil (Logout)

        binding.ivPerfil.setOnClickListener { view ->

            // Cria o menu flutuante ancorado na imagem

            val popup = PopupMenu(this, view)

            // Adiciona a opção "Sair"

            popup.menu.add("Sair")

            // Define o que acontece ao clicar na opção

            popup.setOnMenuItemClickListener { item ->
                if (item.title == "Sair") {
                    deslogarUsuario()
                }
                true
            }

            popup.show() // Exibe o menu
        }
    }

    // Função auxiliar para trocar de Fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Função para deslogar e voltar ao Login
    private fun deslogarUsuario() {

        FirebaseAuth.getInstance().signOut() // Desloga do Firebase

        val intent = Intent(this, LoginActivity::class.java) // Prepara a ida para a tela de Login

        // Limpa a pilha de atividades para que o botão "Voltar" não retorne para cá

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}