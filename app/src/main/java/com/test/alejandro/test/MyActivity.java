package com.test.alejandro.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static android.widget.Toast.LENGTH_LONG;


public class MyActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miotrolayout);
        ImageButton estadisticas = (ImageButton) findViewById(R.id.imageButton3);
        estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MyActivity.this,estadisticas.class);
                startActivity(t);
            }
        });
        ImageButton study = (ImageButton)findViewById(R.id.imageButton2);
        study.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent t = new Intent(MyActivity.this,ActivityLoadingListarTest.class);
                startActivity(t);

            }
        });


        File f = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"aprendetest"+File.separator+"data");
        f.mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
