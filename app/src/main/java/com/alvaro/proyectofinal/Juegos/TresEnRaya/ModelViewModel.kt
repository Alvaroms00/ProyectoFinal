package com.alvaro.proyectofinal.Juegos.TresEnRaya

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Celda
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.EstadoCelda
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.EstadoTablero
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Tablero

class ModelViewModel : ViewModel() {
    private var tablero = Tablero()
    val vistaTablero = MutableLiveData(tablero)

    private fun actualizarTablero() {
        vistaTablero.value = tablero
    }

    fun celdaClicada(celda: Celda) {
        tablero.estadoCelda(celda, EstadoCelda.Cruz)
        actualizarTablero()
        if (tablero.estadoTablero == EstadoTablero.INCOMPLETO) {
            turnoMaquina()
        }
    }

    fun reiniciar() {
        tablero.reiniciarTablero()
        actualizarTablero()
    }


    private fun turnoMaquina() {
        val maquinaPuedeGanar = tablero.buscarMovimientoGanador(EstadoCelda.Circulo)
        val jugadorPuedeGanar = tablero.buscarMovimientoGanador(EstadoCelda.Cruz)

        when {
            maquinaPuedeGanar != null -> tablero.estadoCelda(maquinaPuedeGanar, EstadoCelda.Circulo)
            jugadorPuedeGanar != null -> tablero.estadoCelda(jugadorPuedeGanar, EstadoCelda.Circulo)
            tablero.estadoCelda(Celda.CENTER_CENTER, EstadoCelda.Circulo) -> Unit
            else -> do {
                val celda = Celda.values().random()
                val posicionCorrecta = tablero.estadoCelda(celda, EstadoCelda.Circulo)
            } while (!posicionCorrecta)
        }
        actualizarTablero()
    }
}