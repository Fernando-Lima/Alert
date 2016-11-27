package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.fernando.alert.MainActivity;

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
        values.put("cod", usuario.getCod());
        dbUsuario.insert("tbl_usuario", null, values);
    }

    public Usuario buscarUsuario(){
        String[] colunas = new String[]{"_id","nome","telefone"};
        Cursor c = dbUsuario.query("tbl_usuario",colunas,"cod = 1",null,null,null,null);
        Usuario usuario = new Usuario();

        if(c.moveToFirst()){
            do {
                usuario.setNome(c.getString(c.getColumnIndex("nome")));
                usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            }while (c.moveToNext());
        }
        c.close();
        return usuario;
    }

    public Usuario buscarTudoUsuario(){
        String[] colunas = new String[]{"_id","nome","telefone"};
        Cursor c = dbUsuario.query("tbl_usuario",colunas,"cod = 1",null,null,null,null);
        Usuario usuario = new Usuario();

        if(c.moveToFirst()){
            do {
                usuario.setNome(c.getString(c.getColumnIndex("nome")));
                usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            }while (c.moveToNext());
        }
        c.close();
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
