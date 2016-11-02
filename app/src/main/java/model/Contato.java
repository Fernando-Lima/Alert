package model;

/**
 * Created by fernando on 23/09/15.
 */
public class Contato {
    Long id;
    String nome;
    String telefone;
    Double latitude;
    Double longitude;
    int principal;
    int local;


    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getLatitude() {return latitude;}

    public void setLatitude(Double latitude){this.latitude = latitude;}

    public Double getLongitude(){return longitude;}

    public void setLongitude(Double longitude){this.longitude = longitude;}

    public int getPrincipal() {return principal;}

    public void setPrincipal(int principal) {this.principal = principal;}

    public int getLocal() {return local;}

    public void setLocal(int local) {this.local = local;}

}
