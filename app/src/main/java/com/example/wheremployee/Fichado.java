package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Fichado extends AppCompatActivity {

    private LocationManager locManager;
    private Location loc;

    private Button btnFinalizar;

    long idEmpleado = 0;
    long idJornada = 0;

    String horaInicio = null;
    String horaFin = null;
    double latitud = 0;
    double longitud = 0;
    ArrayList<LatLng> coordenadas = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichado);

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
        }

        try{
            ActivityCompat.requestPermissions(Fichado.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                latitud = 0;
                longitud = 0;
            } else {
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                latitud = loc.getLatitude();
                longitud = loc.getLongitude();
            }
        } catch (Exception e){
            Toast.makeText(this, "Error al capturar las coordenadas actuales.", Toast.LENGTH_SHORT).show();
        }

    }

    public void finalizarJornada(View v){

        try{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            horaFin = sdf.format(c.getTime());
        } catch (Exception e){
            Toast.makeText(this, "Error al capturar la hora y fecha actual.", Toast.LENGTH_SHORT).show();
        }

        ContentValues valores = null;
        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        try{
            valores = new ContentValues();
            valores.put(Utilidades.campoHoraInicio, horaInicio);
            valores.put(Utilidades.campoHoraFin, horaFin);
            valores.put(Utilidades.campoEmpleado, idEmpleado);
        } catch(Exception e){
            Toast.makeText(this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
        }

        try{
            idJornada = bd.insert(Utilidades.tablaJornada, null, valores);
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al insertar la jornada en la base de datos.", Toast.LENGTH_SHORT).show();
        }

        if(idJornada == -1){
            Toast.makeText(this, "Error al crear la jornada.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "Genial, se ha creado su jornada con id: "+ idJornada +".", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }
    }


}
