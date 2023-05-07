package com.alvaro.proyectofinal.Juegos.Hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.alvaro.proyectofinal.R;

public class LetrasAdapter extends BaseAdapter {

    private String[] letras;
    private LayoutInflater letrasLayout;

    public LetrasAdapter(Context context){
       letras = new String[26];
       for (int i = 0; i < letras.length; i++){
           letras[i] = "" + (char)(i + 'A');
       }
       letrasLayout = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return letras.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button btnLetra;
        if (view == null){
            btnLetra = (Button) letrasLayout.inflate(R.layout.letras,viewGroup,false);
        }else {
            btnLetra = (Button)view;
        }
        btnLetra.setText(letras[i]);
        return btnLetra;
    }
}
