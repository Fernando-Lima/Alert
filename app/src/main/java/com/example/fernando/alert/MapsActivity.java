package com.example.fernando.alert;
import android.content.Context;
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
    private String nome = "Nadine";
    private Double latitude = -26.873576;
    private Double longitude = -52.4085978;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;

        try {
            //Pegar minha localização atual com o locationManager
            //locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);


            //Objeto Criteria permite a realizar buscas ao elemento Provider
            //Criteria criteria = new Criteria();
            //Pega o melhor provider
            //String provider = locationManager.getBestProvider(criteria, true);


            // Add a marker in Sydney, Australia, and move the camera.
            map.getUiSettings().setZoomControlsEnabled(true); // Botão zoom
            map.setOnMapLongClickListener(this); //segurar precionado para obter coordenadas
            map.setMyLocationEnabled(true);// Botão para pegar minha localização atual

        }catch (SecurityException ex){
            Log.e(TAG,getLocalClassName() + " Error ", ex);
        }

        LatLng sydney = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions();
        marker.position(sydney);
        marker.title("Marker in Sydney");
        map.addMarker(marker);
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this,"Coordenadas: " + latLng.toString(), Toast.LENGTH_SHORT).show();

        addMarcador(map,latLng);

    }

    public void addMarcador(GoogleMap map, LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title("Minha localizacao").snippet(nome);
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

