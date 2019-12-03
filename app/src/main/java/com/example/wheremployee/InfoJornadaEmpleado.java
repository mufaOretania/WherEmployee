package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;
import com.google.android.gms.maps.MapView;

public class InfoJornadaEmpleado extends AppCompatActivity {

    private MapView map = null;
    private TextView txtInfo = null;

    int idEmpleado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jornada_empleado);

        map = (MapView) findViewById(R.id.mapView);
        txtInfo = (TextView) findViewById(R.id.txtInfo);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpleado = datos.getInt("idEmpleado");
        }

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            String[] camposDevueltos = new String[] {Utilidades.campoNombreEmpl, Utilidades.campoDniEmpl, Utilidades.campoTelefonoEmpl, Utilidades.campoDireccionEmpl, Utilidades.campoUsuarioEmpl, Utilidades.campoContrasenaEmpl};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoIdEmpl+"=? " , args, null, null, null);

            String[] argsJornada = new String[] {idEmpleadoString};
            String[] camposDevueltosJornada = new String[] {Utilidades.campoIdJor};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor filaJornada = bd.query(Utilidades.tablaEmpleado, camposDevueltosJornada, Utilidades.campoIdEmpl+"=? " , argsJornada, "DESCENDENT", null, null);

            int idJornada = filaJornada.getInt(0);
            String idJornadaString = idJornada+"";

            String cadena = fila.getString(0)+"\n"+fila.getString(1)+"\n"+fila.getString(2)+"\n"+fila.getString(3)+"\n"+fila.getString(4)+"\n"+fila.getString(5);
            txtInfo.setText(cadena);

            String[] argsCoor = new String[] {idEmpleadoString, idJornadaString};
            String[] camposDevueltosCoor = new String[] {Utilidades.campolongitud, Utilidades.campolatitud};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor filaCoor = bd.query(Utilidades.tablaEmpleado, camposDevueltosCoor, Utilidades.campoEmpleadoCoor+"=? and "+ Utilidades.campoJornada+"=?" , argsCoor, null, null, null);

            //Añadir aquí las coordenadas al mapa

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No se encontro información acerca de este empleado.", Toast.LENGTH_SHORT).show();
        }


    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

}
