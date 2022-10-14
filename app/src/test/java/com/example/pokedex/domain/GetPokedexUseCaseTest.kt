package com.example.pokedex.domain

import com.example.pokedex.data.repository.PokedexRepository
import com.example.pokedex.domain.GetPokedexUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPokedexUseCaseTest {

    @RelaxedMockK
    private lateinit var pokedexRepository: PokedexRepository

    private lateinit var getPokedexUseCase: GetPokedexUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getPokedexUseCase = GetPokedexUseCase(pokedexRepository)
    }

    @Test
    fun `when the app init, the system shows a pokemon list`() = runBlocking {
        //Given
        coEvery { pokedexRepository.getPokedex() } returns emptyList()

        //When
        val response = getPokedexUseCase.getPokedex()

        //Then
        assert(response.isEmpty())
    }

}