package com.alvaro.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.databinding.FragmentHangmanPuntuacionBinding


class HangmanPuntuacionFragment : Fragment() {
    private var columnCount = 2
    private lateinit var dataList: ArrayList<Usuarios>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hangman_puntuacion,container,false)
        if (view is RecyclerView){
            with(view){
                layoutManager = when{
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else-> GridLayoutManager(context, columnCount)
                }
                adapter = MyAdapterAhorcado(dataList)
            }
        }
        return view
    }

    companion object{
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HangmanPuntuacionFragment().apply{
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}