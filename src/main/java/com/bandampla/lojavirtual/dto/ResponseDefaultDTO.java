package com.bandampla.lojavirtual.dto;

public class ResponseDefaultDTO<T> {

    private String mensagem;
    private T dados;

    public ResponseDefaultDTO(String mensagem, T dados) {
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}
