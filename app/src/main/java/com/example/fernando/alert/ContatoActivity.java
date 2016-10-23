package com.example.fernando.alert;

import android.app.ListActivity;
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

import java.util.ArrayList;
import java.util.List;

import DAO.ContatoDAO;
import adapter.ContatoListAdapter;
import model.Contato;

public class ContatoActivity extends DebugActivity{

    ContatoListAdapter contatoListAdapter;
    List<Contato> contatos;
    ContatoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        dao = new ContatoDAO(this);
        contatos = dao.listar();

        ListView listView = (ListView)findViewById(R.id.lst_Contato);
        contatoListAdapter = new ContatoListAdapter(this,R.layout.list_contato_item,contatos);
        listView.setAdapter(contatoListAdapter);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"Excluir");
        menu.add(0,1,0,"Adicionar como contato principal");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                ContextMenu.ContextMenuInfo info = item.getMenuInfo();
                int pos = ((AdapterView.AdapterContextMenuInfo)info).position;
                dao.deletarContato(String.valueOf(contatos.get(pos).getId()));
                atualizaLista();
        }
        return true;
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