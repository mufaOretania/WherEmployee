package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NuevaEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_empresa);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void nuevosEmpleados(View v){
        Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
        startActivityForResult(intent, 0);
    }
}
