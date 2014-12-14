package com.test.alejandro.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.Vector;

import static android.provider.BaseColumns._ID;
/**
 * Created by Alejandro on 07/12/2014.
 */
public class Handler_sqlite extends SQLiteOpenHelper {

    public Handler_sqlite(Context context) {
        super(context, "aprendetest", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE mispuntuaciones("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, test TEXT, aciertos INTEGER, fallos INTEGER, resultado INTEGER, fecha TEXT, hora TEXT ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mispuntuaciones");
        onCreate(db);
    }

    /**
     *
     * @param test nombre
     * @param aciertos aciertos
     * @param fallos fallos
     * @param resultado apto/no apto
     * @param fecha fecha
     * @param hora hora
     * Inserta un nuevo test en la base de datos
     */
    public void insertar(String test, int aciertos, int fallos, boolean resultado, String fecha, String hora){
        ContentValues valores = new ContentValues();
        valores.put("test", test);
        valores.put("aciertos", aciertos);
        valores.put("fallos", fallos);
        if(resultado) valores.put("resultado", 1) ; else valores.put("resultado", 0);
        valores.put("fecha", fecha);
        valores.put("hora",hora);
        this.getWritableDatabase().insert("mispuntuaciones",null,valores);
    }

    /**
     *
     * @return devuelve un array bidimensional con toda la informacion de todos los tests que s ehan realizado
     */
    public String[] leerTestRealizados(){
        String[] vector = null;
        String columnas[] = {"test"};
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT DISTINCT test FROM mispuntuaciones",null/*"mispuntuaciones",columnas,null,null,null,null,null,null*/);
        for(int i = 0;cursor.moveToNext();i++){
            if(!new File(Environment.getExternalStorageDirectory().toString()+"/aprendetest/data/"+cursor.getString(0)+"/"+cursor.getString(0)+".test").exists()){
                this.getWritableDatabase().delete("mispuntuaciones","test = '"+cursor.getString(0)+"'",null);
            }
        }
        cursor = this.getReadableDatabase().rawQuery("SELECT DISTINCT test FROM mispuntuaciones",null/*"mispuntuaciones",columnas,null,null,null,null,null,null*/);
        vector = new String[cursor.getCount()];
        for(int i = 0;cursor.moveToNext();i++){
                vector[i] = cursor.getString(0);
        }

        return vector;
    }

    /**
     *
     * @param test es un String que contiene el nombre del test para buscarlo en la base de datos
     * @return Un array bidimensional de Strings con toda la informacion del test requerido.
     */
    public String[][] leerDatosTestRealizado(String test){
        String[][] vector = null;
        String columnas[] = {"test","aciertos","fallos","resultado","fecha","hora"};
        String[] tst = {test};
        Cursor cursor = this.getReadableDatabase().query("mispuntuaciones",columnas,"test=?",tst,null,null,null);


            vector = new String[cursor.getCount()][6];
            for(int i = 0;cursor.moveToNext();i++) {
                vector[i][0] = cursor.getString(0);
                vector[i][1] = cursor.getInt(1)+"";
                vector[i][2] = cursor.getInt(2)+"";
                if(cursor.getInt(3)==1){

                    vector[i][3] = "APTO";
                }
                else{
                    vector[i][3] = "NO APTO";
                }
                vector[i][4] = cursor.getString(4);
                vector[i][5] = cursor.getString(5);
            }

        return vector;
    }

    /**
     *
     * @param test es un String que contiene el nombre del test para buscarlo en la base de datos
     * @return deuelve un entero, que es la version del test que se ha pasado como parametro
     */
    public int getUltimoResultadoTest(String test){
        int resultado = 0;

        String sql = " SELECT resultado FROM mispuntuaciones WHERE test = '"+test+"' ORDER BY "+_ID+" DESC LIMIT 1 ";
        Cursor c = this.getReadableDatabase().rawQuery(sql, null);
        if(c.moveToFirst()){
            resultado = c.getInt(0);
        }
        else{
            resultado=-1;
        }
        return resultado;
    }

}
