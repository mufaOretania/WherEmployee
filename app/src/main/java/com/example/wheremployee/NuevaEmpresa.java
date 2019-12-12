package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wheremployee.utilidades.Utilidades;

public class NuevaEmpresa extends AppCompatActivity {

    private EditText cajaNombre, cajaNombrePropietario, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private  TextView txtError;
    long idEmpresa = 0;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_empresa);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaNombrePropietario = (EditText) findViewById(R.id.cajaNombrePorpietario);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void crearEmpresa(View v){

        ContentValues valores = null;
        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }


        try {
            String nombre = cajaNombre.getText().toString();
            String nombrePropietario = cajaNombrePropietario.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            valores = new ContentValues();
            valores.put(Utilidades.campoNombreEmp, nombre);
            valores.put(Utilidades.campoNombreProp, nombrePropietario);
            valores.put(Utilidades.campoDni, dni);
            valores.put(Utilidades.campoTelefono, telefono);
            valores.put(Utilidades.campoDireccion, direccion);
            valores.put(Utilidades.campoUsuario, nombreUsuario);
            valores.put(Utilidades.campoContrasena, contrasena);

            try{
                validarDni(dni);
            }catch (Exception e){
                Toast.makeText(this, "Dni no válido, introduce un dni válido.", Toast.LENGTH_SHORT).show();
                error = error + e;

                Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
                startActivityForResult(intent, 0);
            }

            try{
                validarTelefono(telefono);
            }catch (Exception e){
                Toast.makeText(this, "Teléfono no válido, introduce un teléfono válido.", Toast.LENGTH_SHORT).show();
                error = error + e;

                Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
                startActivityForResult(intent, 0);
            }

            Toast.makeText(this, "BIEN - Datos capturados.", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los campos.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
            startActivityForResult(intent, 0);
        }

        try{
            idEmpresa = bd.insert(Utilidades.tablaEmpresa, null, valores);
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al insertar la empresa en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        cajaNombre.setText("");
        cajaNombrePropietario.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        //txtError.setText(error);

        if(idEmpresa == -1 || idEmpresa == 0){
            Toast.makeText(this, "Error al crear la empresa.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevaEmpresa.class);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "Genial, se ha creado su empresa con id: "+ idEmpresa +".", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevosEmpleados.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }

    }

    public boolean validarDni(String dni){

        boolean result = false;

        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(dni.length() == 9 || Character.isLetter(dni.charAt(8))) {
            result = true;
        }

        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (dni.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(dni) && letraDNI(dni).equals(letraMayuscula)) {
            result = true;
        }

        return result;


    }

    private boolean soloNumeros(String dni) {

        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(i = 0; i < dni.length() - 1; i++) {
            numero = dni.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

    private String letraDNI(String dni) {
        // El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella

        // pasar miNumero a integer
        int miDNI = Integer.parseInt(dni.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }

    private boolean validarTelefono(String tlf) {

        boolean result = true;
        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 9 primeros dígitos
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        if(tlf.length() != 9 ) {
            result = false;
        }

        for(i = 0; i < tlf.length() ; i++) {
            numero = tlf.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(!numero.equals(unoNueve[j])) {
                    result = false;
                }
            }
        }
        return result;
    }
}
