package com.alvaro.proyectofinal.Juegos.TresEnRaya

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Celda
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.EstadoTablero
import com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos.Tablero
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityTictactoeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction

class TictactoeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTictactoeBinding
    private lateinit var database: DatabaseReference
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

    //Funcion para introducir el evento de los botones
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

    //Funcion para actualizar la celda y mostrar la imagen correspondiente
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

    //Funcion para leer el estado del juego y realizar las acciones necesarias en los distintos casos
    private fun estadoJuego(estadoTablero: EstadoTablero) = when (estadoTablero) {
        EstadoTablero.CRUCES_GANAN -> {
            actualizarPuntuacion()
            val builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.victoria)
            builder.setTitle(getString(R.string.victoria))
            builder.setMessage("Has vencido a la maquina")
            builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
                viewModel.reiniciar()
            }
            builder.setNegativeButton(getString(R.string.salir)) { _, _ ->
                finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        EstadoTablero.CIRCULOS_GANAN -> {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.derrota)
            builder.setTitle(getString(R.string.derrota))
            builder.setMessage("Has perdido contra la maquina")
            builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
                viewModel.reiniciar()
            }
            builder.setNegativeButton(getString(R.string.salir)) { _, _ ->
                finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        EstadoTablero.EMPATE -> {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.empate))
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
    //Funcion para guardar la puntuacion que hemos obtenido tras ganar la partida
    private fun actualizarPuntuacion() {
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")
        val preferencias = getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val usuario = preferencias.getString("usuario", "")

        if (usuario != null) {
            val referenciaUsuario = database.child(usuario)

            referenciaUsuario.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val puntuacionAhorcado =
                        currentData.child("puntuacionTresEnRaya").getValue(Long::class.java)
                    val puntajeActual = puntuacionAhorcado?.toInt() ?: 0
                    val puntuacion = 100
                    val nuevaPuntuacion = puntajeActual + puntuacion

                    currentData.child("puntuacionTresEnRaya").value = nuevaPuntuacion

                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    if (error != null) {
                        Log.w("Error", "error")
                    } else {
                        Log.i("Correcto", "Correcto")
                    }
                }
            })
        }
    }

}