package com.alvaro.proyectofinal.Menus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.FragmentRankingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RankingFragment : Fragment() {
    private lateinit var binding: FragmentRankingBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.puntuacionAhorcado,
            R.id.puntuacionTicTacToe
        ))

        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)*/
    }
}