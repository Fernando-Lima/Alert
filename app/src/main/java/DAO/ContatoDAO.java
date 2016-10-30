package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.BancoDados;
import model.Contato;

/**
 * Created by fernando on 23/09/15.
 */
public class ContatoDAO {
    Boolean checked;

    SQLiteDatabase dbContato;// representa a conexão com o banco

    public ContatoDAO(Context context) {
        dbContato = BancoDados.getDbContato(context);// Cria o Banco
    }

    public void  salvar(Contato contato) {
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("latitude", contato.getLatitude());
        values.put("longitude",contato.getLongitude());
        values.put("principal",contato.getPrincipal());
        dbContato.insert("tbl_contato", null, values);
        Log.e("banco","contatoDAO inseriu contato");
    }
    public Contato buscar(String id){
        String[] colunas = new String[]{"_id","nome","telefone","principal"};
        String[] args = new String[]{id};

        Cursor c = dbContato.query("tbl_contato",colunas,"_id = ?",args,null,null,null);

        c.moveToFirst();
        Contato contato = new Contato();

        contato.setId(c.getLong(c.getColumnIndex("_id")));
        contato.setNome(c.getString(c.getColumnIndex("nome")));
        contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
        contato.setPrincipal(c.getInt(c.getColumnIndex("principal")));
        return contato;
    }
    public void deletarContato(String id){
        String[] args = new String[]{id};
        dbContato.delete("tbl_contato", "_id = ?", args);

    }
    public void alterarContato(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("principal",contato.getPrincipal());

        String[] args = new String[]{String.valueOf(contato.getId())};

        dbContato.update("tbl_contato", values, "_id = ? ", args);

    }
    public List<Contato> listar(){
        String[] colunas = new String[]{"_id","nome","telefone","latitude","longitude","principal"};
        List<Contato> contatos;
        Cursor c = dbContato.query("tbl_contato",colunas,null,null,null,null,null,null);

        contatos = new ArrayList<Contato>();
        if (c.moveToFirst()){
            do{
                Contato contato = new Contato();

                contato.setId(c.getLong(c.getColumnIndex("_id")));
                contato.setNome(c.getString(c.getColumnIndex("nome")));
                contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
                contato.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                contato.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
                contato.setPrincipal(c.getInt(c.getColumnIndex("principal")));
                contatos.add(contato);
            }while (c.moveToNext());
        }
        c.close();
        return contatos;
    }

    public boolean checarTabela(){
        Cursor cur = dbContato.rawQuery("SELECT COUNT(*) FROM tbl_contato", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                //tabela tbl_contato vazia
                checked = false;
            } else {
                //tabela tbl_contato contém dados
                checked = true;
            }
        }
        return checked;
    }

    //Query para buscar contato principal
    public Contato buscarPrincipal(){
        Cursor c = dbContato.rawQuery("SELECT * FROM tbl_contato WHERE principal = 1",null);
        Contato contato = new Contato();
        contato.setNome(c.getString(c.getColumnIndex("nome")));
        contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
        contato.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
        contato.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
        contato.setPrincipal(c.getInt(c.getColumnIndex("principal")));
        return contato;
    }

}


