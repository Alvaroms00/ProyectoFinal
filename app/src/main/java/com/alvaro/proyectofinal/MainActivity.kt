package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alvaro.proyectofinal.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
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

        //Corrutina para la SplashScreen
        CoroutineScope(Dispatchers.IO).launch{
            delay(1000)
            screenSplash.setKeepOnScreenCondition{ false }
        }

        //Evento para las analiticas de Google
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mesage","Integración a FireBase completada")
        analytics.logEvent("InitScreen", bundle )

        //Creación del guardado de usuario e inicio de la aplicación
        val usuario = getSharedPreferences(getString(R.string.usuario), Context.MODE_PRIVATE)
        var usuarioGuardado = usuario.getString("usuario", "")

        if (usuarioGuardado != ""){
            val intent = Intent(this,SeleccionActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }
    }


}