package com.alvaro.proyectofinal.Juegos.Hangman

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityHangmanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction

class HangmanActivity : AppCompatActivity(), View.OnClickListener {
    //Declaramos las variables necesarias para nuestra actividad
    private lateinit var binding: ActivityHangmanBinding
    private lateinit var database: DatabaseReference

    private lateinit var palabraSecreta: String
    private lateinit var palabrasArray: Array<String>

    private var letrasAdivinadas = ArrayList<Char>()
    private var intentosRestantes = 0
    private val letrasPresionadas = mutableListOf<String>()


    private lateinit var txtPalabra: TextView
    private lateinit var imgAhorcado: ImageView
    private lateinit var editTextRespuesta: EditText
    private lateinit var botonComprobar: Button
    private lateinit var gridLetras: GridView
    private lateinit var txtLetrasPresionadas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHangmanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos las ids de cada elemento
        txtPalabra = binding.txtAdivinar
        imgAhorcado = binding.imgJuegoAhorcado
        editTextRespuesta = binding.editTextRespuesta
        botonComprobar = binding.botonComprobar
        gridLetras = binding.gridLetras
        txtLetrasPresionadas = binding.letrasPresionadas

        botonComprobar.setOnClickListener(this)

        palabrasArray = resources.getStringArray(R.array.palabras)
        palabraSecreta = palabrasArray.random()

        //Creamos el adaptador para las letras y las añadimos
        val letrasAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, obtenerLetrasAbecedario())
        binding.gridLetras.adapter = letrasAdapter
        binding.gridLetras.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val letra = letrasAdapter.getItem(position)
                letra?.let { comprobarLetra(it) }
            }

        actualizarPalabra()
    }

    //Funcion para el evento de cuando se clicka una letra
    override fun onClick(v: View?) {
        if (v?.id == R.id.botonComprobar) {
            val respuesta = binding.editTextRespuesta.text.toString().uppercase()
            comprobarRespuesta(respuesta)
        }
    }
    //Funcion para obtener las letras del abecedario
    private fun obtenerLetrasAbecedario(): List<String> {
        return listOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )
    }

    //Funcion donde comprobamos si las letras clicadas se encuentran el la palabra a adivinar
    private fun comprobarLetra(letra: String) {
        val letraChar = letra.uppercase()[0]
        if (!letrasAdivinadas.contains(letraChar)) {
            letrasAdivinadas.add(letraChar)
            if (!palabraSecreta.contains(letraChar)) {
                intentosRestantes++
                letrasPresionadas.add(letra)
                actualizarLetrasPresionadas()
                actualizarImagenAhorcado()
            }
            actualizarPalabra()
            if (intentosRestantes == 6) {
                desactivarBotones()
                dialogoDerrota()
            } else if (palabraAdivinada()) {
                desactivarBotones()
                dialogoVictoria()
            }
        }
    }

    //Funcion para comprobar la respuesta de la palabra completa y si coincide con la palabra a adivinar
    private fun comprobarRespuesta(respuesta: String) {
        if (respuesta == palabraSecreta) {
            letrasAdivinadas.clear()
            letrasAdivinadas.addAll(palabraSecreta.toCharArray().toList())
            actualizarPalabra()

            desactivarBotones()
            dialogoVictoria()
        } else {
            dialogoDerrota()
            desactivarBotones()
        }
    }

    //Funcion para determinar si hemos adivinado la palabra
    private fun palabraAdivinada(): Boolean {
        for (letra in palabraSecreta) {
            if (!letrasAdivinadas.contains(letra)) {
                return false
            }
        }
        return true
    }
    //Funcion para mostrar guiones cuando se inicia el juego para ocultar la palabra
    private fun actualizarPalabra() {
        val palabraMostrada = StringBuilder()
        for (letra in palabraSecreta) {
            if (letrasAdivinadas.contains(letra)) {
                palabraMostrada.append(letra)
            } else {
                palabraMostrada.append("_")
            }
            palabraMostrada.append(" ")
        }
        txtPalabra.text = palabraMostrada.toString()
    }
    //Funcion para actualizar la imagen cada vez que hacemos un fallo
    private fun actualizarImagenAhorcado() {
        val imagenId =
            resources.getIdentifier("ahorcado_$intentosRestantes", "drawable", packageName)
        binding.imgJuegoAhorcado.setImageResource(imagenId)
    }
    //Funcion para desactivar los botones en caso de finalizar la partida
    private fun desactivarBotones() {
        binding.gridLetras.isEnabled = false
        binding.botonComprobar.isEnabled = false
    }
    //Funcion para obtener una palabra aleatoria dentro de nuestro array de palabras
    private fun obtenerPalabraAleatoria(): String {
        return palabrasArray.random()
    }

    //Funcion para reiniciar el juego
    private fun reiniciarJuego() {
        letrasAdivinadas.clear()
        intentosRestantes = 0
        palabraSecreta = obtenerPalabraAleatoria()
        txtLetrasPresionadas.text = "${getString(R.string.txtLetraFallada)} "
        letrasPresionadas.clear()
        binding.editTextRespuesta.setText("")

        actualizarPalabra()
        actualizarImagenAhorcado()

        binding.gridLetras.isEnabled = true
        binding.botonComprobar.isEnabled = true
    }

    //Funcion para mostrar las letras falladas
    private fun actualizarLetrasPresionadas() {
        val letrasPresionadasText = letrasPresionadas.joinToString(", ")
        txtLetrasPresionadas.text = "${getString(R.string.txtLetraFallada)}$letrasPresionadasText"
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
                        currentData.child("puntuacionAhorcado").getValue(Long::class.java)
                    val puntajeActual = puntuacionAhorcado?.toInt() ?: 0
                    val puntuacion = 100
                    val nuevaPuntuacion = puntajeActual + puntuacion

                    currentData.child("puntuacionAhorcado").value = nuevaPuntuacion

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

    //Funcion para mostrar un dialogo cuando ganamos la partida
    private fun dialogoVictoria() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.victoria)
        builder.setTitle(getString(R.string.victoria))
        builder.setMessage("¡Felicidades!\nHas adivinado la palabra.\nHas ganado 100 puntos.\n¿Deseas jugar de nuevo?")
        builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
            reiniciarJuego()
            actualizarPuntuacion()
        }
        builder.setNegativeButton(getString(R.string.salir)) { _, _ ->
            finish()
            actualizarPuntuacion()
        }
        builder.show()
    }

    //Funcion para mostrar un dialogo cuando perdemos la partida
    private fun dialogoDerrota() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.derrota)
        builder.setTitle(getString(R.string.derrota))
        builder.setMessage("Has perdido. La palabra era: $palabraSecreta.\n¿Deseas jugar de nuevo?")
        builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
            reiniciarJuego()
        }
        builder.setNegativeButton(R.string.salir) { _, _ ->
            finish()
        }
        builder.show()
    }
}