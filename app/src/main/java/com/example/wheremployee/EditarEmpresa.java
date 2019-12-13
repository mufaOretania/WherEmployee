package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class EditarEmpresa extends AppCompatActivity {

    private EditText cajaId, cajaNombre, cajaNombrePropietario, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private TextView txtError;

    long idEmpresa = 0;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaId = (EditText) findViewById(R.id.cajaId);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaNombrePropietario = (EditText) findViewById(R.id.cajaNombrePorpietario);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpresa = datos.getLong("idEmpresa");
        }

        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        String idEmpresaString = idEmpresa+"";

        String[] args = new String[] {idEmpresaString};
        String[] camposDevueltos = new String[] {Utilidades.campoIdEmpresa, Utilidades.campoNombreEmp, Utilidades.campoNombreProp, Utilidades.campoDni, Utilidades.campoTelefono,Utilidades.campoDireccion, Utilidades.campoUsuario, Utilidades.campoContrasena};

        try {
            //String consulta = "SELECT * FROM "+ Utilidades.tablaEmpresa +" WHERE id=" + idEmpresa;
            Cursor fila = bd.query(Utilidades.tablaEmpresa, camposDevueltos, Utilidades.campoIdEmpresa + "=? ", args, null, null, null);

            try{
                if (fila.moveToFirst()){
                    cajaId.setText(fila.getString(0));
                    cajaNombre.setText(fila.getString(1));
                    cajaNombrePropietario.setText(fila.getString(2));
                    cajaDni.setText(fila.getString(3));
                    cajaTelefono.setText(fila.getString(4));
                    cajaDireccion.setText(fila.getString(5));
                    cajaNombreUsuario.setText(fila.getString(6));
                    cajaContrasena.setText(fila.getString(7));
                }
            }catch(Exception e){
                Toast.makeText(this, "No se pudo recuperar los datos de la empresa .", Toast.LENGTH_SHORT).show();
            }


            fila.close();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar los datos de la empresa en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){

        SQLiteDatabase bd2 = null;
        ContentValues valores = new ContentValues();

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd2 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al actualizar los datos de la empresa.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{
            String nombre = cajaNombre.getText().toString();
            String nombrePropietario = cajaNombrePropietario.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            valores.put(Utilidades.campoNombreEmp, nombre);
            valores.put(Utilidades.campoNombreProp, nombrePropietario);
            valores.put(Utilidades.campoDni, dni);
            valores.put(Utilidades.campoTelefono, telefono);
            valores.put(Utilidades.campoDireccion, direccion);
            valores.put(Utilidades.campoUsuario, nombreUsuario);
            valores.put(Utilidades.campoContrasena, contrasena);

        } catch (Exception e) {
            Toast.makeText(this, "Error al capturar los datos.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }

        try {

            String idEmpresaString = idEmpresa+"";
            String[] args = new String[] {idEmpresaString};
            int cant = bd2.update(Utilidades.tablaEmpresa, valores, " " + Utilidades.campoIdEmpresa + "=?", args);

            bd2.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos de la empresa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error. Imposible actualizar los datos de la empresa.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);

        } catch( Exception e) {
            Toast.makeText(this, "Se produjo un error al editar los datos de la empresa", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }

    public void eliminar(View v){

        SQLiteDatabase bd3 = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd3 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al cargar la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try {

            String idEmpresaString = idEmpresa+"";
            String[] args = new String[] {idEmpresaString};
            int cant = bd3.delete(Utilidades.tablaEmpresa, " " + Utilidades.campoIdEmpresa + "=?", args);

            bd3.close();

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

                Intent intent = new Intent(v.getContext(), EditarEmpresa.class);
                intent.putExtra("idEmpresa", idEmpresa);
                startActivityForResult(intent, 0);
            }

            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this, "Se produjo un error al eliminar la empresa", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent(v.getContext(), PrincipalJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }
}
