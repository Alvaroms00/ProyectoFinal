package com.alvaro.proyectofinal.Menus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alvaro.proyectofinal.HangmanFragment
import com.alvaro.proyectofinal.TictactoeFragment
import com.alvaro.proyectofinal.databinding.FragmentJuegosBinding

class JuegosFragment : Fragment() {

    lateinit var binding: FragmentJuegosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentJuegosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PagerAdapter(this)

    }

    class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HangmanFragment()
                1 -> TictactoeFragment()
                else -> HangmanFragment()
            }
        }
    }

}