package com.example.fernando.alert;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        LocationListener,
        GoogleMap.OnMapClickListener{

    private LocationManager locationManager;
    private static final String TAG = "Erro";
    private GoogleMap map;
    private String telefone;
    private String nome ;
    private String data;
    private Double latitude = -26.873576;
    private Double longitude = -52.4085978;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent it = getIntent();
        if(!it.getStringExtra("nome").equals("")){
         //   edtCodContato.setText(it.getStringExtra("id"));
          //  buscarContato();

            nome = it.getStringExtra("nome");
            data = it.getStringExtra("data");
            telefone = it.getStringExtra("telefone");
            latitude = Double.parseDouble(it.getStringExtra("latitude"));
            longitude = Double.parseDouble(it.getStringExtra("longitude"));

            Log.i("putExtra", it.getStringExtra("nome"));
            Log.i("putExtra", it.getStringExtra("data"));
            Log.i("putExtra", it.getStringExtra("latitude"));
            Log.i("putExtra", it.getStringExtra("longitude"));
            Log.i("putExtra", it.getStringExtra("telefone"));
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;

        try {
            map.getUiSettings().setZoomControlsEnabled(true); // Botão zoom
            map.setOnMapLongClickListener(this); //segurar precionado para obter coordenadas
            map.setMyLocationEnabled(true);// Botão para pegar minha localização atual

        }catch (SecurityException ex){
            Log.e(TAG,getLocalClassName() + " Error ", ex);
        }

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng).title(nome).snippet(data);
        map.addMarker(marker);
        int zoom =15;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        map.moveCamera(update);

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this,"Coordenadas: " + latLng.toString(), Toast.LENGTH_SHORT).show();

        addMarcador(map,latLng);

    }

    public void addMarcador(GoogleMap map, LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(nome);
        markerOptions.position(latLng).snippet(telefone);
        markerOptions.position(latLng).snippet(data);
        Marker marker = map.addMarker(markerOptions);
    }

    public void addMarcadorTitulo(GoogleMap map, LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(nome);
        Marker marker = map.addMarker(markerOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this,"Provider habilitado",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,"Provider desabilitato",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this,"Coordenadas: " + latLng, Toast.LENGTH_SHORT).show();
        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
        map.animateCamera(update);
    }
}

