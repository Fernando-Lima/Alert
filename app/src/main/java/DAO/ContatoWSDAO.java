package DAO;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
    private static final String BUSCAR = "buscarPorId";

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
}
