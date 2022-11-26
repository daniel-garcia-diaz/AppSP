package com.example.mysplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
    public int pos = 0;

    public static Info info = null;
    EditText editU, editC;
    Object object = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Button btnRegresar = findViewById(R.id.btnRegresaar);
        Button btnagregar = findViewById(R.id.btnmas);
        Button edita = findViewById(R.id.btnEditar);
        Button elimina = findViewById(R.id.btnEmilinar);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

     btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agrega();
            }
        });

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


        list = new ArrayList<>();
        list = Login.list;;
        listView = (ListView) findViewById(R.id.listViewId);
        lista = new ArrayList<Info2>();
        lista = info.getContraseñas();
        MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                editU.setText(lista.get(i).getUsuarioContra());
                editC.setText(lista.get(i).getContraContra());
                pos = i;
                edita.setVisibility(view.VISIBLE);
                elimina.setVisibility(view.VISIBLE);
            }
        });
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.remove(pos);
                info.setContraseñas(lista);
                MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                listView.setAdapter(myAdapter);
                editU.setText("");
                editC.setText("");
                Toast.makeText(getApplicationContext(),"Contraseña Eliminada", Toast.LENGTH_LONG).show();
                edita.setVisibility(View.GONE);
                elimina.setVisibility(View.GONE);
            }
        });


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
                String contra = String.valueOf(editC.getText());

               if (user.equals("") || contra.equals("")){
                    Toast.makeText(getApplicationContext(),"Campos Vacios", Toast.LENGTH_LONG).show();
                }
                else{
                    Info2 info2 = new Info2();
                    info2.setContraContra(contra);
                    info2.setUsuarioContra(user);
                    lista.add(info2);
                    info.setContraseñas(lista);
                    MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                    listView.setAdapter(myAdapter);
                    Toast.makeText(getApplicationContext(),"Contraseña Agregada", Toast.LENGTH_LONG).show();
               }
                dialog.dismiss();

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
                Button edita = findViewById(R.id.btnEditar);
                Button elimina = findViewById(R.id.btnEmilinar);

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Campos Vacios", Toast.LENGTH_LONG).show();
                }
                else {
                    lista.get(pos).setUsuarioContra(user);
                    lista.get(pos).setContraContra(pass);
                    info.setContraseñas(lista);
                    MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                    listView.setAdapter(myAdapter);
                    Toast.makeText(getApplicationContext(), "Contraseña Modificada", Toast.LENGTH_LONG).show();
                    edita.setVisibility(View.GONE);
                    elimina.setVisibility(View.GONE);
                    dialog.dismiss();
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
        return super.onOptionsItemSelected(item);

    }

}

