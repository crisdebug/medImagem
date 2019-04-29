package com.example.samuel.medimagem;

public class Observacao {

    public static final String TIPO_AUDIO = "audio";
    public static final String TIPO_TEXTO = "texto";

    private long id;
    private Exam exame;
    private String tipo;
    private String conteudo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Exam getExame() {
        return exame;
    }

    public void setExame(Exam exame) {
        this.exame = exame;
    }
}
