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

public class LoginJefe extends AppCompatActivity {

    private EditText cajaNombreUsuario, cajaContrasena;
    private TextView txtError;
    long idEmpresa = 0;

    String error = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_jefe);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        SQLiteDatabase bd = null;

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getLong("idEmpresa");

            try{
                ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
                bd = con.getReadableDatabase();
            } catch(Exception e){
                Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }


            String idEmpresaString = idEmpresa+"";

            String[] args = new String[] {idEmpresaString};
            String[] camposDevueltos = new String[] {Utilidades.campoUsuario, Utilidades.campoContrasena};

            try{
                //String consulta = "SELECT "+ Utilidades.campoUsuario +","+ Utilidades.campoContrasena +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoIdEmpresa+"=?";
                Cursor fila = bd.query(Utilidades.tablaEmpresa, camposDevueltos, Utilidades.campoIdEmpresa+"=? " , args, null, null, null);

                try{
                    if (fila.moveToFirst()){
                        String user = fila.getString(0);
                        String pass = fila.getString(1);

                        cajaNombreUsuario.setText(user);
                        cajaContrasena.setText(pass);
                    }
                }catch(Exception e){
                    Toast.makeText(this, "No se encontró ninguna empresa.", Toast.LENGTH_SHORT).show();
                }

                fila.close();
                bd.close();
            } catch (Exception e) {
                Toast.makeText(this, "Error al consultar el id de la empresa en la base de datos.", Toast.LENGTH_SHORT).show();
                error = error + e;
            }
            txtError.setText(error);
        }

    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View v){

        SQLiteDatabase bd = null;
        long idEmpresaLogin = 0;
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
            Toast.makeText(this, "Error al capturar los datos de la empresa.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), LoginJefe.class);
            startActivityForResult(intent, 0);
        }

        try{

            String[] args = new String[] {user,pass};
            String[] camposDevueltos = new String[] {Utilidades.campoIdEmpresa};

            //String consulta = "SELECT id FROM "+ Utilidades.tablaEmpresa +" WHERE "+Utilidades.campoUsuario+"=? and "+Utilidades.campoContrasena+"=?";
            Cursor fila = bd.query(Utilidades.tablaEmpresa, camposDevueltos, Utilidades.campoUsuario+"=? and "+ Utilidades.campoContrasena+"=?" , args, null, null, null);

            try{
                if (fila.moveToFirst()){
                    idEmpresaLogin = (long) fila.getInt(0);
                    Toast.makeText(this, "Iniciando sesión. Id: " + idEmpresaLogin, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
                    intent.putExtra("idEmpresa", idEmpresaLogin);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(this, "No se encontró ninguna empresa con ese usuario y contraseña.", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Toast.makeText(this, "No se encontró ninguna empresa con ese usuario y contraseña.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), LoginJefe.class);
                startActivityForResult(intent, 0);
            }

            fila.close();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "No se encontró ninguna empresa con ese nombre de usuario y contraseña.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), LoginJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }
}
