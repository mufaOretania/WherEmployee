package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class PrincipalJefe extends AppCompatActivity {

    Bundle datos = this.getIntent().getExtras();
    int idJefe = datos.getInt("idJefe");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_jefe);

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String consulta = "SELECT * FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=" + idJefe;
            Cursor fila = bd.rawQuery(consulta, null);

            //Añadir empleados al LinearLayout después de obtener los empleados de la tabla empresa.
            //Después crear bucle para añadirle a todos los elementos empleados un evento onClick para lanzar la actividad información del empleado con su id de empleado.

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No existe ninguna empresa con ese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    public void infoJornadaEmpleado(View v){
        Intent intent = new Intent (v.getContext(), InfoJornadaEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void editarEmpresa(View v){
        Bundle datos = this.getIntent().getExtras();
        int idEmpresa = datos.getInt("idEmpr");

        Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
        intent.putExtra("idJefe", idEmpresa);
        startActivityForResult(intent, 0);
    }
}
