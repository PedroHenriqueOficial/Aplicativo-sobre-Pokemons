package com.example.pokemon

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Botão de voltar

        binding.toolbarReport.setNavigationOnClickListener { finish() }

        binding.encerramento.setOnClickListener {

            // 1. Pegar todos os valores e remover espaços em branco no início/fim

            val nome = binding.nome.text.toString().trim()
            val nick = binding.nick.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val senha1 = binding.senha1.text.toString() // Senha não usamos trim para permitir espaços se o usuário quiser
            val senha2 = binding.senha2.text.toString()

            // Verificar se algum dos 5 campos está vazio

            if (nome.isEmpty() || nick.isEmpty() || email.isEmpty() || senha1.isEmpty() || senha2.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar igualdade das senhas

            if (senha1 != senha2) {
                Toast.makeText(this, "Senhas diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Criar usuário no Firebase Auth

            auth.createUserWithEmailAndPassword(email, senha1)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // Se o cadastro de login funcionar, salvamos os dados extras (Nome e Nick)

                        val userId = task.result.user?.uid
                        val database = FirebaseDatabase.getInstance().reference

                        // Cria um objeto/mapa para salvar tudo de uma vez

                        val dadosUsuario = hashMapOf(
                            "name" to nome,
                            "nick" to nick,
                            "email" to email
                        )

                        // Salva em: users -> UID

                        userId?.let { uid ->
                            database.child("users").child(uid).updateChildren(dadosUsuario as Map<String, Any>)
                        }

                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                        auth.signOut() // Desloga para forçar o usuário a logar na próxima tela

                        finish() // Fecha a tela e volta para o Login
                    } else {

                        // Tratamento de erros comuns

                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "Este e-mail já está cadastrado.", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(this, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}