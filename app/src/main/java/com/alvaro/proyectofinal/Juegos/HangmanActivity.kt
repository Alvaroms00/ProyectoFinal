package com.alvaro.proyectofinal.Juegos

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityHangmanBinding
import java.util.Random

public class HangmanActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHangmanBinding

    private var palabras: Array<String>? = null
    private var random: Random? = null
    private var palabraAnterior: String? = null
    private var letrasView: Array<TextView>? = null
    private var palabraLayout: LinearLayout? = null
    private var adapter: LetrasAdapter? = null
    private var gridView: GridView? = null
    private var numCorrecto = 0
    private var numCaracter = 0
    private var partes: Array<ImageView>? = null
    private val partesSize = 6
    private var parteAnterior = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityHangmanBinding.inflate(layoutInflater).also { binding = it }.root)

        palabras = resources.getStringArray(R.array.palabras)
        palabraLayout = findViewById(R.id.palabra)
        gridView = findViewById(R.id.letras)
        random = Random()

        partes = arrayOfNulls(partesSize)
        partes[0] = findViewById(R.id.imgCabeza)
        partes[1] = findViewById(R.id.imgCuerpo)

        juego()
    }

    private fun juego(){
        var nuevaPalabra = palabras?.get(random?.nextInt(palabras?.size ?:0) ?:0)
        while (nuevaPalabra == palabraAnterior) {
            nuevaPalabra = palabras?.get(random?.nextInt(palabras?.size ?: 0) ?: 0)
        }

        palabraAnterior = nuevaPalabra

        letrasView = arrayOfNulls(palabraAnterior?.length ?: 0)

        palabraLayout?.removeAllViews()

        for (i in 0 until (palabraAnterior?.length ?: 0)) {
            letrasView?.set(i, TextView(this))
            letrasView?.get(i)?.text = "" + palabraAnterior?.get(i)
            letrasView?.get(i)?.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            letrasView?.get(i)?.gravity = Gravity.CENTER
            letrasView?.get(i)?.setTextColor(Color.WHITE)
            letrasView?.get(i)?.setBackgroundResource(R.drawable.letras)
            palabraLayout?.addView(letrasView?.get(i))
        }

        adapter = LetrasAdapter(this)
        gridView?.adapter = adapter
        numCorrecto = 0
        parteAnterior = 0
        numCaracter = palabraAnterior?.length ?: 0

        for (i in 0 until partesSize) {
            partes?.get(i)?.visibility = View.INVISIBLE
        }
    }

    fun letraPresionada(view: View){
        val letra = (view as TextView).text.toString()
        val letraChar = letra[0]

        view.isEnabled = false

        var correcto = false

        for (i in 0 until (palabraAnterior?.length ?: 0)) {
            if ((palabraAnterior?.get(i) ?: letraChar) as Boolean) {
                correcto = true
                numCorrecto++
                letrasView?.get(i)?.setTextColor(Color.BLACK)
            }
        }

        if (correcto) {
            if (numCorrecto == numCaracter) {
                deshabilitarBoton()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Has Ganado")
                builder.setMessage("Has acertado la palabra\nLa respuesta era:\n" + palabraAnterior)
                builder.setPositiveButton("Jugar de nuevo") { dialog, which -> juego() }
                builder.setNegativeButton("Salir") { dialog, which -> finish() }
                builder.show()
            }
        } else if (parteAnterior < partesSize) {
            partes?.get(parteAnterior)?.visibility ?: View.VISIBLE
            parteAnterior++
        } else {
            deshabilitarBoton()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Has Perdido")
            builder.setMessage("No has acertado la palabra\n\nLa palabra era:\n" + palabraAnterior)
            builder.setPositiveButton("Jugar de nuevo") { dialog, which -> juego() }
            builder.setNegativeButton("Salir") { dialog, which -> finish() }
            builder.show()
        }
    }

    fun deshabilitarBoton(){
        for (i in 0 until (gridView?.childCount ?: 0)) {
            gridView?.getChildAt(i)?.isEnabled ?: false
        }
    }

}

