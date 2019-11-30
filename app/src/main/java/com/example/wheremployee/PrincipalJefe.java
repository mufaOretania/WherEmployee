package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class PrincipalJefe extends AppCompatActivity {

    int idEmpresa = 0;

    private TextView tv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_jefe);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getInt("idEmpresa");
        }

        tv = (TextView) findViewById(R.id.tvPortada);

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String idEmpresaString = idEmpresa+"";

            String[] args = new String[] {idEmpresaString};

            String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor fila = bd.rawQuery(consulta, args);

            tv.setText("Empresa " + fila.getString(0));

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
        Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }
}
