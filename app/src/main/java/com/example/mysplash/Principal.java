package com.example.mysplash;

import static com.example.mysplash.BD.BDService.TABLE_CONTRAS;
import static com.example.mysplash.Registro.archivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mysplash.API.Music;
import com.example.mysplash.BD.BDContras;
import com.example.mysplash.BD.BDService;
import com.example.mysplash.BD.UserContract;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private List<Info> list;
    public static String TAG = "Daniel";
    public static String json = null;
    public static ListView listView;
    private List<Info2> lista;
    public static Info info = null;
    Info2 info2 = new Info2();
    public int pos = 0;
    EditText editU, editC;
    Object object = null;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Button edita = findViewById(R.id.btnEditar);
        Button elimina = findViewById(R.id.btnEmilinar);


     edita.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Edita();
         }
     });

        Intent intent = getIntent();
        if( intent != null)
        {
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("Info");
                if (object != null) {
                    if (object instanceof Info) {
                        info = (Info) object;
                    }
                }
            }
        }
        edita.setVisibility(View.GONE);
        elimina.setVisibility(View.GONE);

        BDContras bdContras = new BDContras(Principal.this);
        lista = bdContras.getPass(info.getId_user());

        listView = (ListView) findViewById(R.id.listViewId);
        MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                info2 = lista.get(i);
                pos = i;
                toast( i );
                edita.setVisibility(view.VISIBLE);
                elimina.setVisibility(view.VISIBLE);
            }
        });
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BDContras bdContras = new BDContras(Principal.this);
                boolean id = bdContras.eliminarContra(info.getId_user(),info2.getUsuarioContra(),info2.getContraContra());
                if(id){
                    lista = bdContras.getPass(info.getId_user());
                    MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                    listView.setAdapter(myAdapter);
                    Toast.makeText(getApplicationContext(),"Contraseña Eliminada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error al Eliminar", Toast.LENGTH_LONG).show();
                }
                edita.setVisibility(View.GONE);
                elimina.setVisibility(View.GONE);
            }
        });


    }
    private void toast( int i )
    {
        Toast.makeText(getBaseContext(), lista.get(i).getContraContra(), Toast.LENGTH_SHORT);
    }

    public void Agrega(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View Popview = getLayoutInflater().inflate(R.layout.pop, null);

        Button btn = Popview.findViewById(R.id.btnGuardar);
        editU = Popview.findViewById(R.id.EditUsrContra);
        editC = Popview.findViewById(R.id.EditContraContra);

        dialogBuilder.setView(Popview);
        dialog = dialogBuilder.create();
        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user= String.valueOf(editU.getText());
                String pass = String.valueOf(editC.getText());

               if (user.length()==0 || pass.length()==0){
                    Toast.makeText(getApplicationContext(),"Campos Vacios", Toast.LENGTH_LONG).show();
                }
                else{

                   Info2 info2 = new Info2();
                   info2.setContraContra(pass);
                   info2.setUsuarioContra(user);
                   info2.setId_user(info.getId_user());

                   BDContras bdContras = new BDContras(Principal.this);
                   long id = bdContras.savePass(info2);
                   if (id > 0){
                       lista = bdContras.getPass(info.getId_user());
                       MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                       listView.setAdapter(myAdapter);
                       Toast.makeText(Principal.this, "Contraseña Agregada",Toast.LENGTH_LONG).show();
                   }else
                   {
                       Toast.makeText(Principal.this, "No se ha podido Agregar la Contraseña",Toast.LENGTH_LONG).show();
                   }
               }
                dialog.cancel();
            }
        });
    }

    public void Edita(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View Popview2 = getLayoutInflater().inflate(R.layout.pop, null);

        Button btn = Popview2.findViewById(R.id.btnGuardar);
        editU = Popview2.findViewById(R.id.EditUsrContra);
        editC = Popview2.findViewById(R.id.EditContraContra);

        dialogBuilder.setView(Popview2);
        dialog = dialogBuilder.create();
        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user= String.valueOf(editU.getText());
                String pass = String.valueOf(editC.getText());

              /*  if (user.equals("") || pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Campos Vacios", Toast.LENGTH_LONG).show();
                }
                else {

                    }
                }*/

                BDContras bdContras = new BDContras(Principal.this);
                boolean id = bdContras.EditarContra(user,pass,info.getId_user(),info2.getId_pass());
                if(id){
                    lista = bdContras.getPass(info.getId_user());
                    MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                    listView.setAdapter(myAdapter);
                    Toast.makeText(getApplicationContext(), "Contraseña Editada", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_LONG).show();
                }
                dialog.cancel();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id==R.id.item1){
            Agrega();
        }
        if (id==R.id.item2){
            Intent intent2 = new Intent(Principal.this, Olvide.class);
            startActivity(intent2);
            return true;
        }
        if (id==R.id.item3){
            Intent intent3 = new Intent(Principal.this, Login.class);
            startActivity(intent3);
            return true;
        }
        if(id==R.id.regresar){
            Intent intent4 = new Intent(Principal.this, Login.class);
            startActivity(intent4);
            return true;
        }
        if(id==R.id.musica){
            Intent intent4 = new Intent(Principal.this, Music.class);
            startActivity(intent4);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

