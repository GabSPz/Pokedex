package com.example.pokedex.ui.home.homeviewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.domain.GetEvolutionUseCase
import com.example.pokedex.domain.GetPokedexUseCase
import com.example.pokedex.domain.GetSpeciesUseCase
import com.example.pokedex.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var getPokedexUseCase: GetPokedexUseCase

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(getPokedexUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the home view model has init, it get pokedex list from api and set in pokedexList live data`() = runBlocking {
        //Give
        val pokedexTestList = listOf<PokedexModel>(
            PokedexModel(
                1,
                PokemonSpecies(
                    "test",
                    "test"
                )
            )
        )
        coEvery { getPokedexUseCase.getPokedex() } returns pokedexTestList

        //When
        homeViewModel.onCreate()

        //Then
        assert (homeViewModel.pokedexList.value == pokedexTestList)
    }
}