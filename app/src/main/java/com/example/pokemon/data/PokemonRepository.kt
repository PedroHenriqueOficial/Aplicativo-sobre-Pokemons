package com.example.pokemon.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemon.model.PokemonEntity
import com.example.pokemon.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
class PokemonRepository(context: Context) {

    // API (Retrofit)
    private val api = RetrofitClient.service

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = FirebaseDatabase.getInstance().reference

    // API CALLS
    suspend fun getPokemons(limit: Int, offset: Int) = api.getPokemonList(limit, offset)
    suspend fun getPokemonDetail(id: String) = api.getPokemonDetail(id)
    suspend fun getPokemonDetail(id: Int) = api.getPokemonDetail(id.toString())

    // Salvar favoritos (firebase)
    fun insertFavorite(pokemon: PokemonEntity) {
        val uid = auth.currentUser?.uid ?: return

        // Salva em: users > UID > favorites > ID_DO_POKEMON

        dbRef.child("users").child(uid).child("favorites")
            .child(pokemon.id.toString())
            .setValue(pokemon)
    }

    // Remover favoritos (firebase)
    fun deleteFavorite(pokemon: PokemonEntity) {
        val uid = auth.currentUser?.uid ?: return
        dbRef.child("users").child(uid).child("favorites")
            .child(pokemon.id.toString())
            .removeValue()
    }

    // Listar todos (adaptação para o Livedata) (firebase)
    fun getAllFavorites(): LiveData<List<PokemonEntity>> {
        val liveData = MutableLiveData<List<PokemonEntity>>()
        val uid = auth.currentUser?.uid

        // Se não tiver logado, retorna lista vazia

        if (uid == null) {
            liveData.value = emptyList()
            return liveData
        }

        // "Ouve" o banco de dados em tempo real

        val myFavoritesRef = dbRef.child("users").child(uid).child("favorites")

        myFavoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaTemp = mutableListOf<PokemonEntity>()

                // Percorre todos os filhos (pokemons salvos)

                for (item in snapshot.children) {
                    val pokemon = item.getValue(PokemonEntity::class.java)
                    pokemon?.let { listaTemp.add(it) }
                }

                // Atualiza o LiveData (e a tela automaticamente!)

                liveData.postValue(listaTemp)
            }

            override fun onCancelled(error: DatabaseError) {

                // Em caso de erro, pode logar ou ignorar
            }
        })

        return liveData
    }

    // Verificar se é favorito para pintar o coração (firebase)
    suspend fun isFavorite(id: Int): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {

            // Tenta pegar o dado daquele ID específico uma única vez

            val snapshot = dbRef.child("users").child(uid).child("favorites")
                .child(id.toString())
                .get().await() // .await() transforma a Task do Firebase em algo que o Kotlin espera

            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }
}