package com.alvaro.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.alvaro.proyectofinal.Adapters.MyAdapterTicTacToe
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.databinding.FragmentTictactoePuntuacionBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TictactoePuntuacionFragment : Fragment() {
    private lateinit var binding: FragmentTictactoePuntuacionBinding
    private lateinit var dataList: ArrayList<Usuarios>
    private lateinit var adapter: MyAdapterTicTacToe
    var database: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTictactoePuntuacionBinding.inflate(inflater, container, false).also { binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(activity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        dataList = ArrayList()
        adapter = MyAdapterTicTacToe(dataList)
        binding.recyclerView.adapter = adapter
        database = FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")

        eventListener = database!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children){
                    val dataClass = itemSnapshot.getValue(Usuarios::class.java)
                    if (dataClass != null){
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