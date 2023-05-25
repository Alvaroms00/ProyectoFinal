package com.alvaro.proyectofinal.OnBoarding

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")
        emailFocus()
        passFocus()
        nombreFocus()
        binding.btnRegistro.setOnClickListener {
            //Comprobamos si los campos de texto estan vacios
            if (binding.introEmail.text.toString().isNotEmpty() && binding.introPass.text.toString()
                    .isNotEmpty()
            ) {
                //Obtenemos la instancia de nuestra base de datos e introducimos la funcion que nos ofrece firebase para crear un usuario
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.introEmail.text.toString(),
                    binding.introPass.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            mostrarMenu(it.result?.user?.email ?: "", ProviderType.BASIC)

                            val usuario = this.activity?.getSharedPreferences(
                                getString(R.string.usuario),
                                Context.MODE_PRIVATE
                            )
                            usuario?.edit()
                                ?.putString("usuario", binding.introNombre.text.toString())?.apply()
                            usuario?.edit()?.putString("email", binding.introEmail.text.toString())
                                ?.apply()
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
    //Funcion para obtener el foco cuando se esta dentro del campo de nombre
    private fun nombreFocus() {
        binding.introNombre.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILNombre.helperText = nombreValido()
            }
        }
    }
    //Funcion para comprobar el nombre
    private fun nombreValido(): String? {
        val textoNombre = binding.introNombre.text.toString()
        if (textoNombre.length < 1) {
            return getString(R.string.txtErrorNombre)
        }
        return null
    }

    //Funcion para obtener el foco cuando se esta dentro del campo de email
    private fun emailFocus() {
        binding.introEmail.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILEmail.helperText = emailValido()
            }
        }
    }

    //Funcion para validar si el texto introducido es un email comprobando el simbolo arroba (@)
    private fun emailValido(): String? {
        val textoEmail = binding.introEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(textoEmail).matches()) {
            return getString(R.string.EmailIncorrecto)
        }
        return null
    }
    //Funcion para obtener el foco cuando se esta dentro del campo contraseña
    private fun passFocus() {
        binding.introPass.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILPass.helperText = passValida()
            }
        }
    }

    //Funcion para comprobar que se ha introducido una contraseña correcta
    private fun passValida(): String? {
        val textoPass = binding.introPass.text.toString()
        if (textoPass.length < 8) {
            return getString(R.string.MinPass)
        }
        return null
    }

    //Obtenemos una instancia para obtener los valores de los campos y guardarlos dentro de la base de datos
    private fun guardarUsuarios() {
        val nombre = binding.introNombre.text.toString()
        val email = binding.introEmail.text.toString()
        val pass = binding.introPass.text.toString()
        val puntuacionAhorcado = 0
        val puntuacionTresEnRaya = 0

        val usuarios = Usuarios(nombre, email, pass, puntuacionAhorcado, puntuacionTresEnRaya)

        database.child(nombre).setValue(usuarios)
    }

}