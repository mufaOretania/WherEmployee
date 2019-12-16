package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class EditarEmpleado extends AppCompatActivity {

    private EditText cajaId, cajaNombre, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private TextView txtError;

    long idEmpleado = 0;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaId = (EditText) findViewById(R.id.cajaId);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            idEmpleado = datos.getLong("idEmpleado");
        }

        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        String idEmpleadoString = idEmpleado+"";

        String[] args = new String[] {idEmpleadoString};
        String[] camposDevueltos = new String[] {Utilidades.campoIdEmpl, Utilidades.campoNombreEmpl, Utilidades.campoDniEmpl, Utilidades.campoTelefonoEmpl,Utilidades.campoDireccionEmpl, Utilidades.campoUsuarioEmpl, Utilidades.campoContrasenaEmpl};

        try {
            //String consulta = "SELECT * FROM "+ Utilidades.tablaEmpleado +" WHERE id=" + idEmpleado;
            Cursor fila = bd.query(Utilidades.tablaEmpleado, camposDevueltos, Utilidades.campoIdEmpresa + "=? ", args, null, null, null);

            try{
                if (fila.moveToFirst()){
                    cajaId.setText(fila.getString(0));
                    cajaNombre.setText(fila.getString(1));
                    cajaDni.setText(fila.getString(2));
                    cajaTelefono.setText(fila.getString(3));
                    cajaDireccion.setText(fila.getString(4));
                    cajaNombreUsuario.setText(fila.getString(5));
                    cajaContrasena.setText(fila.getString(6));
                }
            }catch(Exception e){
                Toast.makeText(this, "No se pudo recuperar los datos de la empresa .", Toast.LENGTH_SHORT).show();
            }

            fila.close();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar los datos del empleado en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

    }

    public void cancelar(View v){
        Intent intent = new Intent (v.getContext(), PrincipalEmpleado.class);
        startActivityForResult(intent, 0);
    }

    public void guardar(View v){

        SQLiteDatabase bd2 = null;
        ContentValues valores = new ContentValues();

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd2 = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al actualizar los datos del empleado.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{
            String nombre = cajaNombre.getText().toString();
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();

            if(nombre=="" || dni=="" || telefono=="" || direccion=="" || nombreUsuario=="" || contrasena==""){
                Toast.makeText(this, "Algún campo se encuentra vacío, rellene los campos correctamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            } else{
                Toast.makeText(this, "Obteniendo campos.", Toast.LENGTH_SHORT).show();
            }

            if(validarDni(dni)){
                Toast.makeText(this, "Dni válido.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Dni no válido, introduce un dni válido.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            }

            if(validarTelefono(telefono)){
                Toast.makeText(this, "Teléfono válido.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Teléfono no válido, introduce un teléfono válido.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
                intent.putExtra("idEmpLEADO", idEmpleado);
                startActivityForResult(intent, 0);
            }

            valores.put(Utilidades.campoNombreEmpl, nombre);
            valores.put(Utilidades.campoDniEmpl, dni);
            valores.put(Utilidades.campoTelefonoEmpl, telefono);
            valores.put(Utilidades.campoDireccionEmpl, direccion);
            valores.put(Utilidades.campoUsuarioEmpl, nombreUsuario);
            valores.put(Utilidades.campoContrasenaEmpl, contrasena);

        } catch (Exception e) {
            Toast.makeText(this, "Error al capturar los datos.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), EditarEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }

        try {

            String idEmpleadoString = idEmpleado+"";
            String[] args = new String[] {idEmpleadoString};
            int cant = bd2.update(Utilidades.tablaEmpleado, valores, Utilidades.campoIdEmpl + "=?", args);

            bd2.close();

            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos del empleado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), PrincipalEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(this, "Error. Imposible actualizar los datos del empleado.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), EditarEmpleado.class);
                intent.putExtra("idEmpleado", idEmpleado);
                startActivityForResult(intent, 0);
            }



        } catch( Exception e) {
            Toast.makeText(this, "Se produjo un error al editar los datos del empleado", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent(v.getContext(), EditarEmpleado.class);
            intent.putExtra("idEmpleado", idEmpleado);
            startActivityForResult(intent, 0);
        }

        txtError.setText(error);
    }

    public boolean validarDni(String dni){

        boolean result = true;

        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
            result = false;
        }

        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (dni.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(!soloNumeros(dni) && !letraDNI(dni).equals(letraMayuscula)) {
            result = false;
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

        return miDNI.length() == 8;
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
