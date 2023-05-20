package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaro.proyectofinal.BaseDeDatos.Puntuacion
import com.alvaro.proyectofinal.Juegos.Hangman.HangmanActivity
import com.alvaro.proyectofinal.databinding.FragmentHangmanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class HangmanFragment : Fragment() {

    private lateinit var binding: FragmentHangmanBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentHangmanBinding.inflate(inflater, container, false).also { binding = it }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("PuntuacionAhorcado")
        val preferencias = activity?.getSharedPreferences("usuario",Context.MODE_PRIVATE)
        val fecha = preferencias?.getString("fechaAhorcado", "")


        binding.btnAhorcado.setOnClickListener {
            val intent = Intent(activity, HangmanActivity::class.java)
            startActivity(intent)

            val dateFormat = SimpleDateFormat("d/MM/yyyy H:mm")
            val fecha = dateFormat.format(Date())

            val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
            preferencias?.edit()?.putString("fechaAhorcado", fecha)?.apply()
            binding.txtFecha.text = "Última vez jugado: " + fecha
            guardarPuntuacion()
        }
        binding.txtFecha.text = "Última vez jugado: " + fecha

    }
    private fun guardarPuntuacion(){
        val preferencias = activity?.getSharedPreferences("usuario",Context.MODE_PRIVATE)
        val usuario = preferencias?.getString("usuario", "")
        val fechaJuego = preferencias?.getString("fechaAhorcado", "")

        val puntuacion = 0
        val idPuntuacion = database.push().key!!

        val guardarPuntuacion = Puntuacion(usuario, fechaJuego, puntuacion)

        database.child(idPuntuacion).setValue(guardarPuntuacion)
    }
}