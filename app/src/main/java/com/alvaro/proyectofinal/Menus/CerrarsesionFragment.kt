package com.alvaro.proyectofinal.Menus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaro.proyectofinal.LoginActivity
import com.alvaro.proyectofinal.databinding.FragmentCerrarsesionBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class CerrarsesionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentCerrarsesionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCerrarsesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencias = activity?.getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val nombre = preferencias?.getString("usuario", "")
        val email = preferencias?.getString("email", "")
        binding.txtUsuario.text = "Nombre de Usuario: $nombre"
        binding.txtEmail.text = "Email: $email"

        binding.btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            onDestroy()
        }
    }

}