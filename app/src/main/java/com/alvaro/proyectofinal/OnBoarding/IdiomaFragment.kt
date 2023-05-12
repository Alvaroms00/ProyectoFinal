package com.alvaro.proyectofinal.OnBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentIdiomaBinding

class IdiomaFragment : Fragment() {
    lateinit var binding: FragmentIdiomaBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIdiomaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnContIdioma.setOnClickListener {
            navController.navigate(R.id.action_idiomaFragment_to_inicioFragment)
        }
    }

}