package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
    String operacion;
    String UPCScanned;

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

                //Comprobamos el estado de la red
                // si no hay acceso a internet lanzamos menjase al usuario
                if (!verificaConexion(getApplicationContext())) {
                    Toast.makeText(getBaseContext(),
                            "Comprueba tu conexión a Internet.  ", Toast.LENGTH_LONG)
                            .show();

                }
                //si hay acceso a internet continuamos el proceso
                else if(verificaConexion(getApplicationContext())){
                    //inicializamos en escaneo
                    IntentIntegrator.initiateScan(MainActivity.this);
                    operacion= "prestamo";
                }
            }
        });

        //Controlamos la acción al pulsar el botón de devolución
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprobamos el estado de la red
                // si no hay acceso a internet lanzamos menjase al usuario
                if (!verificaConexion(getApplicationContext())) {
                    Toast.makeText(getBaseContext(),
                            "Comprueba tu conexión a Internet.  ", Toast.LENGTH_LONG)
                            .show();

                }
                //si hay acceso a internet continuamos el proceso
                else if(verificaConexion(getApplicationContext())){
                    //inicializamos en escaneo
                    IntentIntegrator.initiateScan(MainActivity.this);
                    operacion= "devolucion";
                }
            }
        });

    }
    //Marcamos lo que queremos que haga una vez haya leído el código
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE:
            {
                if (resultCode == RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                }
                else
                {

                    //Recogemos los datos que nos envió el código Qr/código de barras
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(
                            requestCode, resultCode, data);
                     UPCScanned = scanResult.getContents();
                        if(operacion=="prestamo"){

                            // SACAMOS POR PANTALLA EL TEXTO DEL QR

                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                            LayoutInflater inflater = getLayoutInflater();
                            dialogo1.setView(inflater.inflate(R.layout.dialog_p, null));
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Toast.makeText(getApplicationContext(),"Datos del préstamo: "+UPCScanned,Toast.LENGTH_LONG).show();


                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                   // finish();
                                }
                            });
                            dialogo1.show();

                        }else if(operacion=="devolucion"){
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                            LayoutInflater inflater = getLayoutInflater();
                            dialogo1.setView(inflater.inflate(R.layout.dialog_d, null));
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Toast.makeText(getApplicationContext(), "Datos de la devolución: " + UPCScanned, Toast.LENGTH_LONG).show();
                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                 //   finish();
                                }
                            });
                            dialogo1.show();
                            // SACAMOS POR PANTALLA EL TEXTO DEL QR

                        }

                }
                break;
            }
        }
    }

    //Método que comprueba el estado de la red
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
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

        //controlamos las selección del actionbar
        switch (id){
            case R.id.action_settings:
                Intent i = new Intent(this, Datos.class );
                startActivity(i);
                break;

            case R.id.salir:
                finish();
                break;
        }
        /*if (id == R.id.action_settings) {
            Intent i = new Intent(this, Datos.class );
            startActivity(i);
            //finish();
        }/*/

        return super.onOptionsItemSelected(item);
    }

}
