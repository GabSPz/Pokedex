package com.example.pokedex.core.extensions

fun String.firstCharUpper():String{
    return this.replaceFirstChar { it.uppercase() }
}

fun String.getPokemonIdByUrl():String{
    return this[this.length -2].toString()
}