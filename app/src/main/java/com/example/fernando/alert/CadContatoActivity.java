package com.example.fernando.alert;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import DAO.ContatoDAO;
import DAO.ContatoWSDAO;
import DAO.UsuarioDAO;
import model.Contato;
import model.Usuario;

public class CadContatoActivity extends DebugActivity implements LocationListener{

    EditText edtNomeContato, edtTelefoneContato, edtCodPais, edtCodContato;
    Switch controleSwitch, controleSwitchLocal;
    int principal;
    int local;
    String  latitude, longitude;
    ContatoWSDAO contatoWSDAO;
    String nomeUsuario;
    String telefoneUsuario;

    private static final String TAG = "banco";

    ContatoDAO dao;
    UsuarioDAO usuarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_contato);

        dao = new ContatoDAO(this);
        usuarioDAO = new UsuarioDAO(this);
        contatoWSDAO = new ContatoWSDAO();

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        edtCodContato = (EditText)findViewById(R.id.cadContato_edt_codigo);
        edtNomeContato = (EditText)findViewById(R.id.cadContato_edt_nome);
        edtCodPais = (EditText)findViewById(R.id.cadContato_edt_pais);
        edtTelefoneContato = (EditText)findViewById(R.id.cadContato_edt_phone);
        controleSwitch = (Switch)findViewById(R.id.cadContato_sw_principal);
        controleSwitchLocal = (Switch)findViewById(R.id.cadContato_sw_localizacao);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checked();

        Intent it = getIntent();
        if(!it.getStringExtra("id").equals("")){
            edtCodContato.setText(it.getStringExtra("id"));
            buscarContato();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNomeContato.getText().toString().equals("") || edtTelefoneContato.getText().toString().equals("")){
                    Log.i("error","nome contato em branco");
                    return;
                }else {
                    try {
                        if(edtCodContato.getText().toString().equals("")){
                            salvarContato();
                            Log.e(TAG, "Contato "+edtNomeContato.getText().toString()+" salvo com sucesso");
                            finish();
                        }else{
                            alterarContato();
                            Log.e(TAG, "Contato "+edtNomeContato.getText().toString()+" alterado com sucesso");
                        }
                    }catch (Exception ex){
                        Log.e(TAG,"Erro ao salvar contato ");
                    }
                }
            }
        });

        controleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (dao.checarContatoPrincipal()==true){
                        Toast.makeText(CadContatoActivity.this,"Já existe um contato como Principal",Toast.LENGTH_LONG).show();
                        controleSwitch.setChecked(false);
                        principal = 0;
                    }else {
                        principal = 1;
                    }
                }else{
                    principal = 0;
                }
            }
        });

        controleSwitchLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    local = 1;
                }else {
                    local = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        pegarUsuario();
        super.onResume();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
    }

    public void salvarContato(){
        Contato contato = new Contato();
        contato.setNome(edtNomeContato.getText().toString());
        contato.setTelefone(edtTelefoneContato.getText().toString());
        contato.setLatitude(0.0);
        contato.setLongitude(0.0);
        contato.setPrincipal(principal);
        if(controleSwitchLocal.isChecked()){
            try {
                if (latitude == null){
                    Toast.makeText(this,"o aplicativo não conseguiu pegar localização de GPS",Toast.LENGTH_SHORT).show();
                    local = 0;
                }else {
                    inserirContatoWS();
                    Log.i("ws","contato inserido com sucesso");
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.i("ws","falha ao inserir contato");
            }
        }
        contato.setLocal(local);
        dao.salvar(contato);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void buscarContato(){
        Contato contato = new Contato();
        contato = dao.buscar(edtCodContato.getText().toString());
        edtNomeContato.setText(contato.getNome());
        edtTelefoneContato.setText(contato.getTelefone());
        if (contato.getPrincipal() == 1){
            controleSwitch.setChecked(true);
            principal = 1;
        }
        if (contato.getLocal() == 1){
            controleSwitchLocal.setChecked(true);
            local = 1;
        }
    }

    public void alterarContato(){
        Contato contato = new Contato();
        contato.setId(new Long(edtCodContato.getText().toString()));
        contato.setNome(edtNomeContato.getText().toString());
        contato.setTelefone(edtTelefoneContato.getText().toString());
        contato.setPrincipal(principal);
        if(controleSwitchLocal.isChecked()){
            try {
                if (latitude == null){
                    Toast.makeText(this,"o aplicativo não conseguiu pegar localização de GPS",Toast.LENGTH_SHORT).show();
                    local = 0;
                }else {
                    inserirContatoWS();
                    Log.i("ws","contato inserido com sucesso");
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.i("ws","falha ao inserir contato");
            }
        }
        contato.setLocal(local);
        dao.alterarContato(contato);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void checked(){

        if(dao.checarTabela() != true){
            //tbl_contato vazia
            controleSwitch.setChecked(true);
            principal = 1;
            controleSwitchLocal.setChecked(true);
            local = 1;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude()).toString();
        longitude = String.valueOf(location.getLongitude()).toString();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void pegarUsuario(){
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.buscarUsuario();
        if(usuarioDAO.checarTabela() != true){
            Toast.makeText(this, "table empty", Toast.LENGTH_SHORT).show();
        }else {
            nomeUsuario = usuario.getNome().toString();
            telefoneUsuario = usuario.getTelefone().toString();
        }
    }

    public void inserirContatoWS(){
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNome(nomeUsuario);
        usuario.setTelefone(telefoneUsuario);
        usuario.setTelefoneContato(edtTelefoneContato.getText().toString());
        usuario.setLatitude( latitude);
        usuario.setLongitude(longitude);
        Toast.makeText(this,"Latitude: " + latitude +"Longitude: "+ longitude,Toast.LENGTH_SHORT).show();
        contatoWSDAO.inserir(usuario);
    }
}
