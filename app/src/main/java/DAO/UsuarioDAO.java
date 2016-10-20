package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import db.BancoDados;
import model.Usuario;

/**
 * Created by fernando on 18/10/16.
 */

public class UsuarioDAO {
    SQLiteDatabase db;// representa a conexão com o banco

    public UsuarioDAO(Context context){
        db= BancoDados.getDB(context);
    }

    public void  salvar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("telefone",usuario.getTelefone());
        values.put("latitude",usuario.getLatitude());
        values.put("longitude",usuario.getLongitude());

        db.insert("tbl_usuario", null, values);
        Log.e("banco","usuarioDAO inseriu usuario");
    }

    public Usuario buscarUsuario(String id){
        String[] colunas = new String[]{"_id","nome","telefone","latitude","longitude"};
        String[] args = new String[]{id};

        Cursor c = db.query("tbl_usuario",colunas,"_id = ?",args,null,null,null);

        c.moveToFirst();
        Usuario usuario = new Usuario();
        usuario.setId(c.getLong(c.getColumnIndex("_id")));
        usuario.setNome(c.getString(c.getColumnIndex("nome")));
        usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
        usuario.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
        usuario.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
        return usuario;
    }
}
