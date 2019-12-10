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

    long idEmpleado = 0;

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

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpleado");
        }

        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        String idEmpleadoString = idEmpleado+"";

        String[] args = new String[] {idEmpleadoString};
        String[] camposDevueltos = new String[] {Utilidades.campoIdEmpl, Utilidades.campoNombreEmpl, Utilidades.campoDniEmpl, Utilidades.campoTelefonoEmpl,Utilidades.campoDireccionEmpl, Utilidades.campoUsuarioEmpl, Utilidades.campoContrasenaEmpl};

        try {
            //String consulta = "SELECT * FROM "+ Utilidades.tablaEmpleado +" WHERE id=" + idEmpleado;
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoIdEmpresa + "=? ", args, null, null, null);

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
            Toast.makeText(this, "Error al consultar los datos del empleado en la base de datos.", Toast.LENGTH_SHORT).show();
        }

    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){

        SQLiteDatabase bd2 = null;
        ContentValues valores = new ContentValues();

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd2 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al actualizar los datos del empleado.", Toast.LENGTH_SHORT).show();
        }

        try{
            String nombre = cajaNombre.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            valores.put(Utilidades.campoNombreEmp, nombre);
            valores.put(Utilidades.campoDni, dni);
            valores.put(Utilidades.campoTelefono, telefono);
            valores.put(Utilidades.campoDireccion, direccion);
            valores.put(Utilidades.campoUsuario, nombreUsuario);
            valores.put(Utilidades.campoContrasena, contrasena);

        } catch (Exception e) {
            Toast.makeText(this, "Error al capturar los datos.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }

        try {

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            int cant = bd2.update(Utilidades.tablaEmpleado, valores, Utilidades.campoIdEmpresa + "=?", args);

            bd2.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos del empleado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error. Imposible actualizar los datos del empleado.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);

        } catch( Exception e) {
            Toast.makeText(this, "Se produjo un error al editar los datos del empleado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }

    }

    public void eliminar(View v){

        SQLiteDatabase bd3 = null;
        ContentValues valores = new ContentValues();

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd3 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al eliminar el empleado.", Toast.LENGTH_SHORT).show();
        }

        try {

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            int cant = bd3.delete(Utilidades.tablaEmpleado, Utilidades.campoIdEmpl + "=?", args);

            bd3.close();

            cajaId.setText("");
            cajaNombre.setText("");
            cajaDni.setText("");
            cajaTelefono.setText("");
            cajaDireccion.setText("");
            cajaNombreUsuario.setText("");
            cajaContrasena.setText("");

            if (cant == 1) {
                Toast.makeText(this, "Se eliminó el empleado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudó eliminar el empleado", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this, "Se produjo un error al eliminar el empleado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }
    }

}
