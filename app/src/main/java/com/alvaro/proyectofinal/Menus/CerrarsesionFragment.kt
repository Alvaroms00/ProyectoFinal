package com.alvaro.proyectofinal.Menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentCerrarsesionBinding


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
        binding = FragmentCerrarsesionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.txtEmail.text = email
        binding.txtProveedor.text = */
    }

}