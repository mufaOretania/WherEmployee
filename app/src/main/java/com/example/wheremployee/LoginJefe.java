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

public class LoginJefe extends AppCompatActivity {

    private EditText cajaNombreUsuario, cajaContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_jefe);

        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        int idEmpresa = 0;

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getInt("idEmpresa");
        }
        
        if(idEmpresa>0){
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String idEmpresaString = idEmpresa+"";

            String[] args = new String[] {idEmpresaString};

            try{
                String consulta = "SELECT "+ Utilidades.campoUsuario +","+ Utilidades.campoContrasena +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoIdEmpresa+"=?";
                Cursor fila = bd.rawQuery(consulta, args);

                String user = fila.getString(0);
                String pass = fila.getString(1);

                cajaNombreUsuario.setText(user);
                cajaContrasena.setText(pass);

                fila.close();
                bd.close();
            } catch (Exception e) {
                Toast.makeText(this, "Error al consultar el id de la empresa en la base de datos.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View v){

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String user = cajaNombreUsuario.getText().toString();
            String pass = cajaContrasena.getText().toString();

            String[] args = new String[] {user,pass};

            int idEmpresaLogin = 0;

            try{
                String consulta = "SELECT id FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoUsuario+"=? and "+Utilidades.campoContrasena+"=?";
                Cursor fila = bd.rawQuery(consulta, args);
                idEmpresaLogin = fila.getInt(0);

                fila.close();
                bd.close();
            } catch (Exception e) {
                Toast.makeText(this, "No se encontro ninguna empresa con ese nombre de usuario y contraseña.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), LoginJefe.class);
                intent.putExtra("idEmpresa", idEmpresaLogin);
                startActivityForResult(intent, 0);
            }

            try{
                Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
                intent.putExtra("idEmpresa", idEmpresaLogin);
                startActivityForResult(intent, 0);
            } catch (Exception e){
                Toast.makeText(this, "No se pudo redireccionar a la pantalla principal de la empresa.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar la página.", Toast.LENGTH_SHORT).show();
        }

    }
}
