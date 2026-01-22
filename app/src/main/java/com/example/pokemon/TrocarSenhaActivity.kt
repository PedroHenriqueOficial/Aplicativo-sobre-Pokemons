package com.example.pokemon

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityTrocarSenhaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class TrocarSenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrocarSenhaBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrocarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Botão Voltar

        binding.toolbarReport.setNavigationOnClickListener { finish() }

        // Botão Enviar

        binding.encerramento.setOnClickListener {
            val email = binding.email.text.toString().trim()

            // Validação de campo vazio

            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu e-mail.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            verificarSeEmailExiste(email) // Checa se o e-mail existe no Realtime Database
        }
    }
    private fun verificarSeEmailExiste(email: String) {
        val dbRef = FirebaseDatabase.getInstance().reference

        // Faz uma busca na tabela "users"

        val query = dbRef.child("users").orderByChild("email").equalTo(email)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    enviarEmailRecuperacao(email) // Se o email foi encontrado no banco, envia
                } else {

                    // O Email não existe no nosso banco de dados.

                    Toast.makeText(this@TrocarSenhaActivity, "Email não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TrocarSenhaActivity, "Erro ao verificar cadastro: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun enviarEmailRecuperacao(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email enviado com sucesso!", Toast.LENGTH_LONG).show()
                    finish() // Volta para o Login
                } else {

                    // Como já verificamos que o usuário existe no banco, erros aqui são raros (ex: falta de internet)

                    Toast.makeText(this, "Erro no envio: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}