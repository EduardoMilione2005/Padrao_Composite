package com.controleremoto.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControleRemoto {

    private final String modelo;
    private final List<ComponenteControle> componentes;
    private final List<String> historicoAcoes;

    public ControleRemoto(String modelo) {
        this.modelo = modelo;
        this.componentes = new ArrayList<>();
        this.historicoAcoes = new ArrayList<>();
    }

    public void adicionarComponente(ComponenteControle componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente não pode ser nulo.");
        }
        componentes.add(componente);
    }

    public void removerComponente(ComponenteControle componente) {
        componentes.remove(componente);
    }

    public ComponenteControle buscarPorNome(String nome) {
        for (ComponenteControle c : componentes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
            if (c instanceof GrupoBotoes grupo) {
                for (ComponenteControle filho : grupo.getFilhos()) {
                    if (filho.getNome().equalsIgnoreCase(nome)) {
                        return filho;
                    }
                }
            }
        }
        return null;
    }

    public void pressionar(String nomeComponente) {
        ComponenteControle componente = buscarPorNome(nomeComponente);
        if (componente != null) {
            componente.pressionar();
            historicoAcoes.add("Pressionado: " + nomeComponente);
        }
    }

    public void soltar(String nomeComponente) {
        ComponenteControle componente = buscarPorNome(nomeComponente);
        if (componente != null) {
            componente.soltar();
            historicoAcoes.add("Solto: " + nomeComponente);
        }
    }

    public List<ComponenteControle> getComponentes() {
        return Collections.unmodifiableList(componentes);
    }

    public List<String> getHistoricoAcoes() {
        return Collections.unmodifiableList(historicoAcoes);
    }

    public void limparHistorico() {
        historicoAcoes.clear();
    }

    public String getModelo() {
        return modelo;
    }

    public int getTotalComponentes() {
        return componentes.size();
    }
}
