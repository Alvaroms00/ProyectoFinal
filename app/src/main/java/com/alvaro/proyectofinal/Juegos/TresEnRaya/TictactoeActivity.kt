package com.alvaro.proyectofinal.Juegos.TresEnRaya

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Celda
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.EstadoTablero
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Tablero
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityTictactoeBinding

class TictactoeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTictactoeBinding
    private val viewModel: ModelViewModel by viewModels()
    override fun onCreate(savedIntanceState: Bundle?) {
        super.onCreate(savedIntanceState)
        binding = ActivityTictactoeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.vistaTablero.observe(this, cambiosTablero)
        botones()
    }

    private val cambiosTablero = Observer { tablero: Tablero ->
        actualizarCeldas(tablero)
        estadoJuego(tablero.estadoTablero)

    }

    private fun botones() = with(binding) {
        celda1.setOnClickListener { viewModel.celdaClicada(Celda.TOP_LEFT) }
        celda2.setOnClickListener { viewModel.celdaClicada(Celda.TOP_CENTER) }
        celda3.setOnClickListener { viewModel.celdaClicada(Celda.TOP_RIGHT) }
        celda4.setOnClickListener { viewModel.celdaClicada(Celda.CENTER_LEFT) }
        celda5.setOnClickListener { viewModel.celdaClicada(Celda.CENTER_CENTER) }
        celda6.setOnClickListener { viewModel.celdaClicada(Celda.CENTER_RIGHT) }
        celda7.setOnClickListener { viewModel.celdaClicada(Celda.BOT_LEFT) }
        celda8.setOnClickListener { viewModel.celdaClicada(Celda.BOT_CENTER) }
        celda9.setOnClickListener { viewModel.celdaClicada(Celda.BOT_RIGHT) }
    }

    private fun actualizarCeldas(tablero: Tablero) {
        binding.celda1.setImageResource(tablero.estado(Celda.TOP_LEFT).res)
        binding.celda2.setImageResource(tablero.estado(Celda.TOP_CENTER).res)
        binding.celda3.setImageResource(tablero.estado(Celda.TOP_RIGHT).res)
        binding.celda4.setImageResource(tablero.estado(Celda.CENTER_LEFT).res)
        binding.celda5.setImageResource(tablero.estado(Celda.CENTER_CENTER).res)
        binding.celda6.setImageResource(tablero.estado(Celda.CENTER_RIGHT).res)
        binding.celda7.setImageResource(tablero.estado(Celda.BOT_LEFT).res)
        binding.celda8.setImageResource(tablero.estado(Celda.BOT_CENTER).res)
        binding.celda9.setImageResource(tablero.estado(Celda.BOT_RIGHT).res)

    }

    private fun estadoJuego(estadoTablero: EstadoTablero) = when (estadoTablero) {
        EstadoTablero.CRUCES_GANAN -> {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.victoria)
            builder.setTitle("¡Victoria!")
            builder.setMessage("Has vencido a la maquina")
            builder.setPositiveButton("Jugar de Nuevo") { _, _ ->
                viewModel.reiniciar()
            }
            builder.setNegativeButton("Salir") { _, _ ->
                finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        EstadoTablero.CIRCULOS_GANAN -> {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.derrota)
            builder.setTitle("Derrota")
            builder.setMessage("Has perdido contra la maquina")
            builder.setPositiveButton("Jugar de Nuevo") { _, _ ->
                viewModel.reiniciar()
            }
            builder.setNegativeButton("Salir") { _, _ ->
                finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        EstadoTablero.EMPATE -> {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Empate")
            builder.setMessage("Has empatado contra la maquina")
            builder.setPositiveButton("Jugar de Nuevo") { _, _ ->
                viewModel.reiniciar()
            }
            builder.setNegativeButton("Salir") { _, _ ->
                finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        EstadoTablero.INCOMPLETO -> {}
    }
}