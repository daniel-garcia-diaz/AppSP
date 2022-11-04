package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    private Button btnRegistrarme;
    private Button btnRegresar;
    private EditText nomCompleto;
    private EditText email;
    private EditText telefono;
    private EditText edad;
    private EditText user;
    private EditText contra;
    private RadioButton masculino;
    private RadioButton femenino;
    private RadioButton otro;
    private CheckBox perrito;
    private CheckBox gatito;
    private CheckBox otre;
    private Chip facebook;
    private Chip insta;
    private Chip tiktok;
    private Chip twitter;
    public static final String archivo = "registro.json";
    private static final String TAG = "Registro";

    Info info = null;
    Gson gson = null;
    String json = null;
    String usr = null;
    String mail = null;
    String mensaje = null;
    List<Info> list = new ArrayList<Info>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nomCompleto = findViewById(R.id.EditNombre);
        edad = findViewById(R.id.EditEdad);
        telefono = findViewById(R.id.EditTel);
        email = findViewById(R.id.EditMail);
        contra = findViewById(R.id.EditContra);
        user = findViewById(R.id.EditUser);
        btnRegistrarme = findViewById(R.id.Regist);
        btnRegresar = findViewById(R.id.btnRegresar);
        masculino = findViewById(R.id.Masculino);
        femenino = findViewById(R.id.Femenino);
        otro = findViewById(R.id.Otro);
        perrito = findViewById(R.id.Perrito);
        gatito = findViewById(R.id.Gatito);
        otre = findViewById(R.id.Otre);
        facebook = findViewById(R.id.face);
        insta = findViewById(R.id.insta);
        tiktok = findViewById(R.id.tiktok);
        twitter = findViewById(R.id.twitter);
        Leer();
        json2List(json);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Info info = new Info();
                info.setNomCompleto(String.valueOf(nomCompleto.getText()));
                info.setEdad(String.valueOf(edad.getText()));
                info.setTelefono(String.valueOf(telefono.getText()));
                info.setEmail(String.valueOf(email.getText()));
                info.setContra(SHA.bytesToHex(SHA.createSha1(String.valueOf(contra.getText()))));
                info.setUser(String.valueOf(user.getText()));
                usr = String.valueOf(user.getText());
                mail = String.valueOf(email.getText());

                String[] gustes  = new String[3];
                if (perrito.isChecked()==true){
                    gustes[0]="Perritos";
                }
                else{
                    gustes[0]="Vacio";
                }
                if (gatito.isChecked()==true){
                    gustes[1]="Gatitos";
                }
                else{
                    gustes[1]="Vacio";
                }
                if (otre.isChecked()==true){
                    gustes[2]="Otre";
                }
                else{
                    gustes[2]="Vacio";
                }
                info.setGustos(gustes);

                String[] red = new String[4];
                if(facebook.isChecked()==true){
                    red[0]="Facebook";
                }
                else{
                    red[0]="Vacio";
                }
                if(insta.isChecked()==true){
                    red[1]="Instagram";
                }
                else{
                    red[1]="Vacio";
                }
                if(tiktok.isChecked()==true){
                    red[2]="TikTok";
                }
                else{
                    red[2]="Vacio";
                }
                if(twitter.isChecked()==true){
                    red[3]="Twitter";
                }
                else{
                    red[3]="Vacio";
                }
                info.setRedes(red);

                if (masculino.isChecked()==true){
                    info.setSexo(String.valueOf(masculino.getText()));
                }
                if (femenino.isChecked()==true){
                    info.setSexo(String.valueOf(femenino.getText()));
                }
                if (otro.isChecked()==true){
                    info.setSexo(String.valueOf(otro.getText()));
                }
                if (nomCompleto.getText().length() == 0 ){
                    Toast.makeText( getApplicationContext() , "Campo de nombre vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(edad.getText().length() == 0){
                    Toast.makeText( getApplicationContext() , "Campo de edad vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(telefono.getText().length() == 0){
                    Toast.makeText( getApplicationContext() , "Campo de telefono vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(email.getText().length() == 0){
                    Toast.makeText( getApplicationContext() , "Campo de mail vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(contra.getText().length() == 0){
                    Toast.makeText( getApplicationContext() , "Campo de contraseña vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(user.getText().length() == 0){
                    Toast.makeText( getApplicationContext() , "Campo de usuario vacio" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if (valuser(list, usr)){
                    Toast.makeText( getApplicationContext() , "El nombre de usuraio no está disponibe" , Toast.LENGTH_LONG ).show();
                    return;
                }
                if(!PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches()){
                    Toast.makeText( getApplicationContext() , "Error de sintaxis en el mail" , Toast.LENGTH_LONG ).show();
                    return;
                }

                list2Json(info, list);
            }
        });
    }

    public void json2List( String json )
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<Info>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void object2Json(Info info) {

        Gson gson = null;
        String json = null;
        String mensaje = null;

        gson = new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    public void list2Json(Info info,List<Info> list )
    {
        Gson gson = null;
        String json = null;

        gson = new Gson();
        list.add(info);
        json = gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Registro.this, Login.class);
        startActivity(intent);
    }

    private boolean writeFile(String text)
    {
        File file = null;
        FileOutputStream fileOutputStream = null;
        try
        {
            file = getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean valuser(List<Info>list, String user){
        Boolean u = Boolean.FALSE;
        for (Info info1 : list){
            if(info1.getUser().equals(user)){
                u = Boolean.TRUE;
            }
        }
        return u;
    }

    public boolean Leer(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private File getFile( )
    {
        return new File( getDataDir() , archivo );
    }

    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }

}
