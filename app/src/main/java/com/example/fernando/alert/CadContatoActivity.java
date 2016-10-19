package com.example.fernando.alert;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import model.Contato;

public class CadContatoActivity extends DebugActivity {

    EditText edtNomeContato, edtCodigoContato, edtTelefoneContato, edtCodPais;

    private static final String TAG = "Erro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_contato);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                }catch (Exception ex){
                    Log.e(TAG, "Erro ao salvar no banco de dados");
                }
            }
        });

        edtNomeContato = (EditText)findViewById(R.id.cadContato_edt_nome);
        edtCodPais = (EditText)findViewById(R.id.cadContato_edt_pais);
        edtTelefoneContato = (EditText)findViewById(R.id.cadContato_edt_phone);
    }

}
