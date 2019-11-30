package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    int idEmpresa = 0;
    int idEmpleado = 0;

    private TextView tv;
    private LinearLayout ll;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_jefe);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getInt("idEmpresa");
        }

        tv = (TextView) findViewById(R.id.tvPortada);
        ll = (LinearLayout) findViewById(R.id.llEmpleados);

        try{

            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bd_wherEmployee", null, 1);
            SQLiteDatabase bd = con.getWritableDatabase();

            String idEmpresaString = idEmpresa+"";

            String[] args = new String[] {idEmpresaString};

            String consulta = "SELECT "+ Utilidades.campoNombreEmp +" FROM "+ Utilidades.tablaEmpresa +" WHERE "+ Utilidades.campoIdEmpresa + "=?";
            Cursor fila = bd.rawQuery(consulta, args);

            String[] argsEmpl = new String[] {idEmpresaString};

            String consultaEmpl = "SELECT "+ Utilidades.campoIdEmpl+ "," + Utilidades.campoNombreEmpl +" FROM "+ Utilidades.tablaEmpleado +" WHERE "+ Utilidades.campoEmpresa + "=?";
            Cursor filaEmpl = bd.rawQuery(consultaEmpl, argsEmpl);

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
                            startActivityForResult(intent, 0);
                        }
                    });
                    tvAñadirEmpleados.setText(filaEmpl.getString(1));

                    ll.addView(tvAñadirEmpleados);
                }
            }

            fila.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "No existe ninguna empresa con ese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    public void editarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }
}
