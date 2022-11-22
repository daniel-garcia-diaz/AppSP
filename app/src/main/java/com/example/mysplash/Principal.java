package com.example.mysplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {


    private List<Info> list;
    public static String TAG = "Daniel";
    public static String json = null;
    private ListView listView;
    private List<Info2> lista;
    public int pos = 0;

    String aux = null;
    public static Info info = null;
    TextView textView;
    EditText editText, editText1;
    Button btnmas, btnelimi, btnedi;
    Object object = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Button btnRegresar = findViewById(R.id.btnRegresaar);
        //Button btnagregar = findViewById(R.id.button3);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
       /* btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, Agregar.class);
                startActivity(intent);
                finish();
            }
        });*/


        Intent intent = getIntent();
        if( intent != null)
        {
            /*aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                textView.setText(aux);
            }*/
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("Info");
                if (object != null) {
                    if (object instanceof Info) {
                        info = (Info) object;
                        //textView.setText( info.getUser());
                    }
                }
            }
        }
        /*listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<String>();
        for( int i = 0; i < 100; i++)
        {
            list.add( String.format( "Contraseña %d" , i + 1 ) );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_lista,R.id.textView18, list );
        listView.setAdapter(arrayAdapter);*/
        list = new ArrayList<>();
        list = Login.list;
        editText = findViewById(R.id.edit1);
        editText1 = findViewById(R.id.edit2);
        btnmas = findViewById(R.id.btnAgrega);
        btnedi = findViewById(R.id.button);
        btnelimi = findViewById(R.id.button2);
        listView = (ListView) findViewById(R.id.listViewId);
        lista = new ArrayList<Info2>();
        lista = info.getContraseñas();
        MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                editText.setText(lista.get(i).getUsuarioContra());
                editText1.setText(lista.get(i).getContraContra());
                pos = i;
            }
        });
        btnelimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.remove(pos);
                info.setContraseñas(lista);
                MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(),"Contraseña Eliminada", Toast.LENGTH_LONG).show();
            }
        });
        btnedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = String.valueOf(editText.getText());
                String pass = String.valueOf(editText1.getText());
                lista.get(pos).setUsuarioContra(user);
                lista.get(pos).setContraContra(pass);
                info.setContraseñas(lista);
                MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(),"Contraseña Modificada", Toast.LENGTH_LONG).show();
            }
        });
        btnmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= String.valueOf(editText.getText());
                String contra = String.valueOf(editText1.getText());
                /*Info2 info2 = new Info2();
                info2.setContraContra(contra);
                info2.setUsuarioContra(user);
                lista.add(info2);
                info.setContraseñas(lista);
                MyAdapter myAdapter = new MyAdapter(lista, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(),"Contraseña Agregada", Toast.LENGTH_LONG).show();*/

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
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(),"Contraseña Agregada", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(Principal.this, Registro.class);
            startActivity(intent);
            return true;
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
