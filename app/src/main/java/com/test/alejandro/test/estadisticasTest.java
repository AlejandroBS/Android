package com.test.alejandro.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class estadisticasTest extends Activity {
    String[][] datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_test);
        Bundle bundle = getIntent().getExtras();

        TextView txt = (TextView) findViewById(R.id.txt_nombreTest);
        txt.setText(bundle.getString("test"));
        ListView lista = (ListView) findViewById(R.id.lista_resultadoTest);
        Handler_sqlite sqlite = new Handler_sqlite(estadisticasTest.this);
        datos = sqlite.leerDatosTestRealizado(bundle.getString("test"));
        AdapterResultadoTest adapter = new AdapterResultadoTest(this,datos);
        lista.setAdapter(adapter);
        sqlite.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_estadisticas_test, menu);
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
