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

    Bundle datos = this.getIntent().getExtras();
    int idEmpleado = datos.getInt("idEmpleado");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jornada_empleado);

        map = (MapView) findViewById(R.id.mapView);
        txtInfo = (TextView) findViewById(R.id.txtInfo);

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String consulta = "SELECT * FROM "+ Utilidades.tablaEmpleado +" WHERE "+ Utilidades.campoIdEmpl + "=" + idEmpleado;
            Cursor fila = bd.rawQuery(consulta, null);

            // Añadir el información al textview información, y añadir coordenadas al mapa de google.

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No existe ninguna empresa con ese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }


    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

}
