package com.example.pokedex.domain

import com.example.pokedex.data.PokedexRepository
import com.example.pokedex.data.model.PokedexModel
import javax.inject.Inject

class GetPokedexUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    suspend fun getPokedex(): List<PokedexModel> = repository.getPokedex()
}