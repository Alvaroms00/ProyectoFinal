package com.alvaro.proyectofinal


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.alvaro.proyectofinal.databinding.ActivitySeleccionBinding
import com.google.firebase.auth.FirebaseAuth


class SeleccionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeleccionBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySeleccionBinding.inflate(layoutInflater).also { binding = it }.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.juegosFragment,
                R.id.rankingActivity,
                R.id.cerrarsesionFragment,
                R.id.ajustesFragment
            ),
            binding.drawerLayout
        )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        val inicio = FirebaseAuth.getInstance().currentUser

        if (inicio == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}