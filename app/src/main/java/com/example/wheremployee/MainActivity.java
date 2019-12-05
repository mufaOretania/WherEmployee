package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void accederJefe(View v){
        Intent intent = new Intent (v.getContext(), LoginJefe.class);
        startActivityForResult(intent, 0);
    }

    public void accederEmpleado(View v){
        Intent intent = new Intent (v.getContext(), LoginEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void nuevaEmpresa(View v){
        Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
        startActivityForResult(intent, 0);
    }
}
