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

        String nombre = cajaNombre.getText().toString();
        String nombrePropietario = cajaNombrePropietario.getText().toString();
        String dni = cajaDni.getText().toString();
        String telefono = cajaTelefono.getText().toString();
        String direccion = cajaTelefono.getText().toString();
        String nombreUsuario = cajaTelefono.getText().toString();
        String contrasena = cajaContrasena.getText().toString();

        ContentValues valores = new ContentValues();
        valores.put(Utilidades.campoNombreEmp, nombre);
        valores.put(Utilidades.campoNombreProp, nombrePropietario);
        valores.put(Utilidades.campoDni, dni);
        valores.put(Utilidades.campoTelefono, telefono);
        valores.put(Utilidades.campoDireccion, direccion);
        valores.put(Utilidades.campoUsuario, nombreUsuario);
        valores.put(Utilidades.campoContrasena, contrasena);

        try{
            int idResultante = (int) bd.insert(Utilidades.tablaEmpresa, Utilidades.campoIdEmpresa, valores);

            cajaNombre.setText("");
            cajaNombrePropietario.setText("");
            cajaDni.setText("");
            cajaTelefono.setText("");
            cajaDireccion.setText("");
            cajaNombreUsuario.setText("");
            cajaContrasena.setText("");

            Toast.makeText(this, "Genial, se ha creado su empresacon id: "+ idResultante +". Ahora añade a sus empleados.", Toast.LENGTH_SHORT).show();

            bd.close();

            Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
            intent.putExtra("idEmpr", idResultante);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, e+"", Toast.LENGTH_SHORT).show();
        }
    }
}
