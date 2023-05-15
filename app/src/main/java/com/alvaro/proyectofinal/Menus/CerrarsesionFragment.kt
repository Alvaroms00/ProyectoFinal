package com.alvaro.proyectofinal.Menus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.LoginActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentCerrarsesionBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.StringBuilder

enum class ProviderType {
    BASIC
}

class CerrarsesionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentCerrarsesionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCerrarsesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            onDestroy()
        }
    }

}