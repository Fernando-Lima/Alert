package com.example.fernando.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DAO.ContatoDAO;
import model.Contato;

public class CadContatoActivity extends DebugActivity {

    EditText edtNomeContato, edtTelefoneContato, edtCodPais, edtCodContato;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                        salvarContato();
                        Log.e(TAG, "Contato "+edtNomeContato.getText().toString()+" salvo com sucesso");
                        finish();
                    }catch (Exception ex){
                        Log.e(TAG,"Erro ao salvar contato ");
                    }
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

        dao.salvar(contato);
    }

    public void buscarContato(){
        Contato contato = new Contato();

        contato = dao.buscar(edtCodContato.getText().toString());
        edtNomeContato.setText(contato.getNome());
        edtTelefoneContato.setText(contato.getTelefone());
    }
}
