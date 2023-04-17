package com.example.mysplash;

import static com.example.mysplash.BD.BDService.TABLE_CONTRAS;
import static com.example.mysplash.Registro.archivo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysplash.API.Music;
import com.example.mysplash.BD.BDContras;
import com.example.mysplash.BD.BDService;
import com.example.mysplash.BD.UserContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements LocationListener{

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
    ImageView imageView;
    Object object = null;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
    int REQUEST_CODE = 200;
    public static Double latitud;
    public static Double longitud;
    private LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        startGps();
        verificarPermisos();

        Button edita = findViewById(R.id.btnEditar);
        Button elimina = findViewById(R.id.btnEmilinar);
        Button ubi = findViewById(R.id.btnUbi);

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
        ubi.setVisibility(View.GONE);

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
                ubi.setVisibility(view.VISIBLE);
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
                ubi.setVisibility(View.GONE);
            }
        });

        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, Mapa.class);
                startActivity(intent);
            }
        });
    }

    private void toast( int i )
    {
        Toast.makeText(getBaseContext(), lista.get(i).getContraContra(), Toast.LENGTH_SHORT);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verificarPermisos(){
        int permisoUbi = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permisoUbi2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

       if ( permisoUbi == PackageManager.PERMISSION_GRANTED ){
            Toast.makeText(this, "Permiso Ubi ", Toast.LENGTH_SHORT).show();
        } else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
        if ( permisoUbi2 == PackageManager.PERMISSION_GRANTED ){
            Toast.makeText(this, "Permiso Ubi ", Toast.LENGTH_SHORT).show();
        } else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);
        }
        if ( permisoCamara == PackageManager.PERMISSION_GRANTED ){
            Toast.makeText(this, "Permiso Camara ", Toast.LENGTH_SHORT).show();
        } else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE);
        }
    }

    public void Agrega(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View Popview = getLayoutInflater().inflate(R.layout.pop, null);


        Button btn = Popview.findViewById(R.id.btnGuardar);
        Button tomarfoto = Popview.findViewById(R.id.tomarfoto);
        Button subirfoto = Popview.findViewById(R.id.subirfoto);

        imageView = Popview.findViewById(R.id.foto);
        editU = Popview.findViewById(R.id.EditUsrContra);
        editC = Popview.findViewById(R.id.EditContraContra);

        dialogBuilder.setView(Popview);
        dialog = dialogBuilder.create();
        dialog.show();


        tomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        subirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        longitud = location.getLongitude();
        latitud = location.getLatitude();

        if (latitud != 0 && longitud != 0){

            Info2 info2 = new Info2();
            info2.setLongitud(longitud);
            info2.setLatitud(latitud);
            Toast.makeText(this, String.valueOf(longitud), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, String.valueOf(latitud), Toast.LENGTH_SHORT).show();
            stopGps();
        }
        else
            return;
    }

    private void startGps() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
            return;
        }
        if( locationManager == null )
        {
            locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, (float) 0, (LocationListener) this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 3:
                if( android.Manifest.permission.ACCESS_FINE_LOCATION.equals( permissions[ 0 ]) && grantResults[ 0 ] == 0 )
                {
                    startGps();
                    return;
                }
                break;
        }
    }
    private void stopGps( )
    {
        locationManager.removeUpdates((LocationListener) this);
        locationManager = null;
    }
    @Override
    public void onProviderEnabled(@NonNull String provider)
    {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
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

