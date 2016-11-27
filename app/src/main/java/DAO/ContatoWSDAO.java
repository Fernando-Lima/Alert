package DAO;

import android.database.Cursor;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.Usuario;

/**
 * Created by fernando on 15/11/16.
 */

public class ContatoWSDAO {
    private static final String URL = "http://192.168.2.104:8080/AlertaTCC/services/ContatoDAO?wsdl";
    private static final String NAMESPACE = "http://DAO";
    private static final String INSERIR = "inserir";
    private static final String EXCLUIR = "excluir";
    private static final String ATUALIZAR = "atualizar";
    private static final String BUSCAR = "buscarTodos";
    private static final String BUSCARPORID = "buscarPorId";

    public boolean inserir (Usuario usuario){

        SoapObject insert = new SoapObject(NAMESPACE,INSERIR);

        SoapObject cto = new SoapObject(NAMESPACE,"contato");
        cto.addProperty("id",usuario.getId());
        cto.addProperty("nome",usuario.getNome());
        cto.addProperty("telefone",usuario.getTelefone());
        cto.addProperty("telefoneUsuario", usuario.getTelefoneContato());
        cto.addProperty("latitude", usuario.getLatitude());
        cto.addProperty("longitude", usuario.getLongitude());
        cto.addProperty("data",usuario.getData());

        insert.addSoapObject(cto);

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
    public boolean atualizar(){

        return true;
    }

    public ArrayList<Usuario> buscarTodos(String telefoneUsuario){
        ArrayList<Usuario> list = new ArrayList<>();

        SoapObject buscarUsuarios = new SoapObject(NAMESPACE,BUSCAR);
        buscarUsuarios.addProperty("telefoneUsuario", telefoneUsuario);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuarios);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + BUSCAR, envelope);

            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();

            if(resposta != null){
                for (SoapObject soapObject : resposta) {
                    Usuario usuario = new Usuario();
                    //usuario.setId((Long) soapObject.getProperty("id"));
                    usuario.setNome(soapObject.getProperty("nome").toString());
                    usuario.setData(soapObject.getProperty("enviarData").toString());
                    usuario.setTelefoneContato(soapObject.getProperty("telefone").toString());
                    usuario.setLatitude(soapObject.getProperty("latitude").toString());
                    usuario.setLongitude(soapObject.getProperty("longitude").toString());
                    list.add(usuario);
                }
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public Usuario buscarUsuarioPorId(int id){
        Usuario usuario = null;

        SoapObject buscarUsuarios = new SoapObject(NAMESPACE,BUSCARPORID);
        buscarUsuarios.addProperty("id",id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuarios);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + BUSCARPORID, envelope);

            SoapObject resposta = (SoapObject) envelope.getResponse();


            usuario = new Usuario();

            usuario.setNome(resposta.getProperty("nome").toString());
                // usuario.setData(soapObject.getProperty("data").toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return usuario;
    }
}
