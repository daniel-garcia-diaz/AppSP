package com.example.mysplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Olvide extends AppCompatActivity {

    public static List<Info> list;
    public static String json = null;
    public static String TAG = "Hola";
    public static String TOG = "Error";
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usuario = null;
    public String correo,msj,pass, nuevapass,nueva2;
    EditText user,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);

        Button btnRecupera = findViewById(R.id.btnRecupera);
        user = findViewById(R.id.editRUser);
        mail = findViewById(R.id.editRMail);
        list = Login.list;


        btnRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = String.valueOf(user.getText());
                correo = String.valueOf(mail.getText());
                if(usuario.length()==0 || correo.length()==0){
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                }else {
                    int i = 0;
                    int j = 0;
                    for (Info info : list) {
                        if (info.getUser().equals(usuario) || info.getEmail().equals(correo)) {
                            correo = info.getEmail();
                            pass = info.getContra();
                            nuevapass = String.format("%d", (int) (Math.random() * 1000));
                            nueva2 = SHA.bytesToHex(SHA.createSha1(nuevapass));
                            msj = "<!DOCTYPE html>\n" +
                                    "<html lang=\"en\">\n" +
                                    "\n" +
                                    "<head>\n" +
                                    "    <meta charset=\"UTF-8\">\n" +
                                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                                    "    <title>Olvide Contraseña</title>\n" +
                                    "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                                    "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                                    "    <link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">\n" +
                                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD\" crossorigin=\"anonymous\">\n" +
                                    "    <style>\n" +
                                    "        body{\n" +
                                    "            font-family: 'Montserrat', sans-serif;\n" +
                                    "            align-content: center;\n" +
                                    "        }\n" +
                                    "        img{\n" +
                                    "            width: 350px;\n" +
                                    "        }\n" +
                                    "        .centrado{\n" +
                                    "            display:flex;\n" +
                                    "            justify-content: center;\n" +
                                    "            align-items: center;\n" +
                                    "        }\n" +
                                    "        \n" +
                                    "    </style>\n" +
                                    "</head>\n" +
                                    "<body>\n" +
                                    "    <div class=\"container centrado\">\n" +
                                    "        <div class=\"p-4\">\n" +
                                    "            <div class=\"p-4 centrado\" style=\"background: linear-gradient(50deg, #00B1D9,#049DBF,#005D98); border-radius: 15px;\">\n" +
                                    "                <h2 style=\"font-weight: 800; color: white;\">Recupera tu contraseña</h2>\n" +
                                    "            </div>\n" +
                                    "            \n" +
                                    "            <hr>\n" +
                                    "            <img src=\"https://www.puppies.com.au/uploads/1/0/5/8/105867835/corgi-cat-500-500px_orig.png\" alt=\"Perrito\">\n" +
                                    "            <div>\n" +
                                    "                <p style=\"font-size: 30px;\" class=\"centrado\">Tu nueva contraseña es:</p>\n" +
                                    "                <p style=\"font-size: 30px; font-weight: 800;\" class=\"centrado\"> " + nuevapass + "</p>\n" +
                                    "        </div>\n" +
                                    "\n" +
                                    "        </div>\n" +
                                    "        \n" +
                                    "    </div>\n" +
                                    "</html>";
                            correo = myDesUtil.cifrar(correo);
                            msj = myDesUtil.cifrar(msj);
                            list.get(j).setContra(nueva2);
                            Log.i(TAG, nueva2);
                            Log.i(TAG, list.get(j).getContra());
                            List2Json(list);
                            i = 1;
                        }
                        j++;
                    }
                    if (i == 1) {
                        Log.i(TAG, usuario);
                        Log.i(TAG, correo);
                        Log.i(TAG, msj);
                        if (Enviar(correo, msj)) {
                            Toast.makeText(getBaseContext(), "Se envío mensaje", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Olvide.this, Login.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG);

                    } else {
                        if (i == 0) {
                            Log.i(TAG, "Sin usuario");
                            Toast.makeText(getBaseContext(), "No existe usuario", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

            }
        });
    }
    public boolean Enviar( String correo ,String msj)
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( correo == null || correo.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , correo );
            jsonObject.put("mensaje", msj);
            String obj = jsonObject.toString();
            Log.i(TAG, obj);
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
                Log.e  (TAG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }
    public void List2Json(List<Info> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            json = myDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Escrito");
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
    private File getFile( )
    {
        return new File( getDataDir() , Registro.archivo );
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
            Intent intent4 = new Intent(Olvide.this, Login.class);
            startActivity(intent4);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}