package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class NuevosEmpleados extends AppCompatActivity {

    private EditText cajaNombre, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private LinearLayout llEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevos_empleados);

        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        llEmpleados = (LinearLayout) findViewById(R.id.llEmpleados);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
        startActivityForResult(intent, 0);
    }

    public void anadirEmpleado(View v){
        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        String nombre = cajaNombre.getText().toString();
        String dni = cajaDni.getText().toString();
        String telefono = cajaTelefono.getText().toString();
        String direccion = cajaTelefono.getText().toString();
        String nombreUsuario = cajaTelefono.getText().toString();
        String contrasena = cajaContrasena.getText().toString();

        ContentValues valores = new ContentValues();
        valores.put(Utilidades.campoNombreEmpl, nombre);
        valores.put(Utilidades.campoDniEmpl, dni);
        valores.put(Utilidades.campoTelefonoEmpl, telefono);
        valores.put(Utilidades.campoDireccionEmpl, direccion);
        valores.put(Utilidades.campoUsuarioEmpl, nombreUsuario);
        valores.put(Utilidades.campoContrasenaEmpl, contrasena);

        long idResultante = bd.insert(Utilidades.tablaEmpleado, Utilidades.campoIdEmpl, valores);

        cajaNombre.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        Bundle datos = this.getIntent().getExtras();
        int idEmpresa = datos.getInt("idEmpr");



        Toast.makeText(this, "Genial, se ha creado un nuevo empleado con id: "+ idResultante +". Siga creando empleados, o finalice la empresa.", Toast.LENGTH_SHORT).show();

        bd.close();

        llEmpleados.refreshDrawableState();
    }

    public void terminarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
}
