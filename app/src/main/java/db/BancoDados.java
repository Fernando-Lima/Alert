package db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BancoDados {

	// Nome do banco
	private static final String NOME_BANCO = " Alerta ";
	// Controle de versao
	private static final int VERSAO_BANCO = 1;
	// Script para fazer drop na tabela
	private static final String[] SCRIPT_DATABASE_DELETE = new String[]{"DROP TABLE IF EXISTS tbl_usuario;"};

	// Cria a tabela com o "_id" sequencial
	//Cria um Array do tipo String passando o Scrip do create da tabela tbl_usuario
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"create table tbl_usuario(_id integer primary key, nome text, codPais text, telefone text);"
	};

	private static SQLiteDatabase db;

	// Injeta os parâmetros no construtor do SQLiteHelper passando contexto, nome, versão, Script create, Script Delete
	public static SQLiteDatabase getDB(Context ctx) {
		try {
			if (db == null) {
				SQLiteHelper dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
				db = dbHelper.getWritableDatabase();
				Log.i("banco","bancoDados banco criado com sucesso");
			}
		}catch (SQLException ex){
			Log.i("banco","Erro ao criar o Banco de Dados");
		}
		return db;
	}
}
