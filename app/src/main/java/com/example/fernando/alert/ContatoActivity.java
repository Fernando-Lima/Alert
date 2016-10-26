package com.example.fernando.alert;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAO.ContatoDAO;
import adapter.ContatoListAdapter;
import model.Contato;

public class ContatoActivity extends DebugActivity{

    ContatoListAdapter contatoListAdapter;
    List<Contato> contatos;
    ContatoDAO dao;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        dao = new ContatoDAO(this);
        contatos = dao.listar();

        listView = (ListView)findViewById(R.id.lst_Contato);
        contatoListAdapter = new ContatoListAdapter(this,R.layout.list_contato_item,contatos);
        listView.setAdapter(contatoListAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ContatoActivity.this);
                builder.setTitle("Excluir contato");
                builder.setMessage("Excluir o contato selecionado");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deletarContato(String.valueOf(contatos.get(pos).getId()));
                        atualizaLista();
                        return;
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ContatoActivity.this, CadContatoActivity.class));
                abrirContato();
            }
        });

    }

    public void abrirContato(){
        Intent it = new Intent(this,CadContatoActivity.class);
        it.putExtra("id","");
        startActivityForResult(it, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        atualizaLista();
    }

    public void atualizaLista(){
        List<Contato> c= dao.listar();
        contatos.clear();
        contatos.addAll(c);
        contatoListAdapter.notifyDataSetChanged();
    }



}