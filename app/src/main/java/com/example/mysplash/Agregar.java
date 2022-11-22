package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.mysplash.Registro.archivo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Agregar extends AppCompatActivity {

    public static String TAG = "mensaje";
    private List<Info2> lista;
    Button regiscontra;
    private EditText contra2, red;
    private TextView contrasena, red2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        regiscontra = findViewById(R.id.btnGuardar);
        red = findViewById(R.id.EditUsrContra);
        contra2 = findViewById(R.id.EditContraContra);
        contrasena = findViewById(R.id.EditContraContra);
        red2 = findViewById(R.id.EditUsrContra);
        Intent intent = getIntent();
        Object object = null;
        Info info = null;
        List<Info> list =new ArrayList<Info>();
        regiscontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info2 myData = null;
                Object object = null;
                Info info = null;
                object = intent.getExtras().get("MyInfo");
                info = (Info) object;
                lista = info.getContraseñas();
                myData = new Info2();
                myData.setContraContra(String.valueOf(contrasena.getText()));
                myData.setUsuarioContra(String.valueOf(red.getText()));
                lista.add(myData);
                info.setContraseñas(lista);
                List2Json(info,list);
                Intent intent = new Intent(Agregar.this, Principal.class);
                intent.putExtra("MyInfo", info);
                startActivity(intent);
            }
        });
    }
    public void List2Json(Info info,List<Info> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
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
    private File getFile(){
        return new File(getDataDir(),archivo);
    }

}