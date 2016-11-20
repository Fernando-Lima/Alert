package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.BancoDados;
import model.Usuario;

/**
 * Created by fernando on 18/10/16.
 */

public class UsuarioDAO {
    SQLiteDatabase dbUsuario;// representa a conexão com o banco
    Boolean checked;

    public UsuarioDAO(Context context){
        dbUsuario= BancoDados.getDbUsuario(context); //Cria o banco
    }

    public void  salvar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("telefone",usuario.getTelefone());
        values.put("latitude",usuario.getLatitude());
        values.put("longitude",usuario.getLongitude());
        dbUsuario.insert("tbl_usuario", null, values);
    }

    public Usuario buscarUsuario(){
        String[] colunas = new String[]{"_id","nome","telefone"};
        Cursor c = dbUsuario.rawQuery("SELECT nome, telefone FROM tbl_usuario",null);

        c.moveToFirst();
        Usuario usuario = new Usuario();
        usuario.setId(c.getLong(c.getColumnIndex("_id")));
        usuario.setNome(c.getString(c.getColumnIndex("nome")));
        usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));

        return usuario;
    }

    public List<Usuario> listar(){
        String[] colunas = new String[]{"_id", "nome", "telefone", "latitude", "longitude"};
        List<Usuario> usuarios;

        Cursor c = dbUsuario.query("tbl_usuario",colunas,null,null,null,null,null,null);

        usuarios = new ArrayList<Usuario>();

        if(c.moveToFirst()){
            do {
                Usuario usuario = new Usuario();

                usuario.setId(c.getLong(c.getColumnIndex("_id")));
                usuario.setNome(c.getString(c.getColumnIndex("nome")));
                usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
                usuario.setLatitude(c.getString(c.getColumnIndex("latitude")));
                usuario.setLongitude(c.getString(c.getColumnIndex("longitude")));

                usuarios.add(usuario);
            }while (c.moveToNext());
        }
        c.close();
        return usuarios;
    }

    public boolean checarTabela(){
        Cursor cur = dbUsuario.rawQuery("SELECT COUNT(*) FROM tbl_usuario", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                //tabela tbl_usuario vazia
                checked = false;
            } else {
                //tabela tbl_usuario contém dados
                checked = true;
            }
        }
        return checked;
    }


}
