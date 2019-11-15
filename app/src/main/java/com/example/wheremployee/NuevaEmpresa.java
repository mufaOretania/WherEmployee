package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void nuevosEmpleados(View v){

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        String nombre = cajaNombre.getText().toString();
        String nombrePropietario = cajaNombrePropietario.getText().toString();
        String dni = cajaDni.getText().toString();
        String telefono = cajaTelefono.getText().toString();
        String direccion = cajaTelefono.getText().toString();
        String nombreUsuario = cajaTelefono.getText().toString();
        String contrasena = cajaContrasena.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombreEmp", nombre);
        registro.put("nombrePropietario", nombrePropietario);
        registro.put("dniPropietario", dni);
        registro.put("telefono", telefono);
        registro.put("direccion", direccion);
        registro.put("usuarioJefe", nombreUsuario);
        registro.put("contrasena", contrasena);
        bd.insert("empresa", null, registro);

        bd.close();

        cajaNombre.setText("");
        cajaNombrePropietario.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        Toast.makeText(this, "Genial, se ha creado su empresa.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
        //intent.putExtra("idEmpresa", fila.getString(0));
        startActivityForResult(intent, 0);
    }
}
