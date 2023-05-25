package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaro.proyectofinal.Juegos.Hangman.HangmanActivity
import com.alvaro.proyectofinal.databinding.FragmentHangmanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val fecha = preferencias?.getString("fechaAhorcado", "")
        val nombre = preferencias?.getString("usuario", "")

        var puntuacion: String
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")
        //Comprobamos si la base de datos contiene algun dato y lo mostramos en pantalla
        if (nombre != null) {
            database.child(nombre).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        puntuacion = snapshot.child("puntuacionAhorcado").value.toString()
                        binding.txtPuntuacionJuego.text =
                            "${getString(R.string.txtPuntuacionTotal)} $puntuacion ${getString(R.string.puntos)}"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        //Accion del boton para entrar en nuestro juego
        binding.btnAhorcado.setOnClickListener {
            val intent = Intent(activity, HangmanActivity::class.java)
            startActivity(intent)

            val dateFormat = SimpleDateFormat("d/MM/yyyy H:mm")
            val fecha = dateFormat.format(Date())

            val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
            preferencias?.edit()?.putString("fechaAhorcado", fecha)?.apply()
            binding.txtFecha.text = getString(R.string.txtFecha) + fecha
        }
        binding.txtFecha.text = "${getString(R.string.txtFecha)} $fecha"

    }

}