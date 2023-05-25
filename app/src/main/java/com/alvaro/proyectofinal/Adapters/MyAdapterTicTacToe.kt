package com.alvaro.proyectofinal.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.proyectofinal.BaseDeDatos.Usuarios
import com.alvaro.proyectofinal.R

class MyAdapterTicTacToe(private val dataList: List<Usuarios>) :
//Creamos un adaptador para la tarjeta donde se mostrara la puntuacion
    RecyclerView.Adapter<MyViewHolderTicTacToe>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTicTacToe {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolderTicTacToe(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderTicTacToe, position: Int) {
        //Obtenemos los valores de cada uno de los datos necesarios que vamos a mostrar
        holder.posicion.text = ""
        holder.usuario.text = dataList[position].nombre
        holder.puntuacion.text = dataList[position].puntuacionTresEnRaya.toString()
    }
}

class MyViewHolderTicTacToe(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Declaramos los distintos elementos de la base de datos
    var posicion: TextView
    var usuario: TextView
    var puntuacion: TextView

    init {
        posicion = itemView.findViewById(R.id.posicion)
        usuario = itemView.findViewById(R.id.usuario)
        puntuacion = itemView.findViewById(R.id.puntuacion)
    }
}
