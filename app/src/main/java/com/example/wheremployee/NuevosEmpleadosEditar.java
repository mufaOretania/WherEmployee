package com.example.wheremployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheremployee.utilidades.Utilidades;

public class NuevosEmpleadosEditar extends AppCompatActivity {

    private EditText cajaNombre, cajaDni, cajaTelefono, cajaDireccion, cajaNombreUsuario, cajaContrasena;
    private LinearLayout llEmpleados;
    private TextView txtError;

    long idEmpresa = 0;

    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevos_empleados_editar);

        txtError = (TextView) findViewById(R.id.txtError);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaDni = (EditText) findViewById(R.id.cajaDni);
        cajaTelefono = (EditText) findViewById(R.id.cajaTelefono);
        cajaDireccion = (EditText) findViewById(R.id.cajaDireccion);
        cajaNombreUsuario = (EditText) findViewById(R.id.cajaNombreUsuario);
        cajaContrasena = (EditText) findViewById(R.id.cajaContrasena);

        llEmpleados = (LinearLayout) findViewById(R.id.llEmpleados);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idEmpresa = datos.getLong("idEmpresa");
        }

        SQLiteDatabase bd = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getReadableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        String idEmpresaString = idEmpresa+"";

        try{

            String[] argsEmpl = new String[] {idEmpresaString};
            String[] camposDevueltosEmpl = new String[] {Utilidades.campoNombreEmpl};

            //String consultaEmpl = "SELECT "+ Utilidades.campoIdEmpl+ "," + Utilidades.campoNombreEmpl +" FROM "+ Utilidades.tablaEmpleado +" WHERE "+ Utilidades.campoEmpresa + "=?";
            Cursor filaEmpl = bd.query(Utilidades.tablaEmpleado, camposDevueltosEmpl, Utilidades.campoEmpresa+"=? " , argsEmpl, null, null, null);

            if(filaEmpl.moveToFirst()){
                TextView tvAñadirEmpleados = new TextView(getApplicationContext());
                tvAñadirEmpleados.setText(filaEmpl.getString(0));
                llEmpleados.addView(tvAñadirEmpleados);
                while(filaEmpl.moveToNext()){
                    TextView tvAñadirEmpleados2 = new TextView(getApplicationContext());
                    tvAñadirEmpleados2.setText(filaEmpl.getString(0));
                    llEmpleados.addView(tvAñadirEmpleados2);
                }
            } else {
                Toast.makeText(this, "Comienza a crear empleados.", Toast.LENGTH_SHORT).show();
            }

            filaEmpl.close();
            bd.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error al buscar los empleados de la empresa.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }
        txtError.setText(error);
    }

    public void atras(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
    }

    public void anadirEmpleado(View v) {

        long idEmpleado = 0;
        String nombreEmpleado = null;

        SQLiteDatabase bd = null;
        ContentValues valores = null;

        try{
            ConexionSqlLiteHelper con = new ConexionSqlLiteHelper(this, "bbddWherEmployee", null, 1);
            bd = con.getWritableDatabase();
        } catch(Exception e){
            Toast.makeText(this, "Error al enlazarse con la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        try{

            String nombre = cajaNombre.getText().toString();
            nombreEmpleado = nombre;
            String dni = cajaDni.getText().toString();
            String telefono = cajaTelefono.getText().toString();
            String direccion = cajaDireccion.getText().toString();
            String nombreUsuario = cajaNombreUsuario.getText().toString();
            String contrasena = cajaContrasena.getText().toString();


            if(nombre=="" || dni=="" || telefono=="" || direccion=="" || nombreUsuario=="" || contrasena==""){
                Toast.makeText(this, "Algún campo se encuentra vacío, rellene los campos correctamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
                intent.putExtra("idEmpresa", idEmpresa);
                startActivityForResult(intent, 0);
            } else{
                Toast.makeText(this, "Obteniendo campos.", Toast.LENGTH_SHORT).show();
            }

            if(validarDni(dni)){
                Toast.makeText(this, "Dni válido.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Dni no válido, introduce un dni válido.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
                intent.putExtra("idEmpresa", idEmpresa);
                startActivityForResult(intent, 0);
            }

            if(validarTelefono(telefono)){
                Toast.makeText(this, "Teléfono válido.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Teléfono no válido, introduce un teléfono válido.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
                intent.putExtra("idEmpresa", idEmpresa);
                startActivityForResult(intent, 0);
            }

            valores = new ContentValues();
            valores.put(Utilidades.campoNombreEmpl, nombre);
            valores.put(Utilidades.campoDniEmpl, dni);
            valores.put(Utilidades.campoTelefonoEmpl, telefono);
            valores.put(Utilidades.campoDireccionEmpl, direccion);
            valores.put(Utilidades.campoUsuarioEmpl, nombreUsuario);
            valores.put(Utilidades.campoContrasenaEmpl, contrasena);
            valores.put(Utilidades.campoEmpresa, (int) idEmpresa);

            txtError.setText(error);


        } catch (Exception e){
            Toast.makeText(this, "Error al capturar los datos de los empleados.", Toast.LENGTH_SHORT).show();
            error = error + e;

            Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }


        try{
            idEmpleado = bd.insert(Utilidades.tablaEmpleado, null, valores);
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear al empleado en la base de datos.", Toast.LENGTH_SHORT).show();
            error = error + e;
        }

        cajaNombre.setText("");
        cajaDni.setText("");
        cajaTelefono.setText("");
        cajaDireccion.setText("");
        cajaNombreUsuario.setText("");
        cajaContrasena.setText("");

        if(idEmpleado == -1 || idEmpleado == 0){
            Toast.makeText(this, "Error al crear el empleado.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        } else {
            TextView tvAñadir = new TextView(this);
            tvAñadir.setText(nombreEmpleado);

            llEmpleados.addView(tvAñadir);

            Toast.makeText(this, "Genial, se ha creado su empleado con id: "+ idEmpleado +". Cree más empleados o termina la empresa", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (v.getContext(), NuevosEmpleadosEditar.class);
            intent.putExtra("idEmpresa", idEmpresa);
            startActivityForResult(intent, 0);
        }
        txtError.setText(error);
    }

    public void terminarEmpresa(View v){
        Intent intent = new Intent (v.getContext(), PrincipalJefe.class);
        intent.putExtra("idEmpresa", idEmpresa);
        startActivityForResult(intent, 0);
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
