package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PrincipalEmpleado extends AppCompatActivity {

    long idEmpleado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "Cargando la página principal del empleado.", Toast.LENGTH_SHORT).show();


        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpleado");
        } else {
            Toast.makeText(this, "Error al capturar el id del empleado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void editarEmpleado(View v){
        Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
        intent.putExtra("idEmpleado", idEmpleado);
        startActivityForResult(intent, 0);
    }

    public void fichar(View v){
        Intent intent = new Intent (v.getContext(), Fichado.class);
        intent.putExtra("idEmpleado", idEmpleado);
        startActivityForResult(intent, 0);
    }
}
