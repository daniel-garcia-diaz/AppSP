package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysplash.BD.BDUsers;

import java.util.List;

public class Olvide2 extends AppCompatActivity {

    public static String usuario;
    public static List<Info> list;
    public static String cod, nueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide2);

        EditText user = findViewById(R.id.olvuser);
        EditText codigo = findViewById(R.id.olvcod);
        Button establecer = findViewById(R.id.establecer);

        establecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = String.valueOf(user.getText());
                cod = SHA.bytesToHex(SHA.createSha1(String.valueOf(codigo.getText())));
                guardar(usuario, cod);
            }
        });

    }

    public void guardar(String usuario, String cod){
        Boolean ingresar = Boolean.FALSE;
        EditText contra = findViewById(R.id.olvcontra);
        String pass = String.valueOf(contra.getText());

        BDUsers dbUsers = new BDUsers(Olvide2.this);
        Info info = dbUsers.GetUsuario(usuario);
        if(info!=null) {
            if (info.getContra().equals(cod)) {
                nueva = SHA.bytesToHex(SHA.createSha1(pass));
                boolean f = dbUsers.EditUser(usuario, nueva);
                if (f) {
                    Toast.makeText(getApplicationContext(), "Contraseña modificada", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Olvide2.this, Principal.class);
                    startActivity(intent);
                    ingresar = Boolean.TRUE;
                }
                if (ingresar == Boolean.FALSE) {
                    Toast.makeText(getApplicationContext(), "Usuario y/o código incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}