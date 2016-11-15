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

    public Usuario buscarUsuario(String id){
        String[] colunas = new String[]{"nome"};
        String[] args = new String[]{id};

        Cursor c = dbUsuario.query("tbl_usuario",colunas,"_id = ?",args,null,null,null);

        c.moveToFirst();
        Usuario usuario = new Usuario();
        usuario.setId(c.getLong(c.getColumnIndex("_id")));
        usuario.setNome(c.getString(c.getColumnIndex("nome")));
        usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
        usuario.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
        usuario.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
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
                usuario.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                usuario.setLongitude(c.getDouble(c.getColumnIndex("longitude")));

                usuarios.add(usuario);
            }while (c.moveToNext());
        }
        c.close();
        return usuarios;
    }
}
