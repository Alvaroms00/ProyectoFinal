package com.alvaro.proyectofinal.OnBoarding

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaro.proyectofinal.MainActivity
import com.alvaro.proyectofinal.ProviderType
import com.alvaro.proyectofinal.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class RegistroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentRegistroBinding

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

        binding.btnRegistro.setOnClickListener{
            if (binding.introEmail.text.isNotEmpty() && binding.introPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.introEmail.text.toString(),binding.introPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        mostrarMenu(it.result?.user?.email ?:"", ProviderType.BASIC)
                    }else{
                        alerta()
                    }
                }
            }
        }
    }
    private fun alerta(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al registrarse")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun mostrarMenu(email: String, provider: ProviderType){
        val intent = Intent(activity, MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

}