package db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BancoDados {

	// Nome do banco
	private static final String NOME_BANCO = " Alerta ";
	// Controle de versao
	private static final int VERSAO_BANCO = 5;
	// Script para fazer drop na tabela
	private static final String[] SCRIPT_DATABASE_DELETE_USUARIO = new String[]{"DROP TABLE IF EXISTS tbl_usuario;"};
	private static final String[] SCRIPT_DATABASE_DELETE_CONTATO = new String[]{"DROP TABLE IF EXISTS tbl_contato;"};
	// Cria a tabela com o "_id" sequencial //Cria um Array do tipo String passando o Scrip do create da tabela
	private static final String[] SCRIPT_DATABASE_CREATE_USUARIO = new String[] {"create table tbl_usuario(_id integer primary key, nome text, telefone text, latitude double, longitude double);"};
	private static final String[] SCRIPT_DATABASE_CREATE_CONTATO = new String[] {"create table tbl_contato(_id integer primary key, nome text, telefone text, latitude double, longitude double);"};

	private static SQLiteDatabase dbContato;
	private static SQLiteDatabase dbUsuario;

	// Injeta os parâmetros no construtor do SQLiteHelper passando contexto, nome, versão, Script create, Script Delete
	public static SQLiteDatabase getDbUsuario(Context ctx) {
		try {
			if (dbUsuario == null) {
				SQLiteHelper dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE_USUARIO, SCRIPT_DATABASE_DELETE_USUARIO);
				dbUsuario = dbHelper.getWritableDatabase();
				Log.i("banco","bancoDados banco criado com sucesso");
			}
		}catch (SQLException ex){
			Log.i("banco","Erro ao criar o Banco de Dados");
		}
		return dbUsuario;
	}

	public static SQLiteDatabase getDbContato(Context ctx) {
		try {
			if (dbContato == null) {
				SQLiteHelper dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE_CONTATO, SCRIPT_DATABASE_DELETE_CONTATO);
				dbContato = dbHelper.getWritableDatabase();
				Log.i("banco","bancoDados Contato banco criado com sucesso");
			}
		}catch (SQLException ex){
			Log.i("banco","Erro ao criar o Banco de Dados Contatos");
		}
		return dbContato;
	}
}
