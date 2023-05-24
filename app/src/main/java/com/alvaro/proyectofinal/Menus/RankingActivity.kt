package com.alvaro.proyectofinal.Menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityRankingBinding


class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityRankingBinding.inflate(layoutInflater).also { binding = it }.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.puntuacionAhorcado,
            R.id.puntuacionTicTacToe
        ))

        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}