package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class InfoJornadaEmpleado extends AppCompatActivity {

    private TextView txtInfo;
    private LinearLayout llMapa;
    private GoogleMap mapa;

    ArrayList<LatLng> coordenadas = new ArrayList<LatLng>();
    int idEmpleado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jornada_empleado);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        llMapa = (LinearLayout) findViewById(R.id.llMapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        assert mapFragment != null;
        onMapReady(mapa);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpleado = datos.getInt("idEmpleado");
        }

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "datos", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            String[] camposDevueltos = new String[] {Utilidades.campoNombreEmpl, Utilidades.campoDniEmpl, Utilidades.campoTelefonoEmpl, Utilidades.campoDireccionEmpl, Utilidades.campoUsuarioEmpl, Utilidades.campoContrasenaEmpl};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoIdEmpl+"=? " , args, null, null, null);

            String[] argsJornada = new String[] {idEmpleadoString};
            String[] camposDevueltosJornada = new String[] {Utilidades.campoIdJor};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor filaJornada = bd.query(Utilidades.tablaJornada, camposDevueltosJornada, Utilidades.campoIdEmpl+"=? " , argsJornada, "DESCENDENT", null, null);

            int idJornada = filaJornada.getInt(0);
            String idJornadaString = idJornada+"";

            String cadena = fila.getString(0)+"\n"+fila.getString(1)+"\n"+fila.getString(2)+"\n"+fila.getString(3)+"\n"+fila.getString(4)+"\n"+fila.getString(5);
            txtInfo.setText(cadena);

            String[] argsCoor = new String[] {idEmpleadoString, idJornadaString};
            String[] camposDevueltosCoor = new String[] {Utilidades.campolongitud, Utilidades.campolatitud};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor filaCoor = bd.query(Utilidades.tablaCoordenada, camposDevueltosCoor, Utilidades.campoEmpleadoCoor+"=? and "+ Utilidades.campoJornada+"=?" , argsCoor, null, null, null);

            //Añadir aquí las coordenadas al map

            if(filaCoor.moveToFirst()){
                LatLng latlng = new LatLng(filaCoor.getFloat(0),filaCoor.getFloat(1));
                coordenadas.add(latlng);

                coordenadas.add(latlng);
                while(filaCoor.moveToNext()){
                    LatLng latlng2 = new LatLng(filaCoor.getFloat(0),filaCoor.getFloat(1));
                    coordenadas.add(latlng2);
                }
            } else {
                Toast.makeText(this, "No se encontraron coordenadas para este empleado", Toast.LENGTH_SHORT).show();
            }


            fila.close();
            filaJornada.close();
            filaCoor.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No se encontro información acerca de este empleado.", Toast.LENGTH_SHORT).show();
        }


    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

    public void onMapReady(GoogleMap map) {
        if (coordenadas.isEmpty()){
            Toast.makeText(this, "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
        } else {
            for(int i=0; i<coordenadas.size(); i++){
                MarkerOptions mo = new MarkerOptions().position(coordenadas.get(i));
                mapa.addMarker(mo);
            }
        }
        mapa = map;
    }
}
