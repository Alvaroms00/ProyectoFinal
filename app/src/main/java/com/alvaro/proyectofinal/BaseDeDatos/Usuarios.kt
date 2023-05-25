package com.alvaro.proyectofinal.BaseDeDatos

//Creamos las instancias para nuetros datos dentro de la base de datos
data class Usuarios(
    var nombre: String? = null,
    var email: String? = null,
    var pass: String? = null,
    var puntuacionAhorcado: Int? = null,
    var puntuacionTresEnRaya: Int? = null
)
