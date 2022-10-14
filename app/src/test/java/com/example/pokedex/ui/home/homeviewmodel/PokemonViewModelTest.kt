package com.example.pokedex.ui.home.homeviewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.domain.GetEvolutionUseCase
import com.example.pokedex.domain.GetPokemonUseCase
import com.example.pokedex.domain.GetSpeciesUseCase
import com.example.pokedex.ui.pokemon.PokemonViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
class PokemonViewModelTest {

    @RelaxedMockK
    private lateinit var getPokemonUseCase: GetPokemonUseCase

    @RelaxedMockK
    private lateinit var getSpeciesUseCase: GetSpeciesUseCase

    @RelaxedMockK
    private lateinit var getEvolutionUseCase: GetEvolutionUseCase

    private lateinit var pokemonViewModel: PokemonViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        pokemonViewModel = PokemonViewModel(
            getPokemonUseCase,
            getEvolutionUseCase,
            getSpeciesUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when the user click on a anyone item of the pokedex rv, the system get a pokemon and species from api`() = runBlocking{
        val pokemonTest = PokemonModel(
            "test",
            1,
            1,
            1
        )
        val speciesTest = PokemonSpeciesModel("test")
        //Give
        coEvery { getPokemonUseCase.invoke(any()) } returns pokemonTest
        coEvery { getSpeciesUseCase.invoke(any()) } returns speciesTest

        //When
        pokemonViewModel.getPokemon("")
        pokemonViewModel.getPokemonSpecies("")

        //Then
        assert(
            pokemonViewModel.pokemon.value == pokemonTest &&
                    pokemonViewModel.species.value == speciesTest
        )
    }

    @Test
    fun `if pokemon and species is different of null, the system get the pokemon evolution from api`()= runBlocking {
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
        //Give
        coEvery { getEvolutionUseCase.invoke(any()) } returns evolutionTestList

        //When
        pokemonViewModel.getEvolutionChain("")

        //Then
        assert(pokemonViewModel.evolutions.value == evolutionTestList &&
                pokemonViewModel.isLoading.value == false
        )
    }
}