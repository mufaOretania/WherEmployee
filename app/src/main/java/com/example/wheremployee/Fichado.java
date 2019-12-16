package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wheremployee.utilidades.Utilidades;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Fichado extends AppCompatActivity {

    private LocationManager locManager;
    private Location loc;

    private TextView txtError;

    private Button btnFinalizar;

    long idEmpleado = 0;
    long idJornada = 0;
    long idCoordenada = 0;

    String error = null;

    String horaInicio = null;
    String horaFin = null;
    double latitud = 0;
    double longitud = 0;
    double latitud2 = 0;
    double longitud2 = 0;

    ArrayList<Long> coordenadas = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichado);

        txtError = (TextView) findViewById(R.id.txtError);

        ActivityCompat.requestPermissions(Fichado.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpleado");
        }

        try{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            horaInicio = sdf.format(c.getTime());
        } catch (Exception e){
            Toast.makeText(this, "Error al capturar la hora y fecha actual.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        //Todo esto se tiene que hacer hasta que el botón finalizar jornada sea activado.
        try{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                latitud = 0;
                longitud = 0;
            } else {
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                latitud = loc.getLatitude();
                longitud = loc.getLongitude();

                ContentValues valores = null;
                SQLiteDatabase bd = null;

                try{
                    ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
                    bd = con.getWritableDatabase();
                } catch(Exception e){
                    Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }

                try{
                    valores = new ContentValues();
                    valores.put(Utilidades.campolatitud, latitud);
                    valores.put(Utilidades.campolongitud, longitud);
                    valores.put(Utilidades.campoEmpleadoCoor, (int) idEmpleado);
                    valores.put(Utilidades.campoJornada, "null");
                } catch(Exception e){
                    Toast.makeText(this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }

                try{
                    idCoordenada = bd.insert(Utilidades.tablaCoordenada, null, valores);
                    bd.close();
                } catch (Exception e) {
                    Toast.makeText(this, "Error al insertar la coordenada en la base de datos.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }

                if(idCoordenada == -1 || idCoordenada == 0){
                    Toast.makeText(this, "Error al crear la coordenada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Genial, se ha creado su coordenada con id: "+ idCoordenada +".", Toast.LENGTH_SHORT).show();
                    coordenadas.add(idCoordenada);
                }
                txtError.setText(error);

            }
        } catch (Exception e){
            Toast.makeText(this, "Error al capturar las coordenadas actuales.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }///////////////////////////////////////////////////

    }

    public void finalizarJornada(View v){

        try{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            horaFin = sdf.format(c.getTime());
        } catch (Exception e){
            Toast.makeText(this, "Error al capturar la hora y fecha actual.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        ContentValues valores = null;
        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{
            valores = new ContentValues();
            valores.put(Utilidades.campoHoraInicio, horaInicio);
            valores.put(Utilidades.campoHoraFin, horaFin);
            valores.put(Utilidades.campoEmpleado, idEmpleado);
        } catch(Exception e){
            Toast.makeText(this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{
            idJornada = bd.insert(Utilidades.tablaJornada, null, valores);
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al insertar la jornada en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        if(idJornada == -1 || idJornada == 0){
            Toast.makeText(this, "Error al crear la jornada.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "Genial, se ha creado su jornada con id: "+ idJornada +".", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);

        //Bucle con todos las coordenadas para actualizar el campo Jornada de cada una de ellas
        for(int i=0; i<coordenadas.size();i++){

            SQLiteDatabase bd2 = null;

            try{
                ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
                bd2 = con.getReadableDatabase();
            } catch(Exception e){
                Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }

            try{

                String[] args = new String[] {coordenadas.get(i)+""};
                String[] camposDevueltos = new String[] {Utilidades.campolatitud, Utilidades.campolongitud};

                //String consulta = "SELECT id FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoUsuario+"=? and "+Utilidades.campoContrasena+"=?";
                Cursor fila = bd2.query(Utilidades.tablaCoordenada, camposDevueltos, Utilidades.campoIdCoor+"=?" , args, null, null, null);

                try{
                    if (fila.moveToFirst()){
                        latitud2 = fila.getDouble(0);
                        longitud2 = fila.getDouble(1);
                    }
                }catch(Exception e){
                    Toast.makeText(this, "No se pudo capturar la latitud ni la longitud.", Toast.LENGTH_SHORT).show();
                }

                fila.close();
                bd.close();
            } catch (Exception e) {
                Toast.makeText(this, "No se pudo capturar ni la latitud ni la longitud..", Toast.LENGTH_SHORT).show();
                error = error + e;

                Intent intent = new Intent (v.getContext(), Fichado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            }
            txtError.setText(error);


            SQLiteDatabase bd3 = null;
            ContentValues valores2 = null;

            try{
                ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
                bd3 = con.getWritableDatabase();
            } catch(Exception e){
                Toast.makeText(this, "Error al actualizar los datos de la empresa.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }

            try{
                valores2 = new ContentValues();
                valores2.put(Utilidades.campolatitud, latitud2);
                valores2.put(Utilidades.campolongitud, longitud2);
                valores2.put(Utilidades.campoEmpleadoCoor, (int) idEmpleado);
                valores2.put(Utilidades.campoJornada, (int) idJornada);

            } catch (Exception e) {
                Toast.makeText(this, "Error al capturar los datos.", Toast.LENGTH_SHORT).show();
                error = error + e;

                Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            }

            try {

                String idStringCoordenada = idCoordenada+"";
                String[] args = new String[] {idStringCoordenada};
                int cant = bd3.update(Utilidades.tablaCoordenada, valores2, " " + Utilidades.campoIdCoor + "=?", args);

                bd2.close();

                if (cant == 1) {
                    Toast.makeText(this, "Se modificaron los datos de la coordenadas correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
                    intent.putExtra("idEmpleado", idEmpleado);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(this, "Error. Imposible actualizar los datos de la coordenadas.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), Fichado.class);
                    intent.putExtra("idEmpleado", idEmpleado);
                    startActivityForResult(intent, 0);
                }

            } catch( Exception e) {
                Toast.makeText(this, "Se produjo un error al editar los datos de la coordenada", Toast.LENGTH_SHORT).show();
                error = error + e;

                Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            }
            txtError.setText(error);
        }

    }


}
