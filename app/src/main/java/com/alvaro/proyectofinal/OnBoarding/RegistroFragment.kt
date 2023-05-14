package com.alvaro.proyectofinal.OnBoarding

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.MainActivity
import com.alvaro.proyectofinal.Menus.ProviderType
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroFragment : Fragment() {
    lateinit var binding: FragmentRegistroBinding
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailFocus()
        passFocus()
        nombreFocus()
        database = FirebaseDatabase.getInstance().getReference("Usuarios")
        val nombre = binding.introNombre.text.toString()
        val email = binding.introEmail.text.toString()
        val pass = binding.introPass.text.toString()
        val nombreValido = binding.TILNombre.helperText == null
        val emailValido = binding.TILEmail.helperText == null
        val passValida = binding.TILPass.helperText == null

        binding.btnRegistro.setOnClickListener {
            if (nombreValido && emailValido && passValida) {
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                mostrarMenu(it.result?.user?.email ?: "", ProviderType.BASIC)

                                val usuario = this.activity?.getSharedPreferences(
                                    getString(R.string.usuario),
                                    Context.MODE_PRIVATE
                                )
                                usuario?.edit()?.putString("usuario", nombre)?.apply()
                                usuario?.edit()?.putString("email", email)?.apply()
                                usuario?.edit()?.putString("pass", pass)?.apply()

                                Toast.makeText(
                                    activity,
                                    getString(R.string.ToastRegistro),
                                    Toast.LENGTH_SHORT
                                ).show()
                                onDestroy()
                            } else {
                                alerta()
                            }
                        }
                }
                guardarUsuarios()
            }

        }
    }

    private fun alerta() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Error")
        builder.setMessage(getString(R.string.AlertError))
        builder.setPositiveButton(getString(R.string.btnDialogAceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun mostrarMenu(email: String, provider: ProviderType) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

    private fun nombreFocus() {
        binding.introNombre.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILNombre.helperText = nombreValido()
            }
        }
    }

    private fun nombreValido(): String? {
        val textoNombre = binding.introNombre.text.toString()
        if (textoNombre.length < 1) {
            return "No se ha introducido un nombre"
        }
        return null
    }

    private fun emailFocus() {
        binding.introEmail.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILEmail.helperText = emailValido()
            }
        }
    }

    private fun emailValido(): String? {
        val textoEmail = binding.introEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(textoEmail).matches()) {
            return getString(R.string.EmailIncorrecto)
        }
        return null
    }

    private fun passFocus() {
        binding.introPass.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILPass.helperText = passValida()
            }
        }
    }

    private fun passValida(): String? {
        val textoPass = binding.introPass.text.toString()
        if (textoPass.length < 8) {
            return getString(R.string.MinPass)
        }
        return null
    }

    private fun guardarUsuarios() {
        val nombre = binding.introNombre.text.toString()
        val email = binding.introEmail.text.toString()
        val pass = binding.introPass.text.toString()
        val idUsuario = database.push().key!!


        val usuarios = Usuarios(idUsuario, nombre, email, pass)

        database.child(idUsuario).setValue(usuarios)
    }

}