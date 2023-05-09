package com.alvaro.proyectofinal.Juegos.TresEnRaya;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.proyectofinal.R;

import java.util.Arrays;
import java.util.Random;

public class TictactoeActivity extends AppCompatActivity {
    TextView textoVictoria;
    Integer[] botones;

    int[] tablero = new int[]{
            0,0,0,
            0,0,0,
            0,0,0
    };

    int estado = 0;
    int fichaPuesta = 0;

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_tictactoe);

        textoVictoria = findViewById(R.id.txtVictoria);
        textoVictoria.setVisibility(View.INVISIBLE);

        botones = new Integer[]{
                R.id.boton1,R.id.boton2,R.id.boton3,
                R.id.boton4,R.id.boton5,R.id.boton6,
                R.id.boton7,R.id.boton8,R.id.boton9
        };
    }

    public void ponerFicha(View view){
        if (estado == 0){
            int numBoton = Arrays.asList(botones).indexOf(view.getId());
            if (tablero[numBoton] == 0){
                view.setBackgroundResource(R.drawable.cruz);
                tablero[numBoton] = 1;
                fichaPuesta++;
                estado = comprobarEstado();
                if (estado == 0){
                    maquina();
                    fichaPuesta++;
                    estado = comprobarEstado();
                }
            }
        }
    }

    public void maquina(){
        Random random = new Random();
        int posicion = random.nextInt(tablero.length);

        while (tablero[posicion] != 0){
            posicion = random.nextInt(tablero.length);
        }

        Button boton = findViewById(botones[posicion]);
        boton.setBackgroundResource(R.drawable.circulo);
        tablero[posicion] = -1;
    }

    public int comprobarEstado(){
        if (fichaPuesta < 9){
            return 0;
        }else {
            return 2;
        }
    }
}
