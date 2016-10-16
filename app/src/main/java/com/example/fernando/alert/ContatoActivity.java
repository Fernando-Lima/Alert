package com.example.fernando.alert;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import DAO.ContatoDAO;
import DAO.ContatoListAdapter;
import db.BancoDados;
import model.Contato;

public class ContatoActivity extends DebugActivity{
    // private GoogleApiClient client;


    ContatoListAdapter adapter;
    List<Contato> contatos;
    ContatoDAO dao;

    ListActivity listActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContatoActivity.this, CadContatoActivity.class));
            }
        });
        //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

}