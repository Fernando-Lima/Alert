package com.example.fernando.alert;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;


import utils.PermissionUtils;

public class SplahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

        //Lista de permissões necessárias
        String permissions[] = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS
        };
        //Valida Lista

        boolean ok = PermissionUtils.validate(this,0,permissions);
        if(ok){
            //Tudo ok pode entrar
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        for (int result: grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                finish();
                Toast.makeText(getApplicationContext(), "Permissão negada.", Toast.LENGTH_LONG).show();
                //Negou a permissão
                return;
            }else{
                //Permissão concedida
                Toast.makeText(getApplicationContext(), "Permissão concedida.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,HellowActivity.class));
                finish();
                return;
            }
        }
    }
}
