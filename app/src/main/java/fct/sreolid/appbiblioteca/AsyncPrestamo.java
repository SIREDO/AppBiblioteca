package fct.sreolid.appbiblioteca;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by sreolid on 21/03/2016.
 */
public class AsyncPrestamo extends AsyncTask{

    MainActivity main;

    @Override
    protected Object doInBackground(Object[] params) {
        IntentIntegrator.initiateScan(main);
        return null;
    }

    protected void onPostExecute(){

    }


}
