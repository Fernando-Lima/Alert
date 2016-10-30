package com.example.fernando.alert;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import model.Contato;

public class CadContatoActivity extends DebugActivity {

    EditText edtNomeContato, edtTelefoneContato, edtCodPais, edtCodContato;
    Switch controleSwitch;
    int principal;

    private static final String TAG = "banco";

    ContatoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_contato);

        dao = new ContatoDAO(this);

        edtCodContato = (EditText)findViewById(R.id.cadContato_edt_codigo);
        edtNomeContato = (EditText)findViewById(R.id.cadContato_edt_nome);
        edtCodPais = (EditText)findViewById(R.id.cadContato_edt_pais);
        edtTelefoneContato = (EditText)findViewById(R.id.cadContato_edt_phone);
        controleSwitch = (Switch)findViewById(R.id.cadContato_sw_principal);

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
                   principal = 1;
                }else{
                    principal = 0;
                }
            }
        });
    }

    public void salvarContato(){
        Contato contato = new Contato();
        contato.setNome(edtNomeContato.getText().toString());
        contato.setTelefone(edtTelefoneContato.getText().toString());
        contato.setLatitude(0.0);
        contato.setLongitude(0.0);
        contato.setPrincipal(principal);
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
        }
    }

    public void alterarContato(){
        Contato contato = new Contato();
        contato.setId(new Long(edtCodContato.getText().toString()));
        contato.setNome(edtNomeContato.getText().toString());
        contato.setTelefone(edtTelefoneContato.getText().toString());
        contato.setPrincipal(principal);
        dao.alterarContato(contato);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void checked(){

        if(dao.checarTabela() != true){
            //tbl_contato vazia
            controleSwitch.setChecked(true);
            principal = 1;
        }
    }
}
