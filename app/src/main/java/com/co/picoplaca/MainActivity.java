package com.co.picoplaca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.co.BusinessObject.ConfigBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText txtPlaca;
    TextView txtCon, txtNum;

    String fechaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtPlaca = (EditText) findViewById(R.id.txtPlaca);
        txtCon = (TextView) findViewById(R.id.txtCon);
        txtNum = (TextView) findViewById(R.id.txtNum);

        permisosApp();

    }

    private void crarConfiguracion(int validacion) {
        if (!ConfigBO.ExisteDataBase()) {
            if(ConfigBO.CrearConfigDB()){
                ConfigBO.ingresarRegistro(validacion,fechaActual,txtPlaca.getText().toString());
                txtNum.setText(ConfigBO.obtenerNumeroContravencion(txtPlaca.getText().toString()));
            }
        }else{
            ConfigBO.ingresarRegistro(validacion,fechaActual,txtPlaca.getText().toString());
            txtNum.setText(ConfigBO.obtenerNumeroContravencion(txtPlaca.getText().toString()));
        }

    }

    private boolean permisosApp() {
        int PERMISSION_ALL = 1;
        boolean estado = false;

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            estado = false;
        }else{
            estado = true;
        }

        return estado;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void onClickConsultar(View view) {

        String placa = txtPlaca.getText().toString();
        if(validarCadenaPlaca()){
            try {
                if(validarPicoYPlaca()){
                    crarConfiguracion(1);
                    txtCon.setText("NO");
                }else{
                    if(validarDia()){
                        crarConfiguracion(1);
                        txtCon.setText("NO");
                    }else{
                        txtCon.setText("SI");
                        crarConfiguracion(0);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            Util.MostrarAlertDialog(MainActivity.this, "Ingrese Una Placa Valida");
        }



    }

    private boolean validarDia() {

        boolean valido = true;
        String num = txtPlaca.getText().toString().substring(txtPlaca.getText().length()-1,txtPlaca.getText().length());
        int numero = Integer.parseInt(num);
        String dia = Util.FechaActual("EEE");

        if(dia.equalsIgnoreCase("lun.")){
            if(numero == 1 || numero == 2){
                return valido = false;
            }
        }

        if(dia.equalsIgnoreCase("mar.")){
            if(numero == 3 || numero == 4){
                return valido = false;
            }
        }

        if(dia.equalsIgnoreCase("mie.")){
            if(numero == 5 || numero == 6){
                return valido = false;
            }
        }

        if(dia.equalsIgnoreCase("jue.")){
            if(numero == 7 || numero == 8){
                return valido = false;
            }
        }

        if(dia.equalsIgnoreCase("vie.")){
            if(numero == 9 || numero == 0){
                return valido = false;
            }
        }

        return valido;
    }

    private boolean validarPicoYPlaca() throws ParseException {
        boolean picoYPlaca = true;

        fechaActual = Util.FechaActual("yyyy-MM-dd HH:mm");
        String dia = Util.FechaActual("yyyy-MM-dd ");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date1 = sdf.parse(dia+" 07:00");
        Date date2 = sdf.parse(dia+" 09:30");

        Date date3 = sdf.parse(dia+" 16:00");
        Date date4 = sdf.parse(dia+" 19:30");

        Date dateActual = sdf.parse(fechaActual);

        System.out.println("date1 : " + sdf.format(date1));
        System.out.println("date2 : " + sdf.format(date2));

        if (dateActual.after(date1) || dateActual.equals(date1)) {
            if (dateActual.before(date2) || dateActual.equals(date2)) {
                return picoYPlaca = false;
            }
        }

        if (dateActual.after(date3) || dateActual.equals(date3)) {
            if (dateActual.before(date4) || dateActual.equals(date4)) {
                return picoYPlaca = false;
            }
        }

        return picoYPlaca;

    }

    private boolean validarCadenaPlaca() {

        boolean placaValida = true;
        if(Pattern.compile("(([a-zA-Z]{4})([0-9]{3}))+").matcher(txtPlaca.getText().toString()).matches()){
            placaValida = false;
        }
        if(txtPlaca.getText().toString().length() != 7){
            placaValida = false;
        }

        return placaValida;
    }

    public void onClickRegistros(View view) {

        Intent intent = new Intent(MainActivity.this, FormHistorico.class);
        intent.putExtra("placa", txtPlaca.getText().toString());
        startActivity(intent);

    }
}
