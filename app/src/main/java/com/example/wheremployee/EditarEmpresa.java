package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditarEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);
    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void eliminar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }
}
