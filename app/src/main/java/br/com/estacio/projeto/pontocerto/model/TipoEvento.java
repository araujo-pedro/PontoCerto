package br.com.estacio.projeto.pontocerto.model;

public enum TipoEvento {

    ENTRADA("Entrada"),
    INICIO_ALMOCO("Inicio Almoço"),
    FIM_ALMOCO("Fim Almoço"),
    SAIDA("Saida"),
    OUTRO("Outros Eventos");

    private String descricao;

    public String getDescricao() {

        return descricao;
    }

    TipoEvento(String descricao) {
        this.descricao = descricao;
    }


}
