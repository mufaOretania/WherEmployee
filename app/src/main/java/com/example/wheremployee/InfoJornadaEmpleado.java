package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.MapView;

public class InfoJornadaEmpleado extends AppCompatActivity {

    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jornada_empleado);

        map = (MapView) findViewById(R.id.mapView);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        startActivityForResult(intent, 0);
    }

}
