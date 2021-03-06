package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wheremployee.utilidades.Utilidades;

public class LoginEmpleado extends AppCompatActivity {

    private EditText cajaNombreUsuario, cajaContrasena;
    private TextView txtError;
    long idEmpleado = 0;

    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpleado");
        }
    }

    public void atras(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View v) throws InterruptedException {

        SQLiteDatabase bd = null;
        long idEmpleadoLogin = 0;
        String user = null;
        String pass = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{
            user = cajaNombreUsuario.getText().toString();
            pass = cajaContrasena.getText().toString();

        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los empleados.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
            startActivityForResult(intent, 0);
        }

        try{

            String[] args = new String[] {user,pass};
            String[] camposDevueltos = new String[] {Utilidades.campoIdEmpl};

            //String consulta = "SELECT * FROM empleado WHERE nombreUsuario=" + user + " and password=" + pass;
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoUsuarioEmpl+"=? and "+ Utilidades.campoContrasenaEmpl+"=?" , args, null, null, null);

            try{
                if (fila.moveToFirst()){
                    idEmpleadoLogin = (long) fila.getInt(0);
                    Toast.makeText(this, "Iniciando sesión. Id: " + idEmpleadoLogin, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (this, PrincipalEmpleado.class);
                    intent.putExtra("idEmpleado", idEmpleadoLogin);
                    startActivityForResult(intent, 0);
                } else{
                    Toast.makeText(this, "No se encontró ningún empleado con ese usuario y contraseña.", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Toast.makeText(this, "No se encontró ningún empleado con ese usuario y contraseña.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
                startActivityForResult(intent, 0);
            }

            fila.close();
            bd.close();


        } catch (Exception e) {
            Toast.makeText(this, "No se encontro ningún empleado con ese nombre de usuario y contraseña.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }
}
