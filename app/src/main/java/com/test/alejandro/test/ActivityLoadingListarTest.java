package com.test.alejandro.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ActivityLoadingListarTest extends Activity {

    private final int WAIT_TIME = 2500;
    public static boolean acabado = false;
    public static ArrayList<Integer> versiones;
    public static ArrayList<String> listaFicheros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_listar_test);

        findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);  File f = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"aprendetest"+File.separator+"data");
        f.mkdirs();
        AsyncTaskListarTest tarea = new AsyncTaskListarTest();
        tarea.execute(new Parametros(1));

        TextView tv_cargando = (TextView) findViewById(R.id.cargando);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_async_listar_test, menu);
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

    /**
     * Esta clase se conecta al servidor para saber que test hay en el servidor y que versión tienen.
     */
    class AsyncTaskListarTest extends AsyncTask<Parametros,Progreso,Resultado> {

        private boolean error = false;
        Socket socket;
        @Override
        protected void onPreExecute() {
            versiones = new ArrayList<Integer>();
        }
        @Override
        protected Resultado doInBackground(Parametros... params) {
            //Log.d("H", "HOLAAA");

            ArrayList<String> listaFicheros = new ArrayList<String>();

            int tamano = 0;
            try {
                socket = new Socket(DatosConexion.IP,DatosConexion.port);

                if(socket.isConnected()){
                    error = false;
                }
                else{
                    error = true;
                    return null;
                }
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                // le dice al servidor que es un usuario
                salida.writeInt(1);
                salida.flush();
                //le dice al servidor que quiere la lista de los ficheros test
                salida.writeInt(1);
                salida.flush();
                while(entrada.readBoolean()){
                    versiones.add(tamano,entrada.readInt());
                    String s = entrada.readUTF();
                    listaFicheros.add(s);
                    tamano++;

                    // publishProgress(null);
                }
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                error = true;
            }
            return new Resultado(listaFicheros,tamano);
        }
        @Override
        protected void onProgressUpdate(Progreso... prog){
            //Toast.makeText(null,"HOLA",Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPostExecute(Resultado res){
            //error = false;
            if(error == false) {
                listaFicheros = res.listaFicheros;

                Intent intent = new Intent(ActivityLoadingListarTest.this, Activity_Estudiar.class);
                //for(int i = 0;i<listaFicheros.size();i++){
                intent.putExtra("con", '1');
                intent.putExtra("items", listaFicheros);
                intent.putExtra("versiones",versiones);
                //}
                finish();
                startActivity(intent);

            }
            else{
                Intent intent = new Intent(ActivityLoadingListarTest.this, Activity_Estudiar.class);
                //for(int i = 0;i<listaFicheros.size();i++){
                intent.putExtra("con", '0');
//                Log.d("VALUEOF", String.valueOf(null));
                //}
                finish();
                startActivity(intent);


            }
        }
    }

}

