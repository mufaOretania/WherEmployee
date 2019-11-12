package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalEmpleado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_empleado);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void editarEmpleado(View v){
        Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void fichar(View v){
        Intent intent = new Intent (v.getContext(), Fichado.class);
        startActivityForResult(intent, 0);
    }
}
