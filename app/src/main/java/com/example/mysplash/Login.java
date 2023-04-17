package com.example.mysplash;

import static com.example.mysplash.Registro.archivo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysplash.BD.BDUsers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity  {

    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);
    private String testClaro = "Hola mundo";

    public static List<Info> list;
    public static String usuario;
    public static String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button = findViewById(R.id.buttonregistrar);
        Button button1 = findViewById(R.id.login);
        Button button2 = findViewById(R.id.olvide);
        EditText user = findViewById(R.id.TxtUser);
        EditText contra = findViewById(R.id.TxtContra);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = String.valueOf(user.getText());
                pass = SHA.bytesToHex(SHA.createSha1(String.valueOf(contra.getText())));
                //pass = String.valueOf(contra.getText());
                Entrar(usuario, pass);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Olvide.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void Entrar(String usuario, String pass){

        Boolean ingresar = Boolean.FALSE;

        BDUsers dbUsers = new BDUsers(Login.this);
        Info info = dbUsers.GetUsuario(usuario);
        if(info!=null) {
            if (info.getContra().equals(pass)) {
                Intent intent = new Intent(Login.this, Principal.class);
                intent.putExtra("Info", info);
                startActivity(intent);
                ingresar = Boolean.TRUE;
            }
        }
        if (ingresar==Boolean.FALSE) {
            Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

}