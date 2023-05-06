package com.alvaro.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.alvaro.proyectofinal.databinding.ActivitySeleccionBinding


class SeleccionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeleccionBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySeleccionBinding.inflate(layoutInflater).also { binding = it }.root)

        setTitle("Juegos Clasicos")
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.juegosFragment,
            R.id.loginFragment
            ),
            binding.drawerLayout
        )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar,navController,appBarConfiguration)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.drawer_graph)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}