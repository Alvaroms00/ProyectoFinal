package com.alvaro.proyectofinal.Juegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alvaro.proyectofinal.R

class TictactoeActivity : AppCompatActivity() {

    private lateinit var textoVictoria: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)

        textoVictoria = findViewById(R.id.txtTresEnRaya)

    }
}