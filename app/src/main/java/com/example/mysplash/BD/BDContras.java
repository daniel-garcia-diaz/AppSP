package com.example.mysplash.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mysplash.Info;
import com.example.mysplash.Info2;

import java.util.ArrayList;
import java.util.List;

public class BDContras extends BDService{

    Context context;
    public BDContras(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public long savePass(Info2 info2){
        long id = 0;
        try{
            BDService bdService = new BDService(context);
            SQLiteDatabase sqLiteDatabase = bdService.getWritableDatabase();

            ContentValues values= new ContentValues();
            id = sqLiteDatabase.insert(TABLE_CONTRAS,null, UserContract.InfoEntry.toContentValues(info2));

        }catch(Exception ex){
            ex.toString();
        }
        finally{
            return id;
        }

    }
    public List<Info2> getPass(int id )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<Info2> contras = null;
        Info2 info2 = null;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM " + TABLE_CONTRAS +" WHERE id = "+ id,null);
        if( cursor == null )
        {
            return new ArrayList<Info2>();
        }
        if( cursor.getCount() < 1)
        {
            return new ArrayList<Info2>();
        }
        if( !cursor.moveToFirst() )
        {
            return new ArrayList<Info2>();
        }
        Log.d(TAG, "" + cursor.getCount());
        contras = new ArrayList<Info2>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            info2 = new Info2( );
            info2.setId_pass(cursor.getInt(0));
            info2.setContraContra(cursor.getString(1));
            info2.setUsuarioContra(cursor.getString(2));
            info2.setId_user(cursor.getInt(3));
            contras.add(info2);
            cursor.moveToNext( );
        }
        Log.d("Contraseñas",contras.toString());
        return contras;
    }

    public boolean EditarContra (String usuar,String contra,int id,int id_contra){

        boolean correcto = false;
        BDService bdService = new BDService(context);
        SQLiteDatabase sqLiteDatabase = bdService.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("contra", contra);
        values.put("user", usuar);

        try{
            sqLiteDatabase.execSQL("UPDATE " + TABLE_CONTRAS + " SET pass = '" + contra + "', user = '" +usuar+ "' WHERE id= '" + id + "' AND id_pass = '" +id_contra+ "'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
            correcto=false;
        } finally {
            sqLiteDatabase.close();
        }
        return correcto;
    }

    public boolean eliminarContra(int id,String usuar,String contra) {

        boolean correcto = false;

        BDService dbHelper = new BDService(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        try {
            sqLiteDatabase.execSQL("DELETE FROM " + TABLE_CONTRAS + " WHERE id = '" + id + "' AND pass ='" +contra+ "' AND user = '" +usuar+ "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            sqLiteDatabase.close();
        }

        return correcto;
    }
}
