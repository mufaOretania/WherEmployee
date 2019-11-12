package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalJefe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_jefe);
    }

    public void infoJornadaEmpleado(View v){
        Intent intent = new Intent (v.getContext(), InfoJornadaEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void editarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), EditarEmpresa.class);
        startActivityForResult(intent, 0);
    }
}
