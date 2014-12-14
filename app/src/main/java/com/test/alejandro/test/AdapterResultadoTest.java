package com.test.alejandro.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Alejandro on 08/12/2014.
 */
public class AdapterResultadoTest extends ArrayAdapter {
    Activity context;
    String[][] datos;
    public AdapterResultadoTest(Activity context, String[][] resource) {
        super(context,R.layout.item_resultado_test, resource);
        this.context=context;
        this.datos=resource;
    }


     @Override
     public View getView(int posicion, View view, ViewGroup parent){
         LayoutInflater inflater = context.getLayoutInflater();
         View item = inflater.inflate(R.layout.item_resultado_test,null);

         TextView txt_fechaHora = (TextView) item.findViewById(R.id.txt_fechaHora);
         TextView txt_aciertos = (TextView) item.findViewById(R.id.txt_aciertos);
         TextView txt_fallos = (TextView) item.findViewById(R.id.txt_fallos);
         TextView txt_resultado = (TextView) item.findViewById(R.id.txt_resultado);

         txt_fechaHora.setText(datos[posicion][4]+" "+datos[posicion][5]);
         txt_aciertos.setText("Aciertos: "+datos[posicion][1]);
         txt_fallos.setText("Fallos: "+datos[posicion][2]);

         if(datos[posicion][3].equals("APTO")){
             txt_resultado.setText("Resultado: "+"APTO");
             txt_resultado.setTextColor(Color.rgb(145,252,141));
         }
         else{
             txt_resultado.setText("Resultado: "+"NO APTO");
             txt_resultado.setTextColor(Color.rgb(253,128,128));
         }



         return item;

     }
}
