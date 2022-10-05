package com.example.pokedex.data.network

import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.data.network.responses.PokedexResponse
import com.example.pokedex.data.network.responses.PokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("pokedex/1")
    suspend fun getPokedex(): Response<PokedexResponse>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemon(@Path("pokemonId") pokemonID: String): Response<PokemonModel>

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutionChain(@Path("id") pokemonID: String): Response<EvolutionChainResponse>

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonSpecies(@Path("id") pokemonID: String): Response<PokemonSpeciesResponse>
}