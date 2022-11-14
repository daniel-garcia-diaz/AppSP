package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private ListView listView;
    private List<String>list;;

    String aux = null;
    public static Info info = null;
    TextView textView;
    Object object = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Button btnRegresar = findViewById(R.id.btnRegresaar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        textView = findViewById(R.id.PrincipalText);
        Intent intent = getIntent();
        if( intent != null)
        {
            aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                textView.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("Info");
                if (object != null) {
                    if (object instanceof Info) {
                        info = (Info) object;
                        textView.setText( info.getUser());
                    }
                }
            }
        }
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<String>();
        for( int i = 0; i < 100; i++)
        {
            list.add( String.format( "Contraseña %d" , i + 1 ) );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_lista,R.id.EditTextContra, list );
        listView.setAdapter(arrayAdapter);
    }
}