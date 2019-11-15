package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
        SQLiteDatabase bd = con.getWritableDatabase();

        String user = cajaNombreUsuario.getText().toString();
        String pass = cajaContrasena.getText().toString();

        String consulta = "SELECT * FROM empresa WHERE nombreUsuario=" + user + " and password=" + pass;
        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
            Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
            intent.putExtra("idJefe", fila.getString(0));
            startActivityForResult(intent, 0);
        } else
            Toast.makeText(this, "No existe ninguna empresa con ese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        fila.close();
        bd.close();
    }
}
