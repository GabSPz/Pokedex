package com.example.pokedex.core.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.database.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val FAVORITE_POKEMON_DB_NAME = "favorite_pokemon_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PokemonDataBase::class.java,
        FAVORITE_POKEMON_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDataBase) = db.getPokemonDao()
}