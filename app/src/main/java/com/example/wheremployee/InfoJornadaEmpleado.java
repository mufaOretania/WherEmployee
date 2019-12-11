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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class InfoJornadaEmpleado extends AppCompatActivity {

    private TextView txtInfo;
    private LinearLayout llMapa;
    private GoogleMap mapa;

    private TextView txtError;

    ArrayList<LatLng> coordenadas = new ArrayList<LatLng>();
    int idEmpleado = 0;

    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jornada_empleado);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        llMapa = (LinearLayout) findViewById(R.id.llMapa);
        txtError = (TextView) findViewById(R.id.txtError);

        try{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        } catch (Exception e){
            Toast.makeText(this, "Error al mostrar el mapa.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpleado = datos.getInt("idEmpleado");
            String idJornadaString = null;

            SQLiteDatabase bd = null;
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getWritableDatabase();

            try{
                String idEmpleadoString = idEmpleado+"";
                String[] args = new String[] {idEmpleadoString};
                String[] camposDevueltos = new String[] {Utilidades.campoNombreEmpl, Utilidades.campoDniEmpl, Utilidades.campoTelefonoEmpl, Utilidades.campoDireccionEmpl, Utilidades.campoUsuarioEmpl, Utilidades.campoContrasenaEmpl};

                try{
                    //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
                    Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoIdEmpl+"=? " , args, null, null, null);

                    String cadena = fila.getString(0)+"\n"+fila.getString(1)+"\n"+fila.getString(2)+"\n"+fila.getString(3)+"\n"+fila.getString(4)+"\n"+fila.getString(5);
                    txtInfo.setText(cadena);

                    fila.close();

                } catch(Exception e){
                    Toast.makeText(this, "Error al consultar la información del empleado.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }
            } catch (Exception e){
                Toast.makeText(this, "Error al conectarse a la base datos para mostrar la información del empleado.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }

            try{
                String idEmpleadoString = idEmpleado+"";
                String[] argsJornada = new String[] {idEmpleadoString};
                String[] camposDevueltosJornada = new String[] {Utilidades.campoIdJor};
                try{
                    //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
                    Cursor filaJornada = bd.query(Utilidades.tablaJornada, camposDevueltosJornada, Utilidades.campoIdEmpl+"=? " , argsJornada, "DESCENDENT", null, null);

                    int idJornada = filaJornada.getInt(0);
                    idJornadaString = idJornada+"";

                    filaJornada.close();

                } catch(Exception e){
                    Toast.makeText(this, "Error al consultar la información de la jornada.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }
            } catch (Exception e){
                Toast.makeText(this, "Error al conectarse a la base datos para mostrar la información del empleado.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }

            try{
                String idEmpleadoString = idEmpleado+"";
                String[] argsCoor = new String[] {idEmpleadoString, idJornadaString};
                String[] camposDevueltosCoor = new String[] {Utilidades.campolongitud, Utilidades.campolatitud};
                try{
                    //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
                    Cursor filaCoor = bd.query(Utilidades.tablaCoordenada, camposDevueltosCoor, Utilidades.campoEmpleadoCoor+"=? and "+ Utilidades.campoJornada+"=?" , argsCoor, null, null, null);

                    if(filaCoor.moveToFirst()){
                        LatLng latlng = new LatLng(filaCoor.getFloat(0),filaCoor.getFloat(1));
                        coordenadas.add(latlng);

                        coordenadas.add(latlng);
                        while(filaCoor.moveToNext()){
                            LatLng latlng2 = new LatLng(filaCoor.getFloat(0),filaCoor.getFloat(1));
                            coordenadas.add(latlng2);
                        }

                        //Añadir aquí las coordenadas al map///////////////////////////////////////////////////////////////////
                        try{
                            onMapReady(mapa);
                        } catch (Exception e){
                            Toast.makeText(this, "Error al mostrar el mapa. OnMapReady.", Toast.LENGTH_SHORT).show();
                        }


                        filaCoor.close();

                    } else {
                        Toast.makeText(this, "No se encontraron coordenadas para este empleado", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){
                    Toast.makeText(this, "Error al consultar las coordenadas.", Toast.LENGTH_SHORT).show();
                    error = error + e;
                }
            } catch (Exception e){
                Toast.makeText(this, "Error al conectarse a la base datos para mostrar la información del empleado.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }

            bd.close();
            txtError.setText(error);
        }
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

    public void eliminar(View v){

        SQLiteDatabase bd3 = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd3 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al eliminar el empleado.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try {

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            int cant = bd3.delete(Utilidades.tablaEmpleado, Utilidades.campoIdEmpl + "=?", args);

            bd3.close();

            if (cant == 1) {
                Toast.makeText(this, "Se eliminó el empleado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudó eliminar el empleado", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this, "Se produjo un error al eliminar el empleado", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
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
