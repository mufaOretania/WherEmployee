package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarEmpresa extends AppCompatActivity {

    private EditText cajaId, cajaNombre, cajaNombrePropietario, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);

        cajaId = (EditText) findViewById(R.id.cajaId);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaNombrePropietario = (EditText) findViewById(R.id.cajaNombrePorpietario);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);
    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        String id = cajaId.getText().toString();
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
        int cant = bd.update("empresa", registro, "id=" + id, null);

        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modificaron los datos de la empresa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error. Imposible actualizar los datos de la empresa.", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

    public void eliminar(View v){

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        String id = cajaId.getText().toString();

        int cant = bd.delete("empresa", "id=" + id, null);

        bd.close();

        cajaId.setText("");
        cajaNombre.setText("");
        cajaNombrePropietario.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Se eliminó la empresa correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No se pudó eliminar la empresa", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }
}
