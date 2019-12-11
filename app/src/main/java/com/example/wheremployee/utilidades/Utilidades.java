package com.example.wheremployee.utilidades;

public class Utilidades {

    //Valores constantes de la tabla Empresa
    public static final String tablaEmpresa = "empresa";
    public static final String campoIdEmpresa = "id";
    public static final String campoNombreEmp = "nombreEmp";
    public static final String campoNombreProp = "nombrePropietario";
    public static final String campoDni = "dniPropietario";
    public static final String campoTelefono = "telefono";
    public static final String campoDireccion = "direccion";
    public static final String campoUsuario = "usuarioJefe";
    public static final String campoContrasena = "contrasena";

    public static final String crearTablaEmpresa = "CREATE TABLE IF NOT EXISTS "+ tablaEmpresa +" (" +
            ""+campoIdEmpresa+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+campoNombreEmp+" TEXT NOT NULL," +
            ""+campoNombreProp+" TEXT NOT NULL," +
            ""+campoDni+" TEXT NOT NULL," +
            ""+campoTelefono+" TEXT NOT NULL," +
            ""+campoDireccion+" TEXT NOT NULL," +
            ""+campoUsuario+" TEXT NOT NULL," +
            ""+campoContrasena+" TEXT NOT NULL";

    public static final String mufaSL =  "INSERT INTO empresa( id, nombreEmp, nombrePropietario, dniPropietario, telefono, direccion, usuarioJefe, contrasena) VALUES ('1', 'Mufasa SL', 'Mufasa', '26262626X', '666666666', 'Los casas, 8', 'mufa', 'mufa' );";


    //Valores constantes de la tabla Empleado
    public static final String tablaEmpleado = "empleado";
    public static final String campoIdEmpl = "id";
    public static final String campoNombreEmpl = "nombreEmp";
    public static final String campoDniEmpl = "dniEmpl";
    public static final String campoTelefonoEmpl = "telefono";
    public static final String campoDireccionEmpl = "direccion";
    public static final String campoUsuarioEmpl = "usuarioEmpl";
    public static final String campoContrasenaEmpl = "contraseña";
    public static final String campoEmpresa = "empresa";

    public static final String crearTablaEmpleado = "CREATE TABLE IF NOT EXISTS "+ tablaEmpleado +" (" +
            ""+campoIdEmpl+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+campoNombreEmpl+" TEXT NOT NULL," +
            ""+campoDniEmpl+" TEXT NOT NULL," +
            ""+campoTelefonoEmpl+" TEXT NOT NULL," +
            ""+campoDireccionEmpl+" TEXT NOT NULL," +
            ""+campoUsuarioEmpl+" TEXT NOT NULL, " +
            ""+campoContrasenaEmpl+" TEXT NOT NULL, " +
            ""+campoEmpresa+" INTEGER NOT NULL";


    //Valores constantes de la tabla Jornada
    public static final String tablaJornada = "jornada";
    public static final String campoIdJor = "id";
    public static final String campoHoraInicio = "horaInicio";
    public static final String campoHoraFin = "horaFin";
    public static final String campoEmpleado = "empleado";

    public static final String crearTablaJornada = "CREATE TABLE IF NOT EXISTS "+ tablaJornada +" (" +
            ""+campoIdJor+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+campoHoraInicio+" DATE NOT NULL, " +
            ""+campoHoraFin+"  DATE NOT NULL, " +
            ""+campoEmpleado+" INTEGER NOT NULL)";

    //Valores constantes de la tabla Coordenada
    public static final String tablaCoordenada = "coordenada";
    public static final String campoIdCoor = "id";
    public static final String campolatitud = "latitud";
    public static final String campolongitud = "longitud";
    public static final String campoEmpleadoCoor = "empleadoCoor";
    public static final String campoJornada = "jornada";

    public static final String crearTablaCoordenada = "CREATE TABLE IF NOT EXISTS "+tablaCoordenada+" (" +
            ""+campoIdCoor+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+campolatitud+" DOUBLE NOT NULL, " +
            ""+campolongitud+" DOUBLE NOT NULL, " +
            ""+campoEmpleadoCoor+" INTEGER NOT NULL, " +
            ""+campoJornada+" INTEGER NOT NULL)";

}
