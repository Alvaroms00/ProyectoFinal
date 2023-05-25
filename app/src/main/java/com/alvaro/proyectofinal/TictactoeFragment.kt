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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TictactoeFragment : Fragment() {
    private lateinit var binding: FragmentTictactoeBinding
    private lateinit var database: DatabaseReference
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

        val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val fecha = preferencias?.getString("fechaTresEnRaya", "")
        val nombre = preferencias?.getString("usuario", "")

        var puntuacion: String
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")
//Comprobamos si la base de datos contiene algun dato y lo mostramos en pantalla
        if (nombre != null) {
            database.child(nombre).get().addOnSuccessListener {
                if (it.exists()) {
                    puntuacion = it.child("puntuacionTresEnRaya").value.toString()
                    binding.txtPuntuacionTresEnRaya.text =
                        "${getString(R.string.txtPuntuacionTotal)} $puntuacion ${getString(R.string.puntos)}"
                }
            }
        }
        //Accion del boton para entrar en nuestro juego
        binding.btnTresEnRaya.setOnClickListener {
            val intent = Intent(activity, TictactoeActivity::class.java)
            startActivity(intent)

            val dateFormat = SimpleDateFormat("d/MM/yyyy H:mm")
            val fecha = dateFormat.format(Date()).toString()

            val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)

            preferencias?.edit()?.putString("fechaTresEnRaya", fecha)?.apply()
            binding.txtFechaTresEnRaya.text = getString(R.string.txtFecha) + fecha

        }
        binding.txtFechaTresEnRaya.text = getString(R.string.txtFecha) + fecha
    }
}