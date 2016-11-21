package com.example.fernando.alert;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import DAO.UsuarioWSDAO;
import DAO.UsuarioDAO;
import model.Usuario;


public class HellowActivity extends AppCompatActivity {

    EditText edtNome, edtTelefone;
    private Double latitude;
    private Double longitude;
    private AlertDialog alertDialog;
    private static final String TAG = "error";

    UsuarioDAO dao;
    UsuarioWSDAO localDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellow);

        dao = new UsuarioDAO(this);
        localDAO = new UsuarioWSDAO();

        edtNome = (EditText)findViewById(R.id.hellow_edt_nome);
        edtTelefone = (EditText)findViewById(R.id.hellow_edt_numero);

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtNome.getText().toString().equals("")){
                    Log.i("error","nome usuario em branco");
                    return;
                }else if (edtTelefone.getText().toString().equals("")){
                    Log.i("error","telefone usuario em branco");
                    return;
                }else {

                    try {
                        salvarUsuario();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("banco", "Erro " +e);
                    }

                    try {
                        inserirWS();
                        Log.i("ws","inserido com sucesso!");
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.i("ws","Erro ao inserir " + e);
                    }

                    startActivity(new Intent(HellowActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
    public void salvarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(edtNome.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());
        usuario.setCod(1);
        dao.salvar(usuario);
    }

    public void inserirWS(){
        Usuario usuario = new Usuario();
        usuario.setNome(edtNome.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());
        usuario.setId(1L);
        localDAO.inserir(usuario);
    }

}
