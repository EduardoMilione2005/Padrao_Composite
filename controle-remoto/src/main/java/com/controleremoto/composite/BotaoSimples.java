package com.controleremoto.composite;

import com.controleremoto.command.Comando;

public class BotaoSimples implements ComponenteControle {

    private final String nome;
    private final String descricao;
    private final Comando comandoPressionar;
    private final Comando comandoSoltar;
    private boolean pressionado;

    public BotaoSimples(String nome, String descricao,
                        Comando comandoPressionar, Comando comandoSoltar) {
        this.nome = nome;
        this.descricao = descricao;
        this.comandoPressionar = comandoPressionar;
        this.comandoSoltar = comandoSoltar;
        this.pressionado = false;
    }

    @Override
    public void pressionar() {
        this.pressionado = true;
        if (comandoPressionar != null) {
            comandoPressionar.executar();
        }
    }

    @Override
    public void soltar() {
        this.pressionado = false;
        if (comandoSoltar != null) {
            comandoSoltar.executar();
        }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public boolean isPressionado() {
        return pressionado;
    }

    public Comando getComandoPressionar() {
        return comandoPressionar;
    }

    public Comando getComandoSoltar() {
        return comandoSoltar;
    }
}
