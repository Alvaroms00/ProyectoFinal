package com.alvaro.proyectofinal.Juegos.Hangman;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.proyectofinal.R;

import java.util.Random;

public class HangmanActivity extends AppCompatActivity {
    private String[] palabras;
    private Random random;
    private String palabraAnterior;
    private TextView[] letrasView;
    private LinearLayout palabraLayout;
    private LetrasAdapter adapter;
    private GridView gridView;
    private int numCorrecto;
    private int numCaracter;
    private ImageView[] partes;
    private final int partesSize = 6;
    private int parteAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        palabras = getResources().getStringArray(R.array.palabras);
        palabraLayout = findViewById(R.id.palabra);
        gridView = findViewById(R.id.letras);
        random = new Random();

        partes = new ImageView[partesSize];
        partes[0] = findViewById(R.id.imgCabeza);
        partes[1] = findViewById(R.id.imgCuerpo);
        partes[2] = findViewById(R.id.imgBrazoDerecho);
        partes[3] = findViewById(R.id.imgBrazoIzquierdo);
        partes[4] = findViewById(R.id.imgPiernaDerecha);
        partes[5] = findViewById(R.id.imgPiernaIzquierda);
        juego();
    }

    private void juego(){
        String nuevaPalabra = palabras[random.nextInt(palabras.length)];

        while(nuevaPalabra.equals(palabraAnterior))nuevaPalabra = palabras[random.nextInt(palabras.length)];

        palabraAnterior = nuevaPalabra;

        letrasView = new TextView[palabraAnterior.length()];

        palabraLayout.removeAllViews();

        for (int i = 0; i < palabraAnterior.length(); i++){
            letrasView[i] = new TextView(this);
            letrasView[i].setText("" + palabraAnterior.charAt(i));
            letrasView[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            letrasView[i].setGravity(Gravity.CENTER);
            letrasView[i].setTextColor(Color.WHITE);
            letrasView[i].setBackgroundResource(R.drawable.letras);
            palabraLayout.addView(letrasView[i]);
        }

        adapter = new LetrasAdapter(this);
        gridView.setAdapter(adapter);
        numCorrecto = 0;
        parteAnterior = 0;
        numCaracter = palabraAnterior.length();

        for (int i = 0; i <partesSize; i++){
            partes[i].setVisibility(View.INVISIBLE);}
    }

    public void letraPresionada(View view){
        String letra = ((TextView)view).getText().toString();
        char letraChar = letra.charAt(0);

        view.setEnabled(false);

        boolean correcto = false;

        for (int i=0; i<palabraAnterior.length();i++){
            if (palabraAnterior.charAt(i)==letraChar){
                correcto = true;
                numCorrecto++;
                letrasView[i].setTextColor(Color.BLACK);
            }
        }


        if (correcto){
            if (numCorrecto == numCaracter){
                deshabilitarBoton();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.victoria);
                builder.setTitle("Has Ganado");
                builder.setMessage("Has acertado la palabra\n\nLa respuesta era:\n" + palabraAnterior);
                builder.setPositiveButton("Jugar de nuevo", (dialog, which) -> HangmanActivity.this.juego());
                builder.setNegativeButton("Salir", (dialog, which) -> finish());
                builder.show();
            }
        } else if (parteAnterior<partesSize) {
            partes[parteAnterior].setVisibility(View.VISIBLE);
            parteAnterior++;
        }else{
            deshabilitarBoton();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.derrota);
            builder.setTitle("Has Perdido");
            builder.setMessage("No has acertado la palabra\n\nLa palabra era:\n" + palabraAnterior);
            builder.setPositiveButton("Jugar de nuevo", (dialogInterface, i) -> HangmanActivity.this.juego());
            builder.setNegativeButton("Salir", (dialogInterface, i) -> finish());
            builder.show();
        }
    }

    public void deshabilitarBoton() {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            gridView.getChildAt(i).setEnabled(false);
        }
    }

}