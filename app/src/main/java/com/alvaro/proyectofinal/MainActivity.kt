package com.alvaro.proyectofinal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alvaro.proyectofinal.BaseDeDatos.Database
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

        CoroutineScope(Dispatchers.IO).launch{
            delay(1000)
            screenSplash.setKeepOnScreenCondition{ false }
        }

        val usuario = getSharedPreferences(getString(R.string.usuario), Context.MODE_PRIVATE)
        var usuarioGuardado = usuario.getString("usuario", "")

        if (usuarioGuardado != ""){
            val connection = Database.getConnection()
            val statement = connection?.prepareStatement("INSERT INTO usuarios(nombre, usuario, pass) VALUES (?,?,?)")
            statement?.setString(1,R.id.introNombre.toString())
            statement?.setString(2,R.id.introUser.toString())
            statement?.setString(3,R.id.introPass.toString())
            statement?.executeQuery()
            connection?.close()

            val intent = Intent(this,SeleccionActivity::class.java)
            startActivity(intent)
        }else{
            setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }
    }


}