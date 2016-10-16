package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import db.BancoDados;
import model.Contato;

/**
 * Created by fernando on 23/09/15.
 */
public class ContatoDAO {

    SQLiteDatabase db;// representa a conexão com o banco

    public ContatoDAO(Context context) {
        db = BancoDados.getDB(context);// Cria o Banco
    }

    public void  salvar(Contato contato) {
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("codPais",contato.getcodPais());
        values.put("telefone", contato.getTelefone());
        db.insert("tbl_contato", null, values);
    }
    public Contato buscar(String id){
        String[] colunas = new String[]{"_id","nome","codPais","telefone"};
        String[] args = new String[]{id};

        Cursor c = db.query("tbl_contato",colunas,"_id = ?",args,null,null,null);

        c.moveToFirst();
        Contato contato = new Contato();

        contato.setId(c.getLong(c.getColumnIndex("_id")));
        contato.setNome(c.getString(c.getColumnIndex("nome")));
        contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
        contato.setcodPais(c.getString(c.getColumnIndex("codPais")));
        return contato;
    }
    public void deletarContato(String id){
        String[] args = new String[]{id};
        db.delete("tbl_contato", "_id = ?", args);

    }
    public void alterarContato(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("codPais",contato.getcodPais());
        values.put("telefone", contato.getTelefone());

        String[] args = new String[]{String.valueOf(contato.getId())};

        db.update("tbl_contato", values, "_id = ? ", args);

    }
    public List<Contato> listar(){
        String[] colunas = new String[]{"_id","nome","codPais","telefone"};
        List<Contato> contatos;
        Cursor c = db.query("tbl_contato",colunas,null,null,null,null,null,null);

        contatos = new ArrayList<Contato>();
        if (c.moveToFirst()){
            do{
                Contato contato = new Contato();

                contato.setId(c.getLong(c.getColumnIndex("_id")));
                contato.setNome(c.getString(c.getColumnIndex("nome")));
                contato.setcodPais(c.getString(c.getColumnIndex("codPais")));
                contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
                contatos.add(contato);
            }while (c.moveToNext());
        }
        c.close();
        return contatos;
    }

}


