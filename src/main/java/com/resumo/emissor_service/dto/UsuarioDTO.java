package com.resumo.emissor_service.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private String nome;
    private String matricula;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

      public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
