package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.NavController
import com.alvaro.proyectofinal.databinding.FragmentRegistroBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class RegistroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentRegistroBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnContinuar.setOnClickListener{
            var usuario = this.activity?.getSharedPreferences(getString(R.string.usuario), Context.MODE_PRIVATE)
            usuario?.edit()?.putString("usuario",binding.introUser.text.toString())?.apply()

            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
            onDestroy()
        }
    }
}