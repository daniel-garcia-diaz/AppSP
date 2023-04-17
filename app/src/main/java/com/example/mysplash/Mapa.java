package com.example.mysplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mysplash.BD.BDContras;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    GoogleMap mMap;
    Double latitud;
    Double longitud;
    Info2 info2 = new Info2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Double longitud = info2.getLongitud();
        Double latitud = info2.getLatitud();

        LatLng prueba = new LatLng(19.4540242,-99.1932275);
        LatLng ubi = new LatLng(37.421998333333335,-122.084);
        LatLng ubii = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubi).title("Contraseña"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubi));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

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
            Intent intent4 = new Intent(Mapa.this, Principal.class);
            startActivity(intent4);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}