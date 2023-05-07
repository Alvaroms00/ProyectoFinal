package com.alvaro.proyectofinal.Menus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.Juegos.Hangman.HangmanActivity
import com.alvaro.proyectofinal.Juegos.TictactoeActivity
import com.alvaro.proyectofinal.databinding.FragmentJuegosBinding

class JuegosFragment : Fragment() {

    lateinit var binding: FragmentJuegosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuegosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAhorcado.setOnClickListener{
            val intent = Intent(activity,HangmanActivity::class.java)
            startActivity(intent)
        }

        binding.btnTicTacToe.setOnClickListener {
            val intent = Intent(activity,TictactoeActivity::class.java)
            startActivity(intent)
        }
    }

}