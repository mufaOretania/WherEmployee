package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.wheremployee.utilidades.Utilidades;

public class NuevaEmpresa extends AppCompatActivity {

    private EditText cajaNombre, cajaNombrePropietario, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    long idEmpresa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_empresa);

        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaNombrePropietario = (EditText) findViewById(R.id.cajaNombrePorpietario);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void crearEmpresa(View v){

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        ContentValues valores = null;

        try {
            String nombre = cajaNombre.getText().toString();
            String nombrePropietario = cajaNombrePropietario.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaTelefono.getText().toString();
            String nombreUsuario = cajaTelefono.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            valores = new ContentValues();
            valores.put(Utilidades.campoNombreEmp, nombre);
            valores.put(Utilidades.campoNombreProp, nombrePropietario);
            valores.put(Utilidades.campoDni, dni);
            valores.put(Utilidades.campoTelefono, telefono);
            valores.put(Utilidades.campoDireccion, direccion);
            valores.put(Utilidades.campoUsuario, nombreUsuario);
            valores.put(Utilidades.campoContrasena, contrasena);

            String digitos = dni.substring(0, 7);
            String letra = dni.substring(8);

        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los campos.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
            startActivityForResult(intent, 0);
        }

        try{
            idEmpresa = bd.insert(Utilidades.tablaEmpresa, Utilidades.campoIdEmpresa, valores);
        } catch (Exception e) {
            Toast.makeText(this, "Error al insertar la empresa en la base de datos.", Toast.LENGTH_SHORT).show();
        }

        cajaNombre.setText("");
        cajaNombrePropietario.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        bd.close();

        if(idEmpresa == -1){
            Toast.makeText(this, "Error al crear la empresa.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "Genial, se ha creado su empresa con id: "+ idEmpresa +".", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }

    }


}
