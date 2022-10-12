package com.example.pokedex

import com.example.pokedex.data.repository.PokemonRepository
import com.example.pokedex.domain.GetPokemonUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPokemonUseCaseTest {

    @RelaxedMockK
    private lateinit var pokemonRepository: PokemonRepository

    private lateinit var getPokemonUseCase: GetPokemonUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getPokemonUseCase = GetPokemonUseCase(pokemonRepository)
    }

    @Test
    fun `when the user click in someone pokemon of the pokedex list, the system show pokemon info`() = runBlocking {
        val id = ""
        //Given
        coEvery { pokemonRepository.getPokemon(any()) } returns null
        coEvery { pokemonRepository.getPokemonSpecies(any()) } returns null

        //When
        getPokemonUseCase.getPokemonSpecies(id)
        val response = getPokemonUseCase.getPokemon(id)

        //Then
        coVerify (exactly = 0){ getPokemonUseCase.getEvolutionChain(id) }
        assert(response == null)
    }
}