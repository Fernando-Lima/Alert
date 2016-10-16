package com.example.fernando.alert;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends DebugActivity implements LocationListener {

    TextView tvLatitude, tvLongitude, tvProvedor;
    String latitude;
    String longitude;
    String message;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        btn = (Button)findViewById(R.id.alerta_local);
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                enviarSMS();
                return false;
            }
        });

        tvLatitude = (TextView) findViewById(R.id.gps_tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.gps_tvLongitude);
        tvProvedor = (TextView) findViewById(R.id.gps_tvProvedor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        tvLatitude.setText(String.valueOf(location.getLatitude()));
        tvLongitude.setText(String.valueOf(location.getLongitude()));
        tvProvedor.setText(location.getProvider());
        latitude = Double.toString(location.getLatitude());
        longitude = Double.toString(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        //implementar msg ex: para diser que gps não esta ativo
    }

    public  void enviarSMS(){
        message = "ALERTA - Preciso de Ajuda  Esta é a minha localização: Latitude: " + latitude + " Longitude: " + longitude;
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("91291942",null,message, null,null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
