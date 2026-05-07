package com.controleremoto.model;

public class Televisao {

    private boolean ligada;
    private int volume;
    private int canal;
    private static final int VOLUME_MIN = 0;
    private static final int VOLUME_MAX = 100;
    private static final int CANAL_MIN = 1;
    private static final int CANAL_MAX = 999;

    public Televisao() {
        this.ligada = false;
        this.volume = 10;
        this.canal = 1;
    }

    public void ligar() {
        this.ligada = true;
    }

    public void desligar() {
        this.ligada = false;
    }

    public void aumentarVolume() {
        if (ligada && volume < VOLUME_MAX) {
            volume++;
        }
    }

    public void diminuirVolume() {
        if (ligada && volume > VOLUME_MIN) {
            volume--;
        }
    }

    public void setVolume(int volume) {
        if (volume < VOLUME_MIN || volume > VOLUME_MAX) {
            throw new IllegalArgumentException("Volume deve estar entre " + VOLUME_MIN + " e " + VOLUME_MAX);
        }
        this.volume = volume;
    }

    public void proximoCanal() {
        if (ligada) {
            canal = (canal >= CANAL_MAX) ? CANAL_MIN : canal + 1;
        }
    }

    public void canalAnterior() {
        if (ligada) {
            canal = (canal <= CANAL_MIN) ? CANAL_MAX : canal - 1;
        }
    }

    public void setCanal(int canal) {
        if (canal < CANAL_MIN || canal > CANAL_MAX) {
            throw new IllegalArgumentException("Canal deve estar entre " + CANAL_MIN + " e " + CANAL_MAX);
        }
        this.canal = canal;
    }

    public boolean isLigada() { return ligada; }
    public int getVolume() { return volume; }
    public int getCanal() { return canal; }

    public static int getVolumeMin() { return VOLUME_MIN; }
    public static int getVolumeMax() { return VOLUME_MAX; }
    public static int getCanalMin() { return CANAL_MIN; }
    public static int getCanalMax() { return CANAL_MAX; }
}
