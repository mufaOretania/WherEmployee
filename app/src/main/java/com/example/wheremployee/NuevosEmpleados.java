package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class NuevosEmpleados extends AppCompatActivity {

    private EditText cajaNombre, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private LinearLayout llEmpleados;
    private TextView txtError;
    long idEmpresa = 0;

    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevos_empleados);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        llEmpleados = (LinearLayout) findViewById(R.id.llEmpleados);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getLong("idEmpresa");
        }
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
        startActivityForResult(intent, 0);
    }

    public void anadirEmpleado(View v){

        long idEmpleado = 0;
        String nombreEmpleado = null;

        ContentValues valores = null;
        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{

            String nombre = cajaNombre.getText().toString();
            nombreEmpleado = nombre;
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaTelefono.getText().toString();
            String nombreUsuario = cajaTelefono.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            valores.put(Utilidades.campoNombreEmpl, nombre);
            valores.put(Utilidades.campoDniEmpl, dni);
            valores.put(Utilidades.campoTelefonoEmpl, telefono);
            valores.put(Utilidades.campoDireccionEmpl, direccion);
            valores.put(Utilidades.campoUsuarioEmpl, nombreUsuario);
            valores.put(Utilidades.campoContrasenaEmpl, contrasena);
            valores.put(Utilidades.campoEmpresa, idEmpresa);

        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los empleados.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
            startActivityForResult(intent, 0);
        }

        try{
            idEmpleado = bd.insert(Utilidades.tablaEmpresa, null, valores);
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear al empleado en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        cajaNombre.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        bd.close();

        if(idEmpleado == -1){
            Toast.makeText(this, "Error al crear el empleado.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
            startActivityForResult(intent, 0);
        } else {
            TextView tvAñadir = new TextView(getApplicationContext());
            tvAñadir.setText(nombreEmpleado);

            llEmpleados.addView(tvAñadir);

            Toast.makeText(this, "Genial, se ha creado su empleado con id: "+ idEmpleado +". Cree más empleados o termina la empresa", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), LoginJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }

    public void terminarEmpresa(View v){

        txtError.setText(error);
        Intent intent = new Intent (v.getContext(), LoginJefe.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }
}
