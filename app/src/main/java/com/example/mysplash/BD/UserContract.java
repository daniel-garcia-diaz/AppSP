package com.example.mysplash.BD;

import static com.example.mysplash.BD.BDService.TABLE_CONTRAS;
import static com.example.mysplash.BD.BDService.TABLE_USUARIOS;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.mysplash.Info;
import com.example.mysplash.Info2;

import java.io.Serializable;

public class UserContract implements Serializable {

    public static final String TAG = "UserContract";
    public static abstract class UserEntry implements BaseColumns
    {
        public static final String USUARIO = "usuario";

        public static final String getCreateTable( )
        {
            String table = "CREATE TABLE "+ TABLE_USUARIOS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario TEXT NOT NULL UNIQUE," +
                    "contra TEXT NOT NULL," +
                    "nombre TEXT," +
                    "email TEXT," +
                    "tel TEXT," +
                    "edad TEXT," +
                    "sexo TEXT," +
                    ")";
            return table;
        }

        public static ContentValues toContentValues(Info info)
        {
            ContentValues values = new ContentValues();
            values.put("usuario", info.getUser());
            values.put("contra", info.getContra());
            values.put("nombre", info.getNomCompleto());
            values.put("email", info.getEmail());
            values.put("tel", info.getTelefono());
            values.put("edad", info.getEdad());
            values.put("sexo", info.getSexo());
            return values;
        }
    }
    public abstract static class InfoEntry implements BaseColumns{
        public static final String getCreateTable( )
        {
            String table ="CREATE TABLE "+ TABLE_CONTRAS +"(" +
                    "id_pass INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pass TEXT NOT NULL," +
                    "user TEXT NOT NULL," +
                    "id INTEGER NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues(Info2 info2)
        {
            ContentValues values = new ContentValues();
            values.put("pass", info2.getContraContra());
            values.put("user", info2.getUsuarioContra());
            values.put("id", info2.getId_user());

            return values;
        }
    }
}
