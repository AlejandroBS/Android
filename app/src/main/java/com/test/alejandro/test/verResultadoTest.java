package com.test.alejandro.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;


public class verResultadoTest extends Activity {
    Bundle bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultado_test);
        bundle = getIntent().getExtras();

        try {
            Log.d("existe", "existe");
            fis = new FileInputStream(_DIRECTORIO_TEST_+ File.separator+nombreFichero+File.separator+nombreFichero+".test");
            fichero = new ObjectInputStream(fis);

            numPreguntas = fichero.readInt();
            test = new Test(numPreguntas);
            numPreguntasInsertadas = fichero.readInt();
            //test = (Test) fichero.readUnshared();
            test = (Test) fichero.readObject();
            fichero.close();
            respuestas = new Character[numPreguntasInsertadas];
            for(int i = 0; i<numPreguntasInsertadas;i++){
                respuestas[i] = '0';
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


/*
        intent.putExtra("aciertos", aciertos);
        intent.putExtra("fallos", fallos);
        intent.putExtra("nombre", nombreFichero);
        intent.putExtra("numPreguntas",numPreguntasInsertadas);
        intent.putExtra("numRespuestas", numRespuestasInsertadas);
        intent.putExtra("respuestasEscogidas",respuestas);*/




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
