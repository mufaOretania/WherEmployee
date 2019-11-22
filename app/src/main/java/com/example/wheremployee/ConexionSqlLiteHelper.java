package com.example.wheremployee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.wheremployee.utilidades.Utilidades;

public class ConexionSqlLiteHelper extends SQLiteOpenHelper {

    ConexionSqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.crearTablaEmpresa);
        db.execSQL(Utilidades.crearTablaEmpleado);
        db.execSQL(Utilidades.crearTablaJornada);
        db.execSQL(Utilidades.crearTablaCoordenada);
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
