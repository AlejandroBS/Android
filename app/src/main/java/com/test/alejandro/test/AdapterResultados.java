package com.test.alejandro.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class AdapterResultados extends ArrayAdapter<ResultadoTest>{

    Activity context;
    ResultadoTest[] datos;

    public AdapterResultados(Activity context, ResultadoTest[] resource) {
        super(context, R.layout.item_resultado, resource);
        this.context = context;
        this.datos=resource;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_resultado,null);
        ResultadoTest result = datos[posicion];
        RelativeLayout relative = (RelativeLayout) item.findViewById(R.id.relative);
        TextView nPreg = (TextView) item.findViewById(R.id.textView2);
        TextView lbl_enunciado = (TextView) item.findViewById(R.id.textView3);
        TextView txt_enunciado = (TextView) item.findViewById(R.id.textView4);
        TextView lbl_respuestaCorrecta = (TextView) item.findViewById(R.id.textView5);
        TextView txt_respuestaCorrecta = (TextView) item.findViewById(R.id.textView6);
        TextView lbl_respuestaEscogida = (TextView) item.findViewById(R.id.textView7);
        TextView txt_respuestaEscogida = (TextView) item.findViewById(R.id.textView8);

        boolean estado;
        if(result.getChr_respuestaCorrecta()==result.getChr_respuestaEscogida()){
            estado = true;
        }
        else{
            estado=false;
        }

        nPreg.setText("Pregunta "+(posicion+1)+":");
        lbl_enunciado.setText("Enunciado: ");
        txt_enunciado.setText(result.getEnunciado());
        lbl_respuestaCorrecta.setText("Respuesta Correcta: ");
        txt_respuestaCorrecta.setText(result.getChr_respuestaCorrecta() + ") " + result.getStr_respuestaCorrecta());
        if(estado == true){
            lbl_respuestaEscogida.setTextSize(0);
            txt_respuestaEscogida.setTextSize(0);
            lbl_respuestaEscogida.setX(0);
            txt_respuestaEscogida.setX(0);
            lbl_respuestaEscogida.setY(0);
            txt_respuestaEscogida.setY(0);
            //verde
            relative.setBackgroundColor(Color.rgb(145,252,141));
        }
        else{
            //rojo
            relative.setBackgroundColor(Color.rgb(253,128,128));
            if(result.getChr_respuestaEscogida()=='0'){

                lbl_respuestaEscogida.setText("Respuesta Escogida: ");
                txt_respuestaEscogida.setText("No respondida");
            }
            else{
                lbl_respuestaEscogida.setText("Respuesta Escogida: ");
                txt_respuestaEscogida.setText(result.getChr_respuestaEscogida() + ") " + result.getStr_respuestaEscogida());
            }

        }


        return item;
    }

}
