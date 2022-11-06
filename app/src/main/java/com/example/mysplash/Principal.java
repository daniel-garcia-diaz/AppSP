package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Principal extends AppCompatActivity {

    String aux = null;
    Info info = null;
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
                        textView.setText("Bienvenido  " + info.getUser());
                    }
                }
            }
        }
    }
}