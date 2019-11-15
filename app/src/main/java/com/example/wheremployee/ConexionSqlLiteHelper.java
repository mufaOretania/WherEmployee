package com.example.wheremployee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSqlLiteHelper extends SQLiteOpenHelper {

    ConexionSqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String crearTablaEmpresa = "CREATE TABLE IF NOT EXISTS empresa (" +
                "id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nombreEmp TEXT NOT NULL," +
                "nombrePropietario TEXT NOT NULL," +
                "dniPropietario TEXT NOT NULL UNIQUE," +
                "telefono TEXT NOT NULL," +
                "direccion TEXT NOT NULL," +
                "usuarioJefe TEXT NOT NULL," +
                "contraseña TEXT NOT NULL," +
                "empleados ARRAYLIST)";

        String crearTablaEmpleado = "CREATE TABLE IF NOT EXISTS empleado (" +
                "id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nombreEmp TEXT NOT NULL," +
                "nombrePropietario TEXT NOT NULL," +
                "dniPropietario TEXT NOT NULL UNIQUE," +
                "telefono TEXT NOT NULL," +
                "direccion TEXT NOT NULL," +
                "usuarioJefe TEXT NOT NULL, " +
                "contraseña TEXT NOT NULL, " +
                "jornadas ARRAYLIST)";


        String crearTablaJornada = "CREATE TABLE IF NOT EXISTS jornada (" +
                "id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "horaInicio DATE NOT NULL, " +
                "horaFin DATE NOT NULL, " +
                "coordenadas ARRAYLIST)";

        String crearTablaCoordenada = "CREATE TABLE IF NOT EXISTS coordenada (" +
                "id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "latitud FLOAT NOT NULL, " +
                "longitud FLOAT NOT NULL)";

        db.execSQL(crearTablaEmpresa);
        db.execSQL(crearTablaEmpleado);
        db.execSQL(crearTablaJornada);
        db.execSQL(crearTablaCoordenada);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS empresa");
        db.execSQL("DROP TABLE IF EXISTS empleado");
        db.execSQL("DROP TABLE IF EXISTS jornada");
        db.execSQL("DROP TABLE IF EXISTS coordenada");
        onCreate(db);
    }
}
