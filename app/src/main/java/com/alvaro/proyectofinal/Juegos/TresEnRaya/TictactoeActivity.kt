package com.alvaro.proyectofinal.Juegos.TresEnRaya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityTictactoeBinding

class TictactoeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTictactoeBinding
    override fun onCreate(savedIntanceState: Bundle?) {
        super.onCreate(savedIntanceState)
        binding = ActivityTictactoeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actualizarCeldas()
    }

    fun actualizarCeldas(){
        binding.celda1.setImageResource(R.drawable.cruz)
        binding.celda2.setImageResource(R.drawable.cruz)
        binding.celda3.setImageResource(R.drawable.cruz)
        binding.celda4.setImageResource(R.drawable.circulo)
        binding.celda5.setImageResource(R.drawable.circulo)
        binding.celda6.setImageResource(R.drawable.circulo)
        binding.celda7.setImageResource(R.drawable.cruz)
        binding.celda8.setImageResource(R.drawable.cruz)
        binding.celda9.setImageResource(R.drawable.cruz)

    }


}