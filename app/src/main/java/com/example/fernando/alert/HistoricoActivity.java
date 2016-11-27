package com.example.fernando.alert;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAO.ContatoWSDAO;
import DAO.UsuarioDAO;
import DAO.UsuarioWSDAO;
import adapter.HistoricoListAdapter;
import model.Contato;
import model.Usuario;

public class HistoricoActivity extends DebugActivity implements AdapterView.OnItemClickListener {
    HistoricoListAdapter historicoListAdapter;
    private ListView listView;
    private List<Usuario> usuarios;
    private ContatoWSDAO contatoWSDAO;
    UsuarioDAO usuarioDAO;
    private String telefoneUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        usuarioDAO = new UsuarioDAO(this);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onResume() {
        pegarUsuario();
        super.onResume();
        listar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Usuario usuario = usuarios.get(position);
        String codigo = String.valueOf(usuario.getId());
        String nome = usuario.getNome();
        String data = usuario.getData();
        String telefone = usuario.getTelefoneContato();
        String latitude = usuario.getLatitude();
        String longitude = usuario.getLongitude();
        Intent it = new Intent(this,MapsActivity.class);
        it.putExtra("nome",nome);
        it.putExtra("data", data);
        it.putExtra("telefone", telefone);
        it.putExtra("latitude", latitude);
        it.putExtra("longitude", longitude);
        startActivityForResult(it,1);
    }

    public void pegarUsuario(){
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.buscarUsuario();
        if(usuarioDAO.checarTabela() != true){
            Toast.makeText(this, "table empty", Toast.LENGTH_SHORT).show();
        }else {
            telefoneUsuario = usuario.getTelefone().toString();
        }
    }

    public void listar(){
        contatoWSDAO = new ContatoWSDAO();
        usuarios = contatoWSDAO.buscarTodos(telefoneUsuario);
        if(usuarios != null){
            listView = (ListView)findViewById(R.id.lvHistorico);
            historicoListAdapter = new HistoricoListAdapter(this,R.layout.list_historico_item,usuarios);
            listView.setAdapter(historicoListAdapter);

        }else {
            Toast.makeText(this,"Nenhum usuário enviou localização",Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(this);

    }
}
