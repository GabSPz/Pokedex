package com.example.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.database.entities.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDataBase: RoomDatabase() {
     abstract fun getPokemonDao(): PokemonDao
 }