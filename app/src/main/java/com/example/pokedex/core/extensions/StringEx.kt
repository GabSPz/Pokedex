package com.example.pokedex.core.extensions

fun String.firstCharUpper():String{
    return this.replaceFirstChar { it.uppercase() }
}