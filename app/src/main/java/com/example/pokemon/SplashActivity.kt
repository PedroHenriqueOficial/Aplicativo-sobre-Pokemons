package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Configura o temporizador para 5 segundos (5000ms)

        Handler(Looper.getMainLooper()).postDelayed({
            verificarLogin()
        }, 5000)
    }
    private fun verificarLogin() {
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {

            // Se o usuário JÁ existe, vai direto para a Home (MainActivity)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {

            // Se NÃO tem usuário logado, manda para o Login

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        finish() // Fecha a Splash para o usuário não voltar nela com o botão voltar
    }
}