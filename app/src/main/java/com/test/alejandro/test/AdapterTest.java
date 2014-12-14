package com.test.alejandro.test;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.Handler;

/**
 * Created by Alejandro on 24/11/2014.
 */
public class AdapterTest extends ArrayAdapter<ItemTest>{
    Activity context;
    ItemTest[] datos;
    public AdapterTest(Activity context, ItemTest[] datos){
        super(context, R.layout.item_test,datos);
        this.context=context;
        this.datos = datos;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_test,null);

        TextView titulo = (TextView) item.findViewById(R.id.titulo);
        titulo.setText(datos[posicion].getTitulo());
/*
        TextView numPreguntas = (TextView) item.findViewById(R.id.numpreguntas);
        numPreguntas.setText(datos[posicion].getnPreguntas());
*/
        ImageView image = (ImageView) item.findViewById(R.id.imageView);
        if(datos[posicion].getEstado().equals("Realizar")){
            image.setImageResource(R.drawable.pencil_s);
        }
        if(datos[posicion].getEstado().equals("Descargar")){
            image.setImageResource(R.drawable.download);
        }
        if(datos[posicion].getEstado().equals("Redescargar")){
            image.setImageResource(R.drawable.download);
            TextView txt_redescargar = (TextView) item.findViewById(R.id.txt_redescargar);
            txt_redescargar.setText("(Nueva versión)");
        }
        ImageView image2 = (ImageView) item.findViewById(R.id.imageView3);

        Handler_sqlite sqlite = new Handler_sqlite(context);
        if(sqlite.getUltimoResultadoTest(datos[posicion].getTitulo())==1){
            image2.setImageResource(R.drawable.prov_aprobado);
        }
        else if (sqlite.getUltimoResultadoTest(datos[posicion].getTitulo())==0){
            image2.setImageResource(R.drawable.prov_suspenso);
        } else if (sqlite.getUltimoResultadoTest(datos[posicion].getTitulo())==-1){
            image2.setX(0);
            image2.setY(0);
            image2.setImageBitmap(null);
        }
        sqlite.close();

        return item;
    }
}
