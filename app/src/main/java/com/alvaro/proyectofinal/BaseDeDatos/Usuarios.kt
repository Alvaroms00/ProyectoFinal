package com.alvaro.proyectofinal.BaseDeDatos

data class Usuarios(
    var nombre: String? = null,
    var email: String? = null,
    var pass: String? = null,
    var puntuacionAhorcado: Int? = null,
    var puntacionTresEnRaya: Int? = null
)
