package com.example.pokedex.domain

import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.repository.SpeciesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSpeciesUseCaseTest {

    @RelaxedMockK
    private lateinit var speciesRepository: SpeciesRepository

    private lateinit var getSpeciesUseCase: GetSpeciesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getSpeciesUseCase = GetSpeciesUseCase(speciesRepository)
    }

    @Test
    fun `if get pokemon use case is successful, the system get pokemon species from api`() = runBlocking {
       val speciesTest = PokemonSpeciesModel(
           "test"
       )
        //Give
        coEvery { speciesRepository.invoke(any()) } returns speciesTest

        //When
        val response = getSpeciesUseCase.invoke("")

        //Then
        assert(response == speciesTest)
    }
}