package com.alvaro.proyectofinal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alvaro.proyectofinal.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnInicioSesion.setOnClickListener{
            if (binding.introUserInicio.text.isNotEmpty() && binding.introPassInicio.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.introUserInicio.text.toString(),binding.introPassInicio.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                        Toast.makeText(activity, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    }else{
                        showAlert()
                    }
                }
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

    private fun showHome(email: String, Provider: ProviderType){
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider", Provider.name )
        }
        startActivity(intent)
    }
}