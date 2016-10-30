package com.example.fernando.alert;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import DAO.ContatoDAO;
import DAO.UsuarioDAO;
import model.Contato;

public class MainActivity extends DebugActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnOk, btnAlerta;
    String message;
    ContatoDAO dao;
    String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ContatoDAO(this);
        checked();

        btnOk = (Button) findViewById(R.id.main_btn_ok);
        btnAlerta = (Button) findViewById(R.id.main_btn_alerta);

        btnAlerta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (checked() == false) {
                    Toast.makeText(MainActivity.this,"nenhum contato como principal",Toast.LENGTH_SHORT).show();
                }else{
                    Uri uri = Uri.parse("tel:"+telefone);
                    Intent it = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(it);
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        checked();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_contato) {
            Intent it = new Intent(this, ContatoActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_localizacao) {
            Intent it = new Intent(this, MapsActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_historico) {
            Intent it = new Intent(this, HistoricoActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_ajuda) {
            Intent it = new Intent(this, AjudaActivity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void enviarMensagem(View v){
        if (checked() == false) {
            Toast.makeText(MainActivity.this,"nenhum contato como principal para enviar SMS",Toast.LENGTH_SHORT).show();
        }else{
            message = "ALERTA - Preciso de Ajuda ";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telefone,null,message, null,null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public boolean checked(){
        Contato contato = new Contato();
        contato = dao.buscarPrincipal();
        if(dao.checarContatoPrincipal() != true){
            //nenhum contato principal
            Toast.makeText(this,"nenhum contato como principal",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            telefone = contato.getTelefone().toString();
            return true;
        }
    }
}
