package com.example.pokedex.data.network

import com.example.pokedex.data.model.PokedexModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokedexService @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPokedex(): List<PokedexModel> {
        return withContext(Dispatchers.IO){
            val result = apiService.getPokedex()
            result.body()?.pokemonEntries ?: emptyList()
        }
    }
}