package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class LoginEmpleado extends AppCompatActivity {

    private EditText cajaNombreUsuario, cajaContrasena;
    long idEmpleado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);

        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpresa");
        }
    }

    public void atras(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View v){

        SQLiteDatabase bd = null;
        int idEmpleadoLogin = 0;
        String user = null;
        String pass = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        try{
            user = cajaNombreUsuario.getText().toString();
            pass = cajaContrasena.getText().toString();

        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los empleados.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
            startActivityForResult(intent, 0);
        }

        try{

            String[] args = new String[] {user,pass};
            String[] camposDevueltos = new String[] {Utilidades.campoIdEmpl};

            //String consulta = "SELECT * FROM empleado WHERE nombreUsuario=" + user + " and password=" + pass;
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoUsuarioEmpl+"=? and "+ Utilidades.campoContrasenaEmpl+"=?" , args, null, null, null);
            idEmpleadoLogin = fila.getInt(0);

            fila.close();
            bd.close();

            Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleadoLogin);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "No se encontro ningún empleado con ese nombre de usuario y contraseña.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
            startActivityForResult(intent, 0);
        }

    }
}
