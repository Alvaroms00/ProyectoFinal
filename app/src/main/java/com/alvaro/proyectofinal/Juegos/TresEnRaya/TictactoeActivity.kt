package com.alvaro.proyectofinal.Juegos.TresEnRaya

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import java.util.Random
import kotlin.math.abs

class TictactoeActivity : AppCompatActivity() {
    private var textoVictoria: TextView? = null
    private lateinit var botones: Array<Int>
    private var tablero = intArrayOf(
        0, 0, 0,
        0, 0, 0,
        0, 0, 0
    )
    private var estado = 0
    private var fichaPuesta = 0
    private var turno = 1
    private var posGanadora = intArrayOf(-1, -1, -1)
    override fun onCreate(savedIntanceState: Bundle?) {
        super.onCreate(savedIntanceState)
        setContentView(R.layout.activity_tictactoe)
        textoVictoria = findViewById(R.id.txtVictoria)

        botones = arrayOf(
            R.id.boton1, R.id.boton2, R.id.boton3,
            R.id.boton4, R.id.boton5, R.id.boton6,
            R.id.boton7, R.id.boton8, R.id.boton9
        )
    }

    fun ponerFicha(view: View) {
        if (estado == 0) {
            turno = 1
            val numBoton = listOf(*botones).indexOf(view.id)
            if (tablero[numBoton] == 0) {
                view.setBackgroundResource(R.drawable.cruz)
                tablero[numBoton] = 1
                fichaPuesta++
                estado = comprobarEstado()
                terminarPartida()
                if (estado == 0) {
                    turno = -1
                    maquina()
                    fichaPuesta++
                    estado = comprobarEstado()
                    terminarPartida()
                }
            }
        }
    }

    private fun terminarPartida() {
        var fichaVictoria = R.drawable.cruzvictoria
        if (estado == 1 || estado == -1) {
            if (estado == 1) {
                textoVictoria!!.visibility = View.VISIBLE
                textoVictoria!!.setTextColor(Color.GREEN)
                textoVictoria!!.text = "Has ganado!!!"
            } else {
                textoVictoria!!.text = "Has perdido"
                textoVictoria!!.setTextColor(Color.RED)
                textoVictoria!!.visibility = View.VISIBLE
                fichaVictoria = R.drawable.circulovictoria
            }
            for (j in posGanadora) {
                val boton = findViewById<Button>(botones[j])
                boton.setBackgroundResource(fichaVictoria)
            }
        } else if (estado == 2) {
            textoVictoria!!.text = "Has empatado"
            textoVictoria!!.visibility = View.VISIBLE
        }
    }

    private fun maquina() {
            val random = Random()
            var posicion = random.nextInt(tablero.size)
            while (tablero[posicion] != 0) {
                posicion = random.nextInt(tablero.size)
            }
            val boton = findViewById<Button>(botones[posicion])
            boton.setBackgroundResource(R.drawable.circulo)
            tablero[posicion] = -1
    }

    private fun comprobarEstado(): Int {
        var nuevoEstado = 0
        if (abs(tablero[0] + tablero[1] + tablero[2]) == 3) {
            posGanadora = intArrayOf(0, 1, 2)
            nuevoEstado = turno
        } else if (abs(tablero[3] + tablero[4] + tablero[5]) == 3) {
            posGanadora = intArrayOf(3, 4, 5)
            nuevoEstado = turno
        } else if (abs(tablero[6] + tablero[7] + tablero[8]) == 3) {
            posGanadora = intArrayOf(6, 7, 8)
            nuevoEstado = turno
        } else if (abs(tablero[0] + tablero[3] + tablero[6]) == 3) {
            posGanadora = intArrayOf(0, 3, 6)
            nuevoEstado = turno
        } else if (abs(tablero[1] + tablero[4] + tablero[7]) == 3) {
            posGanadora = intArrayOf(1, 4, 7)
            nuevoEstado = turno
        } else if (abs(tablero[2] + tablero[5] + tablero[8]) == 3) {
            posGanadora = intArrayOf(2, 5, 8)
            nuevoEstado = turno
        } else if (abs(tablero[0] + tablero[4] + tablero[8]) == 3) {
            posGanadora = intArrayOf(0, 4, 8)
            nuevoEstado = turno
        } else if (abs(tablero[2] + tablero[4] + tablero[6]) == 3) {
            posGanadora = intArrayOf(2, 4, 6)
            nuevoEstado = turno
        } else if (fichaPuesta == 9) {
            nuevoEstado = 2
        }
        return nuevoEstado
    }
}