package com.example.mysplash;

import static com.example.mysplash.Registro.archivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity {

    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "Hola mundo";
    private String testDesCifrado;

    public static List<Info> list;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String json = null;
    public static String usuario;
    public static String pass;
    public String mail;
    public String mensaje;


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
        Leer();
        json2List(json);

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
                Entrar(usuario, pass);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Login.this, Olvide.class);
                //startActivity(intent);
                //finish();

                MyDesUtil myDesUtil = null;
                myDesUtil = new MyDesUtil( );
                myDesUtil.addStringKeyBase64(KEY);

                usuario = String.valueOf(user.getText());
                if (usuario.equals("")) {
                    Toast.makeText(getApplicationContext(), "Usuario vacio", Toast.LENGTH_LONG).show();
                }
                else{
                    int i = 0;
                    for(Info info : list){
                        if (info.getUser().equals(usuario)){
                            mail = info.getEmail();
                            mensaje="<html><h1>Registro para una app????</h1></html>";
                            mail =myDesUtil.cifrar(mail);
                            mensaje=myDesUtil.cifrar(mensaje);
                            i= 1;
                        }
                    }
                    if(i==1){
                        Log.i(TAG,usuario);
                        Log.i(TAG,mail);
                        Log.i(TAG,mensaje);
                        if( sendInfo( mail,mensaje ) )
                        {
                            Toast.makeText(getBaseContext() , "Enviado" , Toast.LENGTH_LONG );
                            return;
                        }
                        Toast.makeText(getBaseContext() , "Error" , Toast.LENGTH_LONG );
                    }else{
                        if(i==0){
                            Log.i(TAG,"No existe usuario");
                            Toast.makeText(getBaseContext() , "No Existe" , Toast.LENGTH_LONG );
                            return;
                        }
                    }
                }

            }
        });

    }
    public void Entrar(String usuario, String pass){
        Boolean ingresar = Boolean.FALSE;
        for (Info info : list){
            if(info.getUser().equals(usuario) && info.getContra().equals(pass)){
                Intent intent = new Intent(Login.this, Principal.class);
                intent.putExtra("Info", info);
                startActivity(intent);
                ingresar = Boolean.TRUE;
            }
        }
        if (ingresar==Boolean.FALSE){
            Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }

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

    public void json2List( String json )
    {
        Button button1 = findViewById(R.id.login);
        Button button2 = findViewById(R.id.olvide);

        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            button1.setEnabled(false);
            button2.setEnabled(false);
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<Info>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            button1.setEnabled(false);
            button2.setEnabled(false);
            return;
        }
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
    public boolean sendInfo( String mail ,String mensaje)
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/function-test";
        RequestQueue requestQueue = null;
        if( mail == null || mail.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , mail );
            jsonObject.put("mensaje", mensaje);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TOG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }
}