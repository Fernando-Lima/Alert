package DAO;

import android.content.ContentValues;
import android.content.Context;
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
        db.insert("tbl_usuario", null, values);
        Log.e("banco","usuarioDAO inseriu usuario");
    }
}
