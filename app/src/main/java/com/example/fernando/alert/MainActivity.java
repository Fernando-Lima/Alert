package com.example.fernando.alert;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import DAO.ContatoDAO;
import DAO.ContatoWSDAO;
import DAO.UsuarioDAO;
import model.Contato;
import model.Usuario;

public class MainActivity extends DebugActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnOk, btnAlerta;
    String message;
    ContatoDAO dao;
    UsuarioDAO usuarioDAO;
    ContatoWSDAO contatoWSDAO;
    String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ContatoDAO(this);
        usuarioDAO = new UsuarioDAO(this);
        contatoWSDAO = new ContatoWSDAO();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    protected void onResume() {
        checked();
        checkedLocal();
        super.onResume();
    }


    public void enviarMensagem(View v){
        if (checked() == false) {
            Toast.makeText(MainActivity.this,"nenhum contato como principal para enviar SMS",Toast.LENGTH_SHORT).show();
        }else{
            int id = v.getId();
            if(id == R.id.main_btn_ok){
                message = "OK - Estou bem!";
            }else if(id == R.id.main_btn_alerta){
                message = "ALERTA - Preciso de Ajuda ";
            }
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telefone,null,message, null,null);
                Toast.makeText(getApplicationContext(), "SMS enviado.", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Falha ao enviar sms. Por favor tente novamente.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void ligacao(View v){
        Uri uri = Uri.parse("tel:190");
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
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

    public boolean checkedLocal(){
        if (dao.checarLocalContato()!=true){
            Toast.makeText(this,"nenhum contato para enviar localização",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}
