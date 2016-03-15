package fct.sreolid.appbiblioteca;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by sreolid on 15/03/2016.
 */
public class AlertaPrestamo extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the message to display.
        builder.setTitle("Préstamo")
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_info))
                .setMessage("Se va a realizar el préstamo")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               // IntentIntegrator.initiateScan(MainActivity.class);
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

        return builder.create();


    }
}