package com.alvaro.proyectofinal.Juegos.TresEnRaya.Modelos

class Tablero(private  val tablero: MutableMap<Celda, EstadoCelda> = mutableMapOf()) {

    fun estado(celda: Celda): EstadoCelda{
        return tablero[celda] ?: EstadoCelda.Vacio
    }

    fun estadoCelda(celda: Celda, estadoCelda: EstadoCelda): Boolean{
        if (tablero.containsKey(celda)){
            return false
        }
        tablero[celda] = estadoCelda
        return true
    }

    fun reiniciarTablero(){
        tablero.clear()
    }

    fun buscarMovimientoGanador(estado: EstadoCelda): Celda? = when{
        Celda.TOP_LEFT victoria estado -> Celda.TOP_LEFT
        Celda.TOP_CENTER victoria estado -> Celda.TOP_CENTER
        Celda.TOP_RIGHT victoria estado -> Celda.TOP_RIGHT
        Celda.CENTER_LEFT victoria estado -> Celda.CENTER_LEFT
        Celda.CENTER_CENTER victoria estado -> Celda.CENTER_CENTER
        Celda.CENTER_RIGHT victoria estado -> Celda.CENTER_RIGHT
        Celda.BOT_LEFT victoria estado -> Celda.BOT_LEFT
        Celda.BOT_CENTER victoria estado -> Celda.BOT_CENTER
        Celda.BOT_RIGHT victoria estado -> Celda.BOT_RIGHT

        else -> null
    }

    private infix  fun Celda.victoria(estado: EstadoCelda): Boolean{
        if (tablero.containsKey(this)){
            return false
        }
        tablero[this] = estado
        val victoria = estadoVictoria(estado)
        tablero.remove(this)
        return victoria
    }

    val estadoTablero: EstadoTablero
        get() = when{
            estadoVictoria(EstadoCelda.Cruz) -> EstadoTablero.CRUCES_GANAN
            estadoVictoria(EstadoCelda.Circulo) -> EstadoTablero.CIRCULOS_GANAN
            tablero.size < 9 -> EstadoTablero.INCOMPLETO
            else -> EstadoTablero.EMPATE
        }

    private fun estadoVictoria(estado: EstadoCelda): Boolean{
        fun estadoPrueba(vararg celdas: Celda): Boolean = celdas.all { celda ->
            tablero[celda] == estado
        }
        return  estadoPrueba(Celda.TOP_LEFT, Celda.CENTER_LEFT, Celda.BOT_LEFT) ||
                estadoPrueba(Celda.TOP_CENTER, Celda.CENTER_CENTER, Celda.BOT_CENTER) ||
                estadoPrueba(Celda.TOP_RIGHT, Celda.CENTER_RIGHT, Celda.BOT_RIGHT) ||
                estadoPrueba(Celda.TOP_LEFT, Celda.TOP_CENTER, Celda.TOP_RIGHT) ||
                estadoPrueba(Celda.CENTER_LEFT, Celda.CENTER_CENTER, Celda.CENTER_RIGHT) ||
                estadoPrueba(Celda.BOT_LEFT, Celda.BOT_CENTER, Celda.BOT_RIGHT) ||
                estadoPrueba(Celda.TOP_LEFT, Celda.CENTER_CENTER, Celda.BOT_RIGHT) ||
                estadoPrueba(Celda.TOP_RIGHT, Celda.CENTER_CENTER, Celda.BOT_LEFT)

    }

}

