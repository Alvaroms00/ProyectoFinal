package com.alvaro.proyectofinal.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.R

class MyAdapterAhorcado(private val dataList: List<Usuarios>) :
    RecyclerView.Adapter<MyViewHolderAhorcado>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderAhorcado {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolderAhorcado(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderAhorcado, position: Int) {
        holder.posicion.text = ""
        holder.usuario.text = dataList[position].nombre
        holder.puntuacion.text = dataList[position].puntuacionAhorcado.toString()
    }
}

class MyViewHolderAhorcado(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var posicion: TextView
    var usuario: TextView
    var puntuacion: TextView

    init {
        posicion = itemView.findViewById(R.id.posicion)
        usuario = itemView.findViewById(R.id.usuario)
        puntuacion = itemView.findViewById(R.id.puntuacion)
    }
}