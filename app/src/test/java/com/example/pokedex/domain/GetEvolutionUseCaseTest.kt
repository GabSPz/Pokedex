package com.example.pokedex.domain

import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.data.repository.EvolutionRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEvolutionUseCaseTest {

    @RelaxedMockK
    private lateinit var evolutionRepository: EvolutionRepository

    private lateinit var evolutionUseCase: GetEvolutionUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        evolutionUseCase = GetEvolutionUseCase(evolutionRepository)
    }

    @Test
    fun `if species use case is successful, the system get a evolution list`() = runBlocking {
        val evolutionTestList = EvolutionChainResponse(
            1,
            EvolutionPokemonModel(
                listOf(),
                PokemonSpecies(
                    "test",
                    "test"
                ),
                listOf()
            )
        )
        // Give
        coEvery { evolutionRepository.invoke(any()) } returns evolutionTestList

        //When
        val response = evolutionUseCase.invoke("")

        //Then
        assert(response == evolutionTestList)
    }
}