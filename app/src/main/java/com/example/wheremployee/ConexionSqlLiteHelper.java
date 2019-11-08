package com.example.wheremployee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSqlLiteHelper extends SQLiteOpenHelper {

    public ConexionSqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTablaEmpresa = "CREATE TABLE empresa (idEmp INTEGER, nombreEmp TEXT, nombrePropietario TEXT, dniPropietario TEXT, telefono TEXT, direccion TEXT, usuarioJefe TEXT, contraseña TEXT)";
        db.execSQL(crearTablaEmpresa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS empresa");
        onCreate(db);
    }
}
