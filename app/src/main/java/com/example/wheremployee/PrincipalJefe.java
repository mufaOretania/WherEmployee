package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class PrincipalJefe extends AppCompatActivity {

    long idEmpresa = 0;
    long idEmpleado = 0;

    private TextView tvPortada;
    private LinearLayout ll;
    private TextView txtError;

    String error = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_jefe);

        txtError = (TextView) findViewById(R.id.txtError);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getLong("idEmpresa");
        }

        tvPortada = (TextView) findViewById(R.id.tvPortada);
        ll = (LinearLayout) findViewById(R.id.llEmpleados);

        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_datos", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        String idEmpresaString = idEmpresa+"";

        try{
            String[] args = new String[] {idEmpresaString};
            String[] camposDevueltos = new String[] {Utilidades.campoNombreEmp};

            //String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor fila = bd.query(Utilidades.tablaEmpresa, camposDevueltos, Utilidades.campoIdEmpresa+"=? " , args, null, null, null);

            try{
                if (fila.moveToFirst()){
                    tvPortada.setText(fila.getString(0));
                }
            }catch(Exception e){
                Toast.makeText(this, "No se pudo escribir el nombre de la empresa.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar la página.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (this, LoginJefe.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }

        try{

            String[] argsEmpl = new String[] {idEmpresaString};
            String[] camposDevueltosEmpl = new String[] {Utilidades.campoIdEmpl, Utilidades.campoNombreEmpl};

            //String consultaEmpl = "SELECT "+ Utilidades.campoIdEmpl+ "," + Utilidades.campoNombreEmpl +" FROM "+ Utilidades.tablaEmpleado +" WHERE "+ Utilidades.campoEmpresa + "=?";
            Cursor filaEmpl = bd.query(Utilidades.tablaEmpleado, camposDevueltosEmpl, Utilidades.campoEmpresa+"=? " , argsEmpl, null, null, null);

            if(filaEmpl.moveToFirst()){
                TextView tvAñadirEmpleados = new TextView(getApplicationContext());
                idEmpleado = filaEmpl.getInt(0);
                tvAñadirEmpleados.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), InfoJornadaEmpleado.class);
                        intent.putExtra("idEmpleado", idEmpleado);
                        startActivityForResult(intent, 0);
                    }
                });
                tvAñadirEmpleados.setText(filaEmpl.getString(1));

                ll.addView(tvAñadirEmpleados);
                while(filaEmpl.moveToNext()){
                    TextView tvAñadirEmpleados2 = new TextView(getApplicationContext());
                    idEmpleado = filaEmpl.getInt(0);
                    tvAñadirEmpleados2.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent (v.getContext(), InfoJornadaEmpleado.class);
                            intent.putExtra("idEmpleado", idEmpleado);
                            intent.putExtra("idEmpresa", idEmpresa);
                            startActivityForResult(intent, 0);
                        }
                    });
                    tvAñadirEmpleados.setText(filaEmpl.getString(1));

                    ll.addView(tvAñadirEmpleados);
                }
            } else {
                Toast.makeText(this, "No se encontró ningún empleado de esta empresa.", Toast.LENGTH_SHORT).show();
            }

            filaEmpl.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error al buscar los empleados de la empresa.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }
        txtError.setText(error);
    }

    public void editarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }

    public void cerrarSesion(View v){
        Intent intent = new Intent (v.getContext(), LoginJefe.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }
}
