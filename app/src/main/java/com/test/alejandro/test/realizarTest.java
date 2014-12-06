package com.test.alejandro.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;


public class realizarTest extends Activity {
    File f;
    FileInputStream fis ;
    ObjectInputStream fichero;
    public String _DIRECTORIO_TEST_;
    TextView enunciado, nombreTest, numPreg;
    ImageView imagen;
    RadioButton radioA,radioB,radioC;
    RadioGroup grupoRadio;
    ImageButton btnAnterior,btnSiguiente;
    Bitmap bmp;
    Button corregir;AlertDialog alerta;
    //AlertDialog dialogo;
    //AlertDialog  alerta;
    Test test;
    Pregunta pregunta;
    int numPreguntas,numPreguntasInsertadas, numPreguntaActual=0, numRespuestasInsertadas=0;
    int aciertos = 0, fallos = 0;
    Character[] respuestas;
    String nombreFichero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_test);
        Bundle bundle = getIntent().getExtras();
        nombreFichero = bundle.getString("nombre");
        _DIRECTORIO_TEST_ =   Environment.getExternalStorageDirectory().toString()+File.separator+"aprendetest"+File.separator+"data";

        nombreTest  = (TextView) findViewById(R.id.nombreTest);
        numPreg = (TextView) findViewById(R.id.numPreg);
        enunciado = (TextView) findViewById(R.id.enunciado);
        imagen = (ImageView) findViewById(R.id.imagen);
        radioA = (RadioButton) findViewById(R.id.radioA);
        radioB = (RadioButton) findViewById(R.id.radioB);
        radioC = (RadioButton) findViewById(R.id.radioC);
        grupoRadio = (RadioGroup) findViewById(R.id.RadioGroup);
        btnAnterior = (ImageButton) findViewById(R.id.btn_anterior);
        btnSiguiente = (ImageButton) findViewById(R.id.btn_siguiente);
        corregir = (Button) findViewById(R.id.btn_corregir);

        f = new File(_DIRECTORIO_TEST_+File.separator+nombreFichero+File.separator+nombreFichero+".test");
        Log.d("path",f.getAbsolutePath());
        //if(f.exists())
        try {
            Log.d("existe","existe");
            fis = new FileInputStream(_DIRECTORIO_TEST_+File.separator+nombreFichero+File.separator+nombreFichero+".test");
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
        nombreTest.setText(nombreFichero);
        if(test!=null){
            pregunta = test.getPregunta(numPreguntaActual);
            numPreg.setText("Pregunta "+ (numPreguntaActual+1) +" / " + numPreguntasInsertadas);
            bmp = BitmapFactory.decodeFile(_DIRECTORIO_TEST_ + File.separator + pregunta.getImagen());
            imagen.setImageBitmap(bmp);
            enunciado.setText(pregunta.getEnunciado());
            radioA.setText(pregunta.getRespuestaA());
            radioB.setText(pregunta.getRespuestaB());
            radioC.setText(pregunta.getRespuestaC());

            /*if(pregunta.getRespuestaCorrecta()=='A'){

                radioA.setChecked(true);
            }
            else if(pregunta.getRespuestaCorrecta()=='B'){
                radioB.setChecked(true);
            }
            else if(pregunta.getRespuestaCorrecta()=='C'){
                radioC.setChecked(true);
            }*/


            btnAnterior.setEnabled(false);
            if(numPreguntasInsertadas<=2){
                btnSiguiente.setEnabled(false);
            }
            btnAnterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioA.setBackgroundColor(Color.TRANSPARENT);
                    radioB.setBackgroundColor(Color.TRANSPARENT);
                    radioC.setBackgroundColor(Color.TRANSPARENT);

                    radioA.setChecked(false);
                    radioB.setChecked(false);
                    radioC.setChecked(false);


                    grupoRadio.clearCheck();
                    btnSiguiente.setEnabled(true);
                    numPreguntaActual--;
                    if (numPreguntaActual == 0) {
                        btnAnterior.setEnabled(false);
                    }
                    pregunta = test.getPregunta(numPreguntaActual);
                    numPreg.setText("Pregunta " + (numPreguntaActual + 1) + " / " + numPreguntasInsertadas);
                    bmp = BitmapFactory.decodeFile(_DIRECTORIO_TEST_ + File.separator + pregunta.getImagen());
                    imagen.setImageBitmap(bmp);
                    enunciado.setText(pregunta.getEnunciado());
                    radioA.setText(pregunta.getRespuestaA());
                    radioB.setText(pregunta.getRespuestaB());
                    radioC.setText(pregunta.getRespuestaC());
                    if(respuestas[numPreguntaActual]!='0'){
                        colores();
                        radioA.setEnabled(false);
                        radioB.setEnabled(false);
                        radioC.setEnabled(false);
                    }
                    else{
                        radioA.setEnabled(true);
                        radioB.setEnabled(true);
                        radioC.setEnabled(true);
                    }

                }
            });

            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radioA.setBackgroundColor(Color.TRANSPARENT);
                    radioB.setBackgroundColor(Color.TRANSPARENT);
                    radioC.setBackgroundColor(Color.TRANSPARENT);
                    radioA.setChecked(false);
                    radioB.setChecked(false);
                    radioC.setChecked(false);

                    grupoRadio.clearCheck();

                    btnAnterior.setEnabled(true);
                    numPreguntaActual++;
                    if(numPreguntaActual==numPreguntasInsertadas-1){
                        btnSiguiente.setEnabled(false);
                    }
                    pregunta = test.getPregunta(numPreguntaActual);
                    numPreg.setText("Pregunta "+ (numPreguntaActual+1) +" / " + numPreguntasInsertadas);
                    bmp = BitmapFactory.decodeFile(_DIRECTORIO_TEST_ + File.separator + pregunta.getImagen());
                    imagen.setImageBitmap(bmp);
                    enunciado.setText(pregunta.getEnunciado());
                    radioA.setText(pregunta.getRespuestaA());
                    radioB.setText(pregunta.getRespuestaB());
                    radioC.setText(pregunta.getRespuestaC());
                    if(respuestas[numPreguntaActual]!='0'){
                        colores();

                        radioA.setEnabled(false);
                        radioB.setEnabled(false);
                        radioC.setEnabled(false);
                    }
                    else{
                        radioA.setEnabled(true);
                        radioB.setEnabled(true);
                        radioC.setEnabled(true);
                    }

                }
            });
            radioA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (respuestas[numPreguntaActual] == '0') {
                            numRespuestasInsertadas++;

                            respuestas[numPreguntaActual] = 'A';
                        }
                        colores();radioA.setEnabled(false);
                        radioB.setEnabled(false);
                        radioC.setEnabled(false);
                        if (numRespuestasInsertadas == numPreguntasInsertadas) {
                            corregir();
                        }
                        Log.d("tamano",numRespuestasInsertadas+"");
                    }

                }
            });

            radioB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (respuestas[numPreguntaActual] == '0') {
                            numRespuestasInsertadas++;colores();
                            respuestas[numPreguntaActual] = 'B';
                        }
                        colores();radioA.setEnabled(false);
                        radioB.setEnabled(false);
                        radioC.setEnabled(false);
                        if (numRespuestasInsertadas == numPreguntasInsertadas) {
                            corregir();
                        }
                        Log.d("tamano",numRespuestasInsertadas+"");
                    }
                }
            });

            radioC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if(respuestas[numPreguntaActual]=='0'){
                            numRespuestasInsertadas++;colores();respuestas[numPreguntaActual]='C';
                        }
                        colores();radioA.setEnabled(false);
                        radioB.setEnabled(false);
                        radioC.setEnabled(false);

                        if(numRespuestasInsertadas==numPreguntasInsertadas){
                            corregir();
                        }
                        Log.d("tamano",numRespuestasInsertadas+"");
                    }
                }
            });
            alerta = new AlertDialog.Builder(this).create();

            alerta.setMessage("No ha respondido todas las preguntas.\n¿Está seguro de que desea corregir el test?\nLas preguntas no respondidas contarán como fallos.");
            alerta.setCancelable(true);
            alerta.setButton("Corregir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    corregir();
                }
            });
            corregir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(numRespuestasInsertadas==numPreguntasInsertadas){
                        corregir();
                    }
                    else{
                        alerta.show();
                    }


                }
            });

        }
    }

    private void colores(){
        if(respuestas[numPreguntaActual]=='A'){
            radioA.setChecked(true);
            if(respuestas[numPreguntaActual]==pregunta.getRespuestaCorrecta()){
                radioA.setBackgroundColor(Color.GREEN);
            }
            else{
                radioA.setBackgroundColor(Color.RED);
                if(pregunta.getRespuestaCorrecta()=='B') {
                    radioB.setBackgroundColor(Color.GREEN);
                }
                else{
                    radioC.setBackgroundColor(Color.GREEN);
                }
            }
        }
        if(respuestas[numPreguntaActual]=='B'){
            radioB.setChecked(true);
            if(respuestas[numPreguntaActual]==pregunta.getRespuestaCorrecta()){
                radioB.setBackgroundColor(Color.GREEN);
            }
            else{
                radioB.setBackgroundColor(Color.RED);
                if(pregunta.getRespuestaCorrecta()=='A') {
                    radioA.setBackgroundColor(Color.GREEN);
                }
                else{
                    radioC.setBackgroundColor(Color.GREEN);
                }
            }
        }
        if(respuestas[numPreguntaActual]=='C'){
            radioC.setChecked(true);
            if(respuestas[numPreguntaActual]==pregunta.getRespuestaCorrecta()){
                radioC.setBackgroundColor(Color.GREEN);
            }
            else{
                radioC.setBackgroundColor(Color.RED);
                if(pregunta.getRespuestaCorrecta()=='B') {
                    radioB.setBackgroundColor(Color.GREEN);
                }
                else{
                    radioA.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }
    private boolean corregir(){
        aciertos=0;
        fallos=0;
        boolean estado = true;

        for(int i = 0;i<numPreguntasInsertadas;i++){
            if(respuestas[i]==test.getPregunta(i).getRespuestaCorrecta()){
                aciertos++;
            }
            else{
                fallos++;
            }
        }
        if(((fallos*100)/numPreguntasInsertadas)>10){
            estado=false;
        }
        System.out.println(estado);

        Intent intent = new Intent(realizarTest.this, verResultadoTest.class);
        intent.putExtra("aciertos", aciertos);
        intent.putExtra("fallos", fallos);
        intent.putExtra("nombre", nombreFichero);
        intent.putExtra("numPreguntas",numPreguntasInsertadas);
        intent.putExtra("numRespuestas", numRespuestasInsertadas);
        intent.putExtra("respuestasEscogidas",respuestas);
        startActivity(intent);
        finish();

        return estado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_realizar_test, menu);
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
