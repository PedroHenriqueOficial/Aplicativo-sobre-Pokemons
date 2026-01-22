package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Botão "Esqueci Senha"

        binding.esquecerSenha.setOnClickListener {
            startActivity(Intent(this, TrocarSenhaActivity::class.java))
        }

        // Botão "Cadastrar"

        binding.cadastrar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Realiza o lofin ao clicar no botão

        binding.btnEntrar.setOnClickListener {
            realizarLogin()
        }
    }
    private fun realizarLogin() {

        val email = binding.Login.text.toString()
        val senha = binding.senha.text.toString()

        if (email.isNotEmpty() && senha.isNotEmpty()) {

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // Se preenchido corretamente vai para a Main

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    // Verifica se já está logado ao abrir

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}