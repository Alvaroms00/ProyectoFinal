package com.alvaro.proyectofinal.OnBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentPuntuacionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PuntuacionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentPuntuacionBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPuntuacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnContinuarPunt.setOnClickListener {
            navController.navigate(R.id.action_puntuacionFragment_to_registroFragment)
            onDestroy()
        }
    }


}