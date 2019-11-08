package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginEmpleado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);
    }

    public void atras(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }
}
