package com.example.pokedex.domain

import com.example.pokedex.data.repository.PokedexRepository
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import javax.inject.Inject

class GetPokedexUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    suspend fun getPokedex(): List<PokedexModel> = repository.getPokedex()
}