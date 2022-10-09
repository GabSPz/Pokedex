package com.example.pokedex.core.extensions

fun String.firstCharUpper():String{
    return this.replaceFirstChar { it.uppercase() }
}

fun String.getPokemonIdByUrl():String{
    var id = ""
    var cont = 2
    var flag = true

    while (flag){
        val number = this[this.length - cont].toString()
        if (number != "/"){
            id = number + id
            cont += 1
        } else{
            flag = false
        }
    }
    return id
}