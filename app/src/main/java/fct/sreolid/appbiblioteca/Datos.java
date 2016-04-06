package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Datos extends AppCompatActivity {
    public static final String PREFS = "My preferences";
    SharedPreferences mySharedPreferences;

    EditText email, nom, apell, telf, dni;
    TextView m, n, a, t, d;
    Button guarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m = (TextView) findViewById(R.id.tvmail);
        n = (TextView) findViewById(R.id.tvNom);
        a = (TextView) findViewById(R.id.tvApell);
        t = (TextView) findViewById(R.id.tvTelf);
        d = (TextView) findViewById(R.id.tvDni);

        email = (EditText) findViewById(R.id.etMail);
        nom = (EditText) findViewById(R.id.etNom);
        apell = (EditText) findViewById(R.id.etApell);
        telf = (EditText) findViewById(R.id.etTelf);
        dni = (EditText) findViewById(R.id.etDni);

        //Recuperamos las preferencias y las asignamos a las views de la activity
        mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
        email.setText(mySharedPreferences.getString("email", ""));
        nom.setText(mySharedPreferences.getString("nombre", ""));
        apell.setText(mySharedPreferences.getString("apellidos", ""));
        telf.setText(mySharedPreferences.getString("telf", ""));
        dni.setText(mySharedPreferences.getString("dni", ""));

        guarda = (Button) findViewById(R.id.bGuardaDatos);

        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View va) {
                // Creamos  el objeto de preferencias compartidas (datos del usuario)
                mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);

                // Obtenemos un editor para establecer/modificar los datos del usuario
                SharedPreferences.Editor editor = mySharedPreferences.edit();

                // Guardamos nuevos valores
                editor.putString("email", email.getText().toString());
                editor.putString("nombre", nom.getText().toString());
                editor.putString("apellidos", apell.getText().toString());
                editor.putString("telf", telf.getText().toString());
                editor.putString("dni", dni.getText().toString());

                //Controlamos que se han rellenado todos los campos
                if (TextUtils.isEmpty(email.getText())) {
                    email.setError("Campo obligatorio, introduzca email");
                }
                    else if (TextUtils.isEmpty(nom.getText())) {
                        nom.setError("Campo obligatorio, introduzca nombre");
                    }
                        else if (TextUtils.isEmpty(apell.getText())) {
                            apell.setError("Campo obligatorio, introduzca apellidos");
                        }
                            else if (TextUtils.isEmpty(telf.getText()) || telf.length() < 9 || telf.length() > 10) {
                                telf.setError("Campo obligatorio, introduzca teléfono correcto");
                            }
                                else if (TextUtils.isEmpty(dni.getText()) || dni.length() < 9 || dni.length() >= 10) {
                                    dni.setError("Campo obligatorio, introduzca NIF correcto");
                                }
                                    //si no hay errores
                                    else {
                                        // Guardamos los cambios
                                        editor.commit();

                                        //Lanzamos mensaje de guardado con éxito
                                        Toast.makeText(getApplicationContext(), "¡Datos guardados correctamente!",
                                                Toast.LENGTH_SHORT).show();

                                        //Inicializamos pantalla principal y cerramos activity
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);

                                        finish();
                                    }
            }
        });


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
        switch (id) {
            case R.id.inicio:
                Intent is = new Intent(this, MainActivity.class);
                startActivity(is);
                break;

            case R.id.historial:
                Intent in = new Intent(this, Historial.class);
                startActivity(in);
                break;
            case R.id.ayuda:
                //Abrir activity de ayuda de la app

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
