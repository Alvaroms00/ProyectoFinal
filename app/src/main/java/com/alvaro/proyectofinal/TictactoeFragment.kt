package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaro.proyectofinal.Juegos.TresEnRaya.TictactoeActivity
import com.alvaro.proyectofinal.databinding.FragmentTictactoeBinding
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TictactoeFragment : Fragment() {
    private lateinit var binding: FragmentTictactoeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentTictactoeBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateFormat = SimpleDateFormat("d/MM/yyyy H:m")
        val fecha = dateFormat.format(Date())

        binding.btnTresEnRaya.setOnClickListener {
            val intent = Intent(activity, TictactoeActivity::class.java)
            startActivity(intent)

            val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
            preferencias?.edit()?.putString("fecha tresEnRaya", fecha)
            binding.txtFechaTresEnRaya.text = "Última vez jugado: " + fecha
        }

    }
}