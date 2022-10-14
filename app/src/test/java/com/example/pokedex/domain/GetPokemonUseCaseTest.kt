package com.example.pokedex.domain

import com.example.pokedex.data.model.pokemonModel.PokemonModel
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
    fun `when the user click in someone pokemon of the pokedex list, the system get pokemon info from api`() = runBlocking {
        val id = ""
        val pokemonTest = PokemonModel(
            "test",
            1,
            1,
            1
        )
        //Given
        coEvery { pokemonRepository.invoke(any()) } returns pokemonTest

        //When
        val response = getPokemonUseCase.invoke(id)

        //Then
        assert(response == pokemonTest)
    }
}