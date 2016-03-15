package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "My preferences";
    SharedPreferences mySharedPreferences;
    final int PREFERENCIASMOSTRAR_1=1;
    Toolbar toolbar;
    TextView saludo;
    Button prest, dev;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saludo = (TextView) findViewById(R.id.tvSaludo);
        prest = (Button) findViewById(R.id.bPrest);
        dev = (Button) findViewById(R.id.bDev);

        mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
        nombre = mySharedPreferences.getString("nombre", "");

        saludo.setText("Hola "+nombre+", \n ¿qué podemos hacer por ti?");

        //Controlamos la acción al pulsar el botón de préstamo
        prest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hacemos invisible el botón de devolución
                dev.setVisibility(View.INVISIBLE);
                //Mostramos mensaje de alerta
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Confirmación");
                alertDialog.setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_info));
                alertDialog.setMessage("Se va a realizar el préstamo");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         IntentIntegrator.initiateScan(MainActivity.this);
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // IntentIntegrator.initiateScan(MainActivity.class);
                    }
                });

                alertDialog.show();
                //volvemos a hacer visible el botón
            }
        });

        //Controlamos la acción al pulsar el botón de devolución
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hacemos invisible el botón de préstamos
                prest.setVisibility(View.INVISIBLE);

                IntentIntegrator.initiateScan(MainActivity.this);

                //volvemos a hacer visible el botón
            }
        });


    }
    //Marcamos lo que queremos que haga una vez haya leído el código
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE:
            {
                if (resultCode == RESULT_CANCELED){
                }
                else
                {
                    //Controlamos qué botón está invisible y lo hacemos visible
                    if(dev.getVisibility()== View.INVISIBLE){
                        dev.setVisibility(View.VISIBLE);
                    }
                    else if (prest.getVisibility() == View.INVISIBLE){
                        prest.setVisibility(View.VISIBLE);
                    }

                    //Recogemos los datos que nos envió el código Qr/código de barras
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(
                            requestCode, resultCode, data);
                    String UPCScanned = scanResult.getContents();

                    // SACAMOS POR PANTALLA EL TEXTO DEL QR
                    Toast.makeText(getApplicationContext(),UPCScanned,Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(this, Datos.class );
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
