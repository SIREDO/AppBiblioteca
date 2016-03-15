package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sreolid on 11/03/2016.
 */
public class SplashScreenActivity extends Activity {

    public static final String PREFS = "My preferences";
    // Constante que establece la duracion del splash de inicio
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecemos la orientación portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Lanzamos la pantalla principal

                //Recuperamos datos, si no existen lanzamos la pantalla de datos de usuario
                SharedPreferences mySharedPreferences = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
                String correo = mySharedPreferences.getString("email", "");

                if (correo==""){
                    Intent datosIntent = new Intent().setClass(SplashScreenActivity.this, Datos.class);
                    startActivity(datosIntent);
                } else if(correo!=""){
                    Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }



                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}