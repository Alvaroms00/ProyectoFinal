package com.alvaro.proyectofinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alvaro.proyectofinal.Adapters.MyAdapterAhorcado
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.databinding.FragmentHangmanPuntuacionBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HangmanPuntuacionFragment : Fragment() {
    private lateinit var binding: FragmentHangmanPuntuacionBinding
    private lateinit var dataList: ArrayList<Usuarios>
    private lateinit var adapter: MyAdapterAhorcado
    var database: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHangmanPuntuacionBinding.inflate(inflater, container, false)
            .also { binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Instanciamos el adaptador para las puntuaciones del juego Ahorcado
        val gridLayoutManager = GridLayoutManager(activity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        dataList = ArrayList()
        adapter = MyAdapterAhorcado(dataList)
        binding.recyclerView.adapter = adapter
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")

        eventListener = database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(Usuarios::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}