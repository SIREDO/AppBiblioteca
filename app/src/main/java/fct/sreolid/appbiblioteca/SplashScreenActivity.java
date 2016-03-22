package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sreolid on 11/03/2016.
 */
public class SplashScreenActivity extends Activity {
ImageView splog;
    public static final String PREFS = "My preferences";
    // Constante que establece la duracion del splash de inicio
    private static final long SPLASH_SCREEN_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecemos la orientación portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);
        splog= (ImageView) findViewById(R.id.imageView2);

        Animation mov;
        mov = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mover);
        splog.startAnimation(mov);

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

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }


}