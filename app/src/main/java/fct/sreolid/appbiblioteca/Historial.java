package fct.sreolid.appbiblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    ListView lista;
    TextView texto;
    ImageButton volver;

    ArrayList<String> libros;

    android.widget.ArrayAdapter<String> adapest;

    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.listado);
        texto = (TextView) findViewById(R.id.tvP);
        volver = (ImageButton) findViewById(R.id.ibReturn);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Inicializamos Base de datos y la abrimos
        //para recuperar el listado de libros del usuario
        dbAdapter= new MyDBAdapter(getApplicationContext());
        dbAdapter.open();
        libros = dbAdapter.recuperarLibros();

        //Controlamos que el listado no esté vacío
        if(libros.size()<=0){
            Toast.makeText(getApplicationContext(), "Todavía no has realizado ningún préstamo", Toast.LENGTH_LONG).show();
            finish();
        }
        //Si existen libros prestados se muestra el listado
        else if(libros.size()>0){
            adapest = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, libros);
            lista.setAdapter(adapest);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    //Menú de opciones: controla las acciones de cada item
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //controlamos las selección del actionbar
        switch (id){
            case R.id.inicio:
                Intent is = new Intent(this, MainActivity.class );
                startActivity(is);
                break;
            case R.id.action_settings:
                Intent i = new Intent(this, Datos.class );
                startActivity(i);
                break;
            case R.id.ayuda:
                //Abrir activity de ayuda de la app,
                // como una pequeña guía de uso.

                break;

            case R.id.salir:
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
