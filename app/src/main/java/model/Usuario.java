package model;

/**
 * Created by fernando on 17/10/16.
 */

public class Usuario {
    Long id;
    private String nome;
    private String telefone;
    private String telefoneContato;
    private String latitude;
    private String longitude;
    private Integer cod;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public String getTelefoneContato() {return telefoneContato;}

    public void setTelefoneContato(String telefoneContato) {this.telefoneContato = telefoneContato;}

    public String getLatitude() {return latitude;}

    public void setLatitude(String latitude) {this.latitude = latitude;}

    public String getLongitude() {return longitude;}

    public void setLongitude(String longitude) {this.longitude = longitude;}

    public Integer getCod() {return cod;}

    public void setCod(Integer cod) {this.cod = cod;}

    public Usuario(Long id, String nome, String telefone, String latitude, String longitude, Integer cod) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cod = cod;
    }
    public  Usuario(){

    }

}
