package co.edu.upb.android.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import co.edu.upb.android.modelo.Estudiante;

public class Datos extends SQLiteOpenHelper {
    public static String TABLA_ESTUDIANTES = "tbl_estudiantes";
    public static String EST_ID = "_id";
    public static String EST_NOMBRE = "nombre";
    public static String EST_APELLIDO = "apellido";
    public static String EST_EDAD = "edad";
    public static String EST_DIRECCION = "direccion";
    public static String EST_TELEFONO = "telefono";
    public static String EST_CORREO = "correo";
    public static String EST_CLAVE = "clave";

    public Datos(@Nullable Context context) {
        super(context, "upb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TABLA_ESTUDIANTES = "CREATE TABLE "+TABLA_ESTUDIANTES+"(" +
                EST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EST_NOMBRE+" TEXT NOT NULL," +
                EST_APELLIDO+" TEXT NOT NULL," +
                EST_EDAD+" INTEGER," +
                EST_DIRECCION+" TEXT," +
                EST_TELEFONO+" TEXT NOT NULL," +
                EST_CORREO+" TEXT NOT NULL," +
                EST_CLAVE+" TEXT NOT NULL" +
                ")";
        db.execSQL(SQL_TABLA_ESTUDIANTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_ESTUDIANTES);
    }
}