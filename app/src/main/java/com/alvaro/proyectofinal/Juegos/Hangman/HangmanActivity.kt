package com.alvaro.proyectofinal.Juegos.Hangman

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import java.util.Random

class HangmanActivity : AppCompatActivity() {
    private lateinit var palabras: Array<String>
    private var random: Random? = null
    private var palabraAnterior: String? = null
    private lateinit var letrasView: Array<TextView?>
    private var palabraLayout: LinearLayout? = null
    private var adapter: LetrasAdapter? = null
    private var gridView: GridView? = null
    private var numCorrecto = 0
    private var numCaracter = 0
    private lateinit var partes: Array<ImageView?>
    private val partesSize = 6
    private var parteAnterior = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman)
        palabras = resources.getStringArray(R.array.palabras)
        palabraLayout = findViewById(R.id.palabra)
        gridView = findViewById(R.id.letras)
        random = Random()
        partes = arrayOfNulls(partesSize)
        partes[0] = findViewById(R.id.imgCabeza)
        partes[1] = findViewById(R.id.imgCuerpo)
        partes[2] = findViewById(R.id.imgBrazoDerecho)
        partes[3] = findViewById(R.id.imgBrazoIzquierdo)
        partes[4] = findViewById(R.id.imgPiernaDerecha)
        partes[5] = findViewById(R.id.imgPiernaIzquierda)
        juego()
    }

    private fun juego() {
        var nuevaPalabra = palabras[random!!.nextInt(palabras.size)]
        while (nuevaPalabra == palabraAnterior) nuevaPalabra =
            palabras[random!!.nextInt(palabras.size)]
        palabraAnterior = nuevaPalabra
        letrasView = arrayOfNulls(palabraAnterior!!.length)
        palabraLayout!!.removeAllViews()
        for (i in 0 until palabraAnterior!!.length) {
            letrasView[i] = TextView(this)
            letrasView[i]!!.text = "" + palabraAnterior!![i]
            letrasView[i]!!.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            letrasView[i]!!.gravity = Gravity.CENTER
            letrasView[i]!!.setTextColor(Color.WHITE)
            letrasView[i]!!.setBackgroundResource(R.drawable.letras)
            palabraLayout!!.addView(letrasView[i])
        }
        adapter = LetrasAdapter(this)
        gridView!!.adapter = adapter
        numCorrecto = 0
        parteAnterior = 0
        numCaracter = palabraAnterior!!.length
        for (i in 0 until partesSize) {
            partes[i]!!.visibility = View.INVISIBLE
        }
    }

    fun letraPresionada(view: View) {
        val letra = (view as TextView).text.toString()
        val letraChar = letra[0]
        view.setEnabled(false)
        var correcto = false
        for (i in 0 until palabraAnterior!!.length) {
            if (palabraAnterior!![i] == letraChar) {
                correcto = true
                numCorrecto++
                letrasView[i]!!.setTextColor(Color.BLACK)
            }
        }
        if (correcto) {
            if (numCorrecto == numCaracter) {
                deshabilitarBoton()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Has Ganado")
                builder.setMessage("Has acertado la palabra\nLa respuesta era:\n$palabraAnterior")
                builder.setPositiveButton("Jugar de nuevo") { dialog: DialogInterface?, which: Int -> juego() }
                builder.setNegativeButton("Salir") { dialog: DialogInterface?, which: Int -> finish() }
                builder.show()
            }
        } else if (parteAnterior < partesSize) {
            partes[parteAnterior]!!.visibility = View.VISIBLE
            parteAnterior++
        } else {
            deshabilitarBoton()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Has Perdido")
            builder.setMessage("No has acertado la palabra\n\nLa palabra era:\n$palabraAnterior")
            builder.setPositiveButton("Jugar de nuevo") { dialogInterface: DialogInterface?, i: Int -> juego() }
            builder.setNegativeButton("Salir") { dialogInterface: DialogInterface?, i: Int -> finish() }
            builder.show()
        }
    }

    fun deshabilitarBoton() {
        for (i in 0 until gridView!!.childCount) {
            gridView!!.getChildAt(i).isEnabled = false
        }
    }
}