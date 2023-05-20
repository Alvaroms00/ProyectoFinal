package com.alvaro.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }
}