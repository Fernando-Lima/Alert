package model;

/**
 * Created by fernando on 23/09/15.
 */
public class Contato {
    Long id;
    String nome;
    String telefone;
    String codPais;

    public Long getId() {
        return id;
    }

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

    public String getcodPais(){return codPais;}

    public void setcodPais(String codPais){ this.codPais = codPais;}

}
