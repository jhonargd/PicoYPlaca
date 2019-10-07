package com.co.BusinessObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.co.model.Registro;
import com.co.picoplaca.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigBO {

    public static String mensaje;

    public static boolean ExisteDataBase() {
        File dbFile = new File(Util.DirApp(), "DataBase.db");
        return dbFile.exists();
    }

    public static boolean CrearConfigDB() {

        SQLiteDatabase db = null;

        try {

            File dbFile = new File(Util.DirApp(), "DataBase.db");
            db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

            String config = "CREATE TABLE IF NOT EXISTS Historico(placa varchar(20), fecha_registro varchar(20), contravencion varchar(20))";
            db.execSQL(config);
            return true;

        } catch (Exception e) {

            mensaje = e.getMessage();
            return false;

        } finally {

            if (db != null)
                db.close();
        }
    }

    public static boolean ingresarRegistro(int validacion, String fechaActual, String placa) {

        SQLiteDatabase db = null;

        try {

            File dbFile = new File(Util.DirApp(), "DataBase.db");

            if (dbFile.exists()) {

                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                ContentValues values = new ContentValues();
                values.put("placa", placa);
                values.put("fecha_registro", fechaActual);
                if(validacion == 0){
                    values.put("contravencion", "SI" );
                }else{
                    values.put("contravencion", "NO");
                }

                long rows = -1;
                rows = db.insertOrThrow("Historico", null, values);

                return rows > 0;

            } else {
                return false;
            }

        } catch (Exception e) {

            mensaje = e.getMessage();
            return false;

        } finally {

            if (db != null)
                db.close();
        }

    }

    public static String obtenerNumeroContravencion(String placa) {

        String total = "0";
        SQLiteDatabase db = null;

        try {

            File dbFile = new File(Util.DirApp(), "DataBase.db");

            if (dbFile.exists()) {

                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String query = "select count (*) as cantidad from Historico where placa like '" + placa +"' and contravencion like 'SI' ";
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst()) {

                    total = cursor.getString(cursor.getColumnIndex("cantidad"));
                }

                if (cursor != null)
                    cursor.close();

                return total;

            } else {
                return total;
            }

        } catch (Exception e) {

            mensaje = e.getMessage();
            return total;

        } finally {

            if (db != null)
                db.close();
        }
    }

    public static ArrayList<Registro> listaRegistros(String placa) {
        String mensaje = "";
        Registro registro;
        SQLiteDatabase db = null;
        ArrayList<Registro> listaRegistros = new ArrayList<>();

        try {
            File dbFile = new File(Util.DirApp(), "DataBase.db");

            if (dbFile.exists()) {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String query = "SELECT placa, contravencion, fecha_registro FROM Historico where placa like '%" + placa + "%'";
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    do {
                        registro = new Registro();
                        registro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                        registro.setContravencion(cursor.getString(cursor.getColumnIndex("contravencion")));
                        registro.setFache(cursor.getString(cursor.getColumnIndex("fecha_registro")));
                        listaRegistros.add(registro);
                    } while (cursor.moveToNext());
                }

                if (cursor != null)
                    cursor.close();

            } else {
                mensaje = "No existe la base de datos.";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally {
            if (db != null)
                db.close();
        }

        return listaRegistros;
    }

}
