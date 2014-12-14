package com.test.alejandro.test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class AdaptadorEstadisticas extends ArrayAdapter {
    String[] datos;
    Activity ctx ;
    public AdaptadorEstadisticas(Activity context, String[] resource) {
        super(context,R.layout.item_estadisticas,resource);
        ctx=context;
        datos=resource;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater = ctx.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_estadisticas,null);

        TextView txt = (TextView) item.findViewById(R.id.textView9);
        if(datos[posicion].isEmpty()){
                txt.setTextSize(0);
                txt.setX(0);
                txt.setY(0);
        }else {
            txt.setText(datos[posicion]);
        }
        return item;

    }

}
