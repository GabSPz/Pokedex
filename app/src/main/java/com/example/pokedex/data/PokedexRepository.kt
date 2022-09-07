package com.example.pokedex.data

import com.example.pokedex.data.model.PokedexModel
import com.example.pokedex.data.network.PokedexService
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val pokedexService: PokedexService
) {
    suspend fun getPokedex(): List<PokedexModel>{
        return pokedexService.getPokedex()
    }
}