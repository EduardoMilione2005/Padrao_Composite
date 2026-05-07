package com.controleremoto.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrupoBotoes implements ComponenteControle {

    private final String nome;
    private final String descricao;
    private final List<ComponenteControle> filhos;

    public GrupoBotoes(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.filhos = new ArrayList<>();
    }

    public void adicionar(ComponenteControle componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente não pode ser nulo.");
        }
        filhos.add(componente);
    }

    public void remover(ComponenteControle componente) {
        filhos.remove(componente);
    }

    public List<ComponenteControle> getFilhos() {
        return Collections.unmodifiableList(filhos);
    }

    public int getTotalFilhos() {
        return filhos.size();
    }

    @Override
    public void pressionar() {
        for (ComponenteControle filho : filhos) {
            filho.pressionar();
        }
    }

    @Override
    public void soltar() {
        for (ComponenteControle filho : filhos) {
            filho.soltar();
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
}
