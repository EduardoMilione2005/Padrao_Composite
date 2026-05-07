package com.controleremoto.command;

import com.controleremoto.model.Televisao;

public class ComandosTV {

    public static class LigarTV implements Comando {
        private final Televisao tv;

        public LigarTV(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.ligar(); }
        @Override public void desfazer() { tv.desligar(); }
        @Override public String getNome() { return "Ligar TV"; }
    }

    public static class DesligarTV implements Comando {
        private final Televisao tv;

        public DesligarTV(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.desligar(); }
        @Override public void desfazer() { tv.ligar(); }
        @Override public String getNome() { return "Desligar TV"; }
    }

    public static class AumentarVolume implements Comando {
        private final Televisao tv;

        public AumentarVolume(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.aumentarVolume(); }
        @Override public void desfazer() { tv.diminuirVolume(); }
        @Override public String getNome() { return "Aumentar Volume"; }
    }

    public static class DiminuirVolume implements Comando {
        private final Televisao tv;

        public DiminuirVolume(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.diminuirVolume(); }
        @Override public void desfazer() { tv.aumentarVolume(); }
        @Override public String getNome() { return "Diminuir Volume"; }
    }

    public static class DefinirVolume implements Comando {
        private final Televisao tv;
        private final int novoVolume;
        private int volumeAnterior;

        public DefinirVolume(Televisao tv, int novoVolume) {
            this.tv = tv;
            this.novoVolume = novoVolume;
        }

        @Override public void executar() {
            volumeAnterior = tv.getVolume();
            tv.setVolume(novoVolume);
        }
        @Override public void desfazer() { tv.setVolume(volumeAnterior); }
        @Override public String getNome() { return "Definir Volume para " + novoVolume; }
    }

    public static class ProximoCanal implements Comando {
        private final Televisao tv;

        public ProximoCanal(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.proximoCanal(); }
        @Override public void desfazer() { tv.canalAnterior(); }
        @Override public String getNome() { return "Próximo Canal"; }
    }

    public static class CanalAnterior implements Comando {
        private final Televisao tv;

        public CanalAnterior(Televisao tv) { this.tv = tv; }

        @Override public void executar() { tv.canalAnterior(); }
        @Override public void desfazer() { tv.proximoCanal(); }
        @Override public String getNome() { return "Canal Anterior"; }
    }

    public static class IrParaCanal implements Comando {
        private final Televisao tv;
        private final int canal;
        private int canalAnterior;

        public IrParaCanal(Televisao tv, int canal) {
            this.tv = tv;
            this.canal = canal;
        }

        @Override public void executar() {
            canalAnterior = tv.getCanal();
            tv.setCanal(canal);
        }
        @Override public void desfazer() { tv.setCanal(canalAnterior); }
        @Override public String getNome() { return "Ir para Canal " + canal; }
    }


    public static class ComandoNulo implements Comando {
        @Override public void executar() { /* nenhuma ação */ }
        @Override public void desfazer() { /* nenhuma ação */ }
        @Override public String getNome() { return "Nenhum Comando"; }
    }
}
