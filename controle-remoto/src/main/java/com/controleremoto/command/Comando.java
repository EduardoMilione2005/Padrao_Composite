package com.controleremoto.command;

public interface Comando {
    void executar();
    void desfazer();
    String getNome();
}
