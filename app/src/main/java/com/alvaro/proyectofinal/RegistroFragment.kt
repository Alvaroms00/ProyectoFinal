package com.alvaro.proyectofinal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import com.alvaro.proyectofinal.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth

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

        binding.btnRegistrar.setOnClickListener{
            var usuario = this.activity?.getSharedPreferences(getString(R.string.usuario), Context.MODE_PRIVATE)
            usuario?.edit()?.putString("usuario",binding.introUser.text.toString())?.apply()

            if (binding.introEmail.text.isNotEmpty() && binding.introPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.introEmail.text.toString(),binding.introPass.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(activity, "Registro Completo", Toast.LENGTH_SHORT).show()
                    }else{
                        showAlert()
                    }
                }
                val intent = Intent(activity,MainActivity::class.java)
                startActivity(intent)
                onDestroy()
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al registrarse")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}