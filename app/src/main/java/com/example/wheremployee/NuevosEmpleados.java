package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NuevosEmpleados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevos_empleados);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
        startActivityForResult(intent, 0);
    }

    public void anadirEmpleado(View v){

    }

    public void terminarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
}
