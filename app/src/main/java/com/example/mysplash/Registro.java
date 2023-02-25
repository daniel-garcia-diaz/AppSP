package com.example.mysplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mysplash.BD.BDUsers;
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
    Info info = new Info();
    public static final String archivo = "registro.json";
    private static final String TAG = "Registro";
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);
    String usr = null;
    String mail = null;
    List<Info2> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        lista = new ArrayList<>();
        Info2 info2 = null;

        nomCompleto = findViewById(R.id.EditNombre);
        edad = findViewById(R.id.EditEdad);
        telefono = findViewById(R.id.EditTel);
        email = findViewById(R.id.EditMail);
        contra = findViewById(R.id.EditContra);
        user = findViewById(R.id.EditUser);
        btnRegistrarme = findViewById(R.id.Regist);
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

        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                //info.setGustos(gustes);

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
                //info.setRedes(red);

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

                info.setUser(String.valueOf(user.getText()));
                info.setContra(SHA.bytesToHex(SHA.createSha1(String.valueOf(contra.getText()))));
                info.setNomCompleto(String.valueOf(nomCompleto.getText()));
                info.setEdad(String.valueOf(edad.getText()));
                info.setTelefono(String.valueOf(telefono.getText()));
                info.setEmail(String.valueOf(email.getText()));

                BDUsers bdUsers = new BDUsers(Registro.this);
                long id = bdUsers.saveUser(info);
                if (id > 0){
                    Toast.makeText(Registro.this, "Registro Guardado",Toast.LENGTH_LONG).show();
                    Intent intent4 = new Intent(Registro.this, Login.class);
                    startActivity(intent4);
                }
                else {
                    Toast.makeText(Registro.this, "Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2 ,  menu);
        return flag;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.regresar){
            Intent intent4 = new Intent(Registro.this, Login.class);
            startActivity(intent4);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
