package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alvaro.proyectofinal.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            screenSplash.setKeepOnScreenCondition { false }
        }

        val usuario = getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val usuarioGuardado = usuario.getString("usuario", "")

        if (usuarioGuardado != "") {
            val intent = Intent(this, SeleccionActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }
    }
}