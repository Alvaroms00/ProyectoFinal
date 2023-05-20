package com.alvaro.proyectofinal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.Menus.ProviderType
import com.alvaro.proyectofinal.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityLoginBinding.inflate(layoutInflater).also { binding = it }.root)
        emailFocus()
        passFocus()

        binding.btnLogin.setOnClickListener {
            if (binding.introLoginEmail.text.toString().isNotEmpty() && binding.introLoginPass.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.introLoginEmail.text.toString(), binding.introLoginPass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            mostrarMenu(it.result?.user?.email ?: "", ProviderType.BASIC)
                            Toast.makeText(this,  getString(R.string.ToastInicioSesion),
                                Toast.LENGTH_SHORT).show()
                            finish()
                        } else{
                            alerta()
                        }
                    }
            }
        }

    }
    private fun alerta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(getString(R.string.AlertError))
        builder.setPositiveButton(getString(R.string.btnDialogAceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun mostrarMenu(email: String, provider: ProviderType) {
        val intent = Intent(this, SeleccionActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }
    private fun emailFocus() {
        binding.introLoginEmail.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILEmailLogin.helperText = emailValido()
            }
        }
    }

    private fun emailValido(): String? {
        val textoEmail = binding.introLoginEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(textoEmail).matches()) {
            return getString(R.string.EmailIncorrecto)
        }
        return null
    }
    private fun passFocus() {
        binding.introLoginPass.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                binding.TILPassLogin.helperText = passValida()
            }
        }
    }

    private fun passValida(): String? {
        val textoPass = binding.introLoginPass.text.toString()
        if (textoPass.length < 8) {
            return getString(R.string.MinPass)
        }
        return null
    }
}