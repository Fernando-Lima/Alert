package DAO;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import model.Usuario;

/**
 * Created by fernando on 11/11/16.
 */

public class LocalDAO {
    private static final String URL = "http://192.168.2.104:8080/AlertaTCC/services/UsuarioDAO?wsdl";
    private static final String NAMESPACE = "http://DAO";
    private static final String INSERIR = "inserir";
    private static final String EXCLUIR = "excluir";
    private static final String ATUALIZAR = "atualizar";
    private static final String BUSCAR = "buscarPorId";

        public boolean inserir(Usuario usuario){

            SoapObject insert = new SoapObject(NAMESPACE,INSERIR);

            SoapObject usr = new SoapObject(NAMESPACE,"usuario");
            usr.addProperty("id", usuario.getId());
            usr.addProperty("nome", usuario.getNome());
            usr.addProperty("telefone", usuario.getTelefone());

            insert.addSoapObject(usr);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(insert);
            envelope.implicitTypes = true;

            HttpTransportSE http = new HttpTransportSE(URL);

            try {
                http.call("urn:" + INSERIR, envelope);

                SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
                return Boolean.parseBoolean(resposta.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean atualizar(Usuario usuario){
        return true;
        }

        public boolean excluir(Usuario usuario){

            return true;
        }

        public ArrayList<Usuario> buscarTodos(){
            ArrayList<Usuario> list = new ArrayList<>();

            return list;
        }

        public Usuario buscarPorId(int id){
            Usuario usuario = null;

            return usuario;
        }

}

