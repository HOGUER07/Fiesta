package com.example.rc.fiesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.sql.SQLException;

public class Festejo {

    public static final String ID_FILA = "id";
    public static final String ID_PERSONA = "nombre_persona";
    public static final String ID_CUMPLEAÑOS = "cumpleaños_persona";

    public static final String N_BD = "Festejado";
    public static final String N_TABLA = "Tabla_Festejado";
    public static final int VERSION_BD = 1;

    private BDHelper nHelper;
    private final Context nContexto;
    private SQLiteDatabase nBD;


    public static class BDHelper extends SQLiteOpenHelper {


        public BDHelper(Context context) {
            super(context, N_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + N_TABLA + " (" + ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID_PERSONA + " TEXT NOT NULL, " + ID_CUMPLEAÑOS + " TEXT NOT NULL); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + N_TABLA);
            onCreate(db);
        }
    }

    public Festejo(Context C) {
        nContexto = C;
    }

    public Festejo abrir() throws Exception {
        nHelper = new BDHelper(nContexto);
        nBD = nHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        nHelper.close();
    }

    public long crearEntrada(String nom, String tel) {
        ContentValues cv = new ContentValues();
        cv.put(ID_PERSONA, nom);
        cv.put(ID_CUMPLEAÑOS, tel);
        return nBD.insert(N_TABLA, null, cv);
    }

    public String recibir() {
        String[] columnas = new String[]{ID_FILA, ID_PERSONA, ID_CUMPLEAÑOS};
        Cursor c = nBD.query(N_TABLA, columnas, null, null, null, null, null);
        String resultado = "";

        int iFila = c.getColumnIndex(ID_FILA);
        int iNombre = c.getColumnIndex(ID_PERSONA);
        int iTelefono = c.getColumnIndex(ID_CUMPLEAÑOS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            resultado = resultado + c.getString(iFila) + " " +
                    c.getString(iNombre) + " " + c.getString(iTelefono) + "\n";
        }
        return resultado;
    }

    public String getN(Long lb) {
        String[] columnas = new String[]{ID_FILA, ID_PERSONA, ID_CUMPLEAÑOS};
        Cursor c = nBD.query(N_TABLA, columnas, ID_FILA + "=" + lb, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            String nb = c.getString(1);
            return nb;
        }
        return null;
    }

    public String getT(Long lb) {
        String[] columnas = new String [] {ID_FILA, ID_PERSONA, ID_CUMPLEAÑOS};
        Cursor c = nBD.query(N_TABLA, columnas, ID_FILA + "=" + lb, null, null, null, null );
        if (c != null){
            c.moveToFirst(); String tb = c.getString(2); return tb;
        }
        return null;
    }

    public void editar(long eFilal, String eNom, String eTel)throws SQLException {
        ContentValues cvEditar = new ContentValues();
        cvEditar.put(ID_PERSONA,eNom);
        cvEditar.put(ID_CUMPLEAÑOS,eTel);
        nBD.update(N_TABLA, cvEditar, ID_FILA + "=" + eFilal, null);
    }
    public void borrar(long elFilal) { nBD.delete(N_TABLA, ID_FILA + "=" + elFilal, null);
    }
}
