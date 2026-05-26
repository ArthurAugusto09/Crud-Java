package com.template;

public class StreamerDTO {
    private int id;
    private String nome;
    private String conteudo;
    private String plataformaTransmissao;
    private int qtndSeguidores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getPlataformaTransmissao() {
        return plataformaTransmissao;
    }

    public void setPlataformaTransmissao(String plataformaTransmissao) {
        this.plataformaTransmissao = plataformaTransmissao;
    }

    public int getQtndSeguidores() {
        return qtndSeguidores;
    }

    public void setQtndSeguidores(int qtndSeguidores) {
        this.qtndSeguidores = qtndSeguidores;
    }
}
