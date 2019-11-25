package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class EditarEmpleado extends AppCompatActivity {

    private EditText cajaId, cajaNombre, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;

    Bundle datos = this.getIntent().getExtras();
    int idEmpleado = datos.getInt("idEmpleado");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        cajaId = (EditText) findViewById(R.id.cajaId);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        try{
            String consulta = "SELECT * FROM "+ Utilidades.tablaEmpleado +" WHERE id=" + idEmpleado;
            Cursor fila = bd.rawQuery(consulta, null);

            cajaId.setText(fila.getString(0));
            cajaNombre.setText(fila.getString(1));
            cajaDni.setText(fila.getString(2));
            cajaTelefono.setText(fila.getString(3));
            cajaDireccion.setText(fila.getString(4));
            cajaNombreUsuario.setText(fila.getString(5));
            cajaContrasena.setText(fila.getString(6));

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar la página los datos del empleado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (this, PrincipalJefe.class);
            startActivityForResult(intent, 0);
        }
    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){

        try {

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String id = cajaId.getText().toString();
            String nombre = cajaNombre.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            ContentValues valores = new ContentValues();
            valores.put(Utilidades.campoNombreEmpl, nombre);
            valores.put(Utilidades.campoDniEmpl, dni);
            valores.put(Utilidades.campoTelefonoEmpl, telefono);
            valores.put(Utilidades.campoDireccionEmpl, direccion);
            valores.put(Utilidades.campoUsuarioEmpl, nombreUsuario);
            valores.put(Utilidades.campoContrasenaEmpl, contrasena);

            int cant = bd.update(Utilidades.tablaEmpleado, valores, " " + Utilidades.campoIdEmpl + "=" + id, null);

            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos del empleado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error. Imposible actualizar los datos del empleado.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
            startActivityForResult(intent, 0);

        } catch( Exception e) {
            Toast.makeText(this, "Se produjo un error al editar los datos de la empresa", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            startActivityForResult(intent, 0);
        }
    }

}
