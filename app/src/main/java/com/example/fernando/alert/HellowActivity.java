package com.example.fernando.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DAO.UsuarioDAO;
import model.Usuario;


public class HellowActivity extends AppCompatActivity {

    EditText edtNome, edtTelefone;
    TextView tvNome;
    private Double latitude;
    private Double longitude;
    private static final String TAG = "error";

    UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellow);

        dao = new UsuarioDAO(this);

        edtNome = (EditText)findViewById(R.id.hellow_edt_nome);
        edtTelefone = (EditText)findViewById(R.id.hellow_edt_numero);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarUsuario();
                startActivity(new Intent(HellowActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    public void salvarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(edtNome.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());
        dao.salvar(usuario);


    }

}
