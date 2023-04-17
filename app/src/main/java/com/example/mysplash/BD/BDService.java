package com.example.mysplash.BD;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BDService extends SQLiteOpenHelper {

    public static final String TAG = "BDService";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "REGISTROS.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_CONTRAS = "t_contraseñas";

    public BDService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT NOT NULL UNIQUE," +
                "contra TEXT NOT NULL," +
                "nombre TEXT," +
                "email TEXT," +
                "tel TEXT," +
                "edad TEXT," +
                "sexo TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_CONTRAS +"(" +
        "id_pass INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pass TEXT NOT NULL," +
                "user TEXT NOT NULL," +
                "long DOUBLE,"+
                "lat DOUBLE,"+
                "id INTEGER NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1 ){
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTRAS);
        onCreate(sqLiteDatabase);

    }
}
