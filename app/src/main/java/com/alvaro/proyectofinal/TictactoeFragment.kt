package com.alvaro.proyectofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.Juegos.TresEnRaya.TictactoeActivity
import com.alvaro.proyectofinal.databinding.FragmentTictactoeBinding
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TictactoeFragment : Fragment() {
    private lateinit var binding: FragmentTictactoeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentTictactoeBinding.inflate(inflater,container,false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTresEnRaya.setOnClickListener{
            val intent = Intent(activity,TictactoeActivity::class.java)
            startActivity(intent)

            val dateFormat = SimpleDateFormat("d MMM yyyy")
            val fecha = dateFormat.format(Date())

            binding.txtFechaTresEnRaya.text = "Última vez jugado: " + fecha
        }

    }
}