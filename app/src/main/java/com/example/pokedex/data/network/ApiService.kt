package com.example.pokedex.data.network

import com.example.pokedex.data.network.responses.PokedexResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("pokedex/1")
    suspend fun getPokedex(): Response<PokedexResponse>
}