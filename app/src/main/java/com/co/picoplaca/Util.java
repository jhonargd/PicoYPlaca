package com.co.picoplaca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static File DirApp() {
        File SDCardRoot = Environment.getExternalStorageDirectory(); // Environment.getDataDirectory(); //
        File dirApp = new File(SDCardRoot.getPath() + "/picoplaca");
        if (!dirApp.isDirectory()) dirApp.mkdirs();
        return dirApp;
    }

    public static void MostrarAlertDialog (Context context, String mensaje) {

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick (DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setMessage(mensaje);
        alertDialog.show();
    }

    public static String FechaActual(String format) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

}
