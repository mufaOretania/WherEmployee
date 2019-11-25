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

public class EditarEmpresa extends AppCompatActivity {

    private EditText cajaId, cajaNombre, cajaNombrePropietario, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;

    Bundle datos = this.getIntent().getExtras();
    int idEmpresa = datos.getInt("idEmpr");

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

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        try{
            String consulta = "SELECT * FROM "+ Utilidades.tablaEmpresa +" WHERE id=" + idEmpresa;
            Cursor fila = bd.rawQuery(consulta, null);

            cajaId.setText(fila.getString(0));
            cajaNombre.setText(fila.getString(1));
            cajaNombrePropietario.setText(fila.getString(2));
            cajaDni.setText(fila.getString(3));
            cajaTelefono.setText(fila.getString(4));
            cajaDireccion.setText(fila.getString(5));
            cajaNombreUsuario.setText(fila.getString(6));
            cajaContrasena.setText(fila.getString(7));

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar la página los datos de la empresa", Toast.LENGTH_SHORT).show();

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
            String nombrePropietario = cajaNombrePropietario.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            ContentValues valores = new ContentValues();
            valores.put(Utilidades.campoNombreEmp, nombre);
            valores.put(Utilidades.campoNombreProp, nombrePropietario);
            valores.put(Utilidades.campoDni, dni);
            valores.put(Utilidades.campoTelefono, telefono);
            valores.put(Utilidades.campoDireccion, direccion);
            valores.put(Utilidades.campoUsuario, nombreUsuario);
            valores.put(Utilidades.campoContrasena, contrasena);

            int cant = bd.update(Utilidades.tablaEmpresa, valores, " " + Utilidades.campoIdEmpresa + "=" + id, null);

            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos de la empresa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error. Imposible actualizar los datos de la empresa.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            startActivityForResult(intent, 0);

        } catch( Exception e) {
            Toast.makeText(this, "Se produjo un error al editar los datos de la empresa", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            startActivityForResult(intent, 0);
        }
    }

    public void eliminar(View v){

        try {

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
            } else {
                Toast.makeText(this, "No se pudó eliminar la empresa", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this, "Se produjo un error al eliminar la empresa", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            startActivityForResult(intent, 0);
        }
    }
}
