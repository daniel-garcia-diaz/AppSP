package com.example.mysplash;

import static com.example.mysplash.Registro.archivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private List<Info> list;
    public static String TAG = "mensaje";
    public static String json = null;
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
                Intent intent = new Intent(Login.this, Olvide.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void Entrar(String usuario, String pass){
        Boolean ingresar = Boolean.FALSE;
        for (Info info : list){
            if(info.getUser().equals(usuario) && info.getContra().equals(pass)){
                Intent intent = new Intent(Login.this, Principal.class);
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
}