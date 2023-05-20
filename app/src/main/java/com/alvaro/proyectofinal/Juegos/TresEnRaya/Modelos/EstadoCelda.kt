package com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos

import androidx.annotation.DrawableRes
import com.alvaro.proyectofinal.R

sealed class EstadoCelda(@DrawableRes val res: Int) {
    object Cruz : EstadoCelda(R.drawable.cruz)
    object Circulo : EstadoCelda(R.drawable.circulo)
    object Vacio : EstadoCelda(R.drawable.vacio)
}
