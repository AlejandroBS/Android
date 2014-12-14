package com.test.alejandro.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class verResultadoTest extends Activity {
    Bundle bundle;
    TextView aptoNoApto;
    int aciertos = 0, fallos = 0, numRespuestas = 0;
    String nombre = "";
    char[] respuestasEscogidas;
    char[] respuestasCorrectas;
    FileInputStream fis;
    String _DIRECTORIO_TEST_;
    Test test;
    ObjectInputStream fichero;
    String nombreFichero;
    int numPreguntasInsertadas,numPreguntas;
    Character[] respuestas;
    String[] enunciados;
    String[] str_respuestasCorrectas;
    String[] str_respuestasEscogidas;
    ResultadoTest[] resultadoTest;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultado_test);
        bundle = getIntent().getExtras();
        lista = (ListView) findViewById(R.id.lista);
        boolean estado = bundle.getBoolean("resultado");
        aptoNoApto = (TextView) findViewById(R.id.resultado);
        if(estado==true){
            aptoNoApto.setText("APTO");
            aptoNoApto.setTextColor(Color.rgb(145,252,141));
        }
        else{
            aptoNoApto.setTextColor(Color.rgb(253,128,128));
            aptoNoApto.setText("NO APTO");
        }

        _DIRECTORIO_TEST_ = Environment.getExternalStorageDirectory().toString()+File.separator+"aprendetest"+File.separator+"data";
        nombreFichero = bundle.getString("nombre");
        try {
            fis = new FileInputStream(_DIRECTORIO_TEST_+ File.separator+nombreFichero+File.separator+nombreFichero+".test");
            fichero = new ObjectInputStream(fis);
            numPreguntas = fichero.readInt();
            test = new Test(numPreguntas);
            numPreguntasInsertadas = fichero.readInt();
            resultadoTest = new ResultadoTest[numPreguntasInsertadas];
            enunciados = new String[numPreguntasInsertadas];
            test = (Test) fichero.readObject();
            aciertos = bundle.getInt("aciertos");
            fallos = bundle.getInt("fallos");
            fichero.close();
            respuestasEscogidas = new char[numPreguntasInsertadas];
            respuestasEscogidas = bundle.getCharArray("respuestasEscogidas");
            respuestasCorrectas = new char[numPreguntasInsertadas];
            str_respuestasCorrectas = new String[numPreguntasInsertadas];
            str_respuestasEscogidas = new String[numPreguntasInsertadas];
            for(int i = 0; i<numPreguntasInsertadas;i++){
                enunciados[i] = test.getPregunta(i).getEnunciado();
                respuestasCorrectas[i] = test.getPregunta(i).getRespuestaCorrecta();
                if(respuestasCorrectas[i]=='A'){
                    str_respuestasCorrectas[i] = test.getPregunta(i).getRespuestaA();
                }
                else if(respuestasCorrectas[i]=='B'){
                    str_respuestasCorrectas[i] = test.getPregunta(i).getRespuestaB();
                }
                else if(respuestasCorrectas[i]=='C'){
                    str_respuestasCorrectas[i] = test.getPregunta(i).getRespuestaC();
                }
                else{
                    str_respuestasCorrectas[i]="";
                }
                if(respuestasEscogidas[i]=='A'){
                    str_respuestasEscogidas[i] = test.getPregunta(i).getRespuestaA();
                }
                else if(respuestasEscogidas[i]=='B'){
                    str_respuestasEscogidas[i] = test.getPregunta(i).getRespuestaB();
                }
                else if(respuestasEscogidas[i]=='C'){
                    str_respuestasEscogidas[i] = test.getPregunta(i).getRespuestaC();
                }
                else{
                    str_respuestasEscogidas[i]="";
                }
            }

            for(int i = 0;i<numPreguntasInsertadas;i++){
                resultadoTest[i] = new ResultadoTest(respuestasEscogidas[i],str_respuestasEscogidas[i],respuestasCorrectas[i],str_respuestasCorrectas[i],enunciados[i],i);
            }


            DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date dt_fecha = new Date();
            System.out.println(fecha.format(dt_fecha));
            DateFormat hora = new SimpleDateFormat("HH:mm");
            Date dt_hora = new Date();
            System.out.println(hora.format(dt_hora));

            AdapterResultados adapter = new AdapterResultados(verResultadoTest.this,resultadoTest);
            lista.setAdapter(adapter);
            //System.out.println(fecha+".."+hora.getDateFormatSymbols().toString());
            Handler_sqlite sqlite = new Handler_sqlite(getApplicationContext());
            //Log.d("INSERTAR", nombreFichero +" // "+ aciertos +" // "+ fallos +" // "+ estado +" // "+ fecha.format(dt_fecha).toString() +" // "+ hora.format(dt_hora).toString());
            sqlite.insertar(nombreFichero, aciertos, fallos, estado, fecha.format(dt_fecha).toString(), hora.format(dt_hora).toString());
            sqlite.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_resultado_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
