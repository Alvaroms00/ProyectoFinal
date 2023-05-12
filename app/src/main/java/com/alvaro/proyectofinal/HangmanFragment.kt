package com.alvaro.proyectofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.Juegos.Hangman.HangmanActivity
import com.alvaro.proyectofinal.databinding.FragmentHangmanBinding

class HangmanFragment : Fragment() {

    private lateinit var binding: FragmentHangmanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentHangmanBinding.inflate(inflater,container,false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAhorcado.setOnClickListener {
            val intent = Intent(activity, HangmanActivity::class.java)
            startActivity(intent)
        }
    }
}