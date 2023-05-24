package com.alvaro.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.google.firebase.database.core.Context

class MyAdapterAhorcado(private val context: Context, private val dataList: List<Usuarios>):RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.posicion.text = ""
        holder.usuario.text = dataList[position].nombre
        holder.puntuacion.text = dataList[position].puntuacionAhorcado.toString()
    }
}

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var posicion: TextView
    var usuario: TextView
    var puntuacion: TextView

    init {
        posicion = itemView.findViewById(R.id.posicion)
        usuario = itemView.findViewById(R.id.usuario)
        puntuacion = itemView.findViewById(R.id.puntuacion)
    }
}