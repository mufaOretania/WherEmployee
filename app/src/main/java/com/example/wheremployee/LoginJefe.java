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



            String consulta = "SELECT * FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoUsuario+"=" + user + " and "+Utilidades.campoContrasena+"=" + pass;
            Cursor fila = bd.rawQuery(consulta, null);

            Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
            intent.putExtra("idJefe", fila.getString(0));
            startActivityForResult(intent, 0);

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No existe ninguna empresa con ese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }

    }
}
