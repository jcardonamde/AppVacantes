package co.edu.upb.android.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import co.edu.upb.android.modelo.Estudiante;

public class EstudianteCAD {
    public static boolean guardarEstudiante(Estudiante e, Context c){
        // llamando a la base de datos en modo escritura
        try {
            Datos dbHelper = new Datos(c);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // configurar los valores a guardar
            ContentValues values = new ContentValues();
            values.put(Datos.EST_APELLIDO, e.getApellido());
            values.put(Datos.EST_NOMBRE, e.getNombre());
            values.put(Datos.EST_CORREO, e.getCorreo());
            values.put(Datos.EST_EDAD, e.getEdad());
            values.put(Datos.EST_DIRECCION, e.getDireccion());
            values.put(Datos.EST_TELEFONO, e.getTelefono());
            values.put(Datos.EST_CLAVE, e.getClave());

            long id = db.insert(Datos.TABLA_ESTUDIANTES, null, values);
            return (id > 0);
        }catch (Exception ex){
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static boolean actualizarEstudiante(Estudiante e, Context c){
        // llamando a la base de datos en modo escritura
        try {
            Datos dbHelper = new Datos(c);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // configurar los valores a guardar
            ContentValues values = new ContentValues();
            values.put(Datos.EST_APELLIDO, e.getApellido());
            values.put(Datos.EST_NOMBRE, e.getNombre());
            //values.put(Datos.EST_CORREO, e.getCorreo());
            values.put(Datos.EST_EDAD, e.getEdad());
            values.put(Datos.EST_DIRECCION, e.getDireccion());
            values.put(Datos.EST_TELEFONO, e.getTelefono());
            values.put(Datos.EST_CLAVE, e.getClave());

            String[] args = new String []{ e.getCorreo()};
            int id=db.update(Datos.TABLA_ESTUDIANTES, values, "correo=?", args);
            return (id > 0);
        }catch (Exception ex){
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static Estudiante consultarEstudiante(String correo, Context c){
        // llamando a la base de datos en modo lectura
        try {
            Datos dbHelper = new Datos(c);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // configurar los valores a guardar
            // Cursor cursor2 = db.query(Datos.TABLA_ESTUDIANTES,null,null,null,null,null,null,null);
            String[] args = new String[] {correo};
            Cursor cursor = db.rawQuery("SELECT * FROM "+Datos.TABLA_ESTUDIANTES+" WHERE "+Datos.EST_CORREO+"=?",args);
            Estudiante es=new Estudiante();
            if(cursor.moveToFirst()){
                int indice = cursor.getColumnIndex(Datos.EST_ID);
                es.setId(Long.parseLong(cursor.getString(indice)));

                indice = cursor.getColumnIndex(Datos.EST_EDAD);
                es.setEdad(Integer.parseInt(cursor.getString(indice)));

                indice = cursor.getColumnIndex(Datos.EST_NOMBRE);
                es.setNombre(cursor.getString(indice));

                indice = cursor.getColumnIndex(Datos.EST_APELLIDO);
                es.setApellido(cursor.getString(indice));

                indice = cursor.getColumnIndex(Datos.EST_DIRECCION);
                es.setDireccion(cursor.getString(indice));

                indice = cursor.getColumnIndex(Datos.EST_TELEFONO);
                es.setTelefono(cursor.getString(indice));

                indice = cursor.getColumnIndex(Datos.EST_CORREO);
                es.setCorreo(cursor.getString(indice));
                return es;
            }
            return null;
        }catch (Exception ex){
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
