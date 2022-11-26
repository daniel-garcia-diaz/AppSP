package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Olvide extends AppCompatActivity {

    public static List<Info> list;
    public static String json = null;
    public static String TAG = "Hola";
    public static String cadena = null;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usuario = null;
    public String correo,msj;
    EditText user,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);

        Button btnRegresar = findViewById(R.id.btnRegresaaar);
        Button btnRecupera = findViewById(R.id.btnRecupera);
        user = findViewById(R.id.editRUser);
        list = Login.list;

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Olvide.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = String.valueOf(user.getText());
                if(usuario.equals("")){
                    Toast.makeText(getApplicationContext(), "Llena el campo de Usuario", Toast.LENGTH_LONG).show();
                }else{
                    int i=0;
                    for(Info info : list){
                        if(info.getUser().equals(usuario)){
                            correo=info.getEmail();
                            msj = "<html><h1>Olvide Contraseña</h1></html>";
                            correo = myDesUtil.cifrar(correo);
                            msj = myDesUtil.cifrar(msj);
                            i=1;
                        }
                    }
                    if(i==1){
                        Log.i(TAG,usuario);
                        Log.i(TAG,correo);
                        Log.i(TAG,msj);
                        if( Enviar( correo,msj ) )
                        {
                            Toast.makeText(getBaseContext() , "Se envío el texto" , Toast.LENGTH_LONG );
                            return;
                        }
                        Toast.makeText(getBaseContext() , "Error en el envío" , Toast.LENGTH_LONG );
                    }else{
                        if(i==0){
                            Log.i(TAG,"no hay usuarios");
                            Toast.makeText(getBaseContext() , "No existen usuarios" , Toast.LENGTH_LONG );
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
}