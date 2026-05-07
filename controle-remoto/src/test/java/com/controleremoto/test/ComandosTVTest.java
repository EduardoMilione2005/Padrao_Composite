package com.controleremoto.test;

import com.controleremoto.command.Comando;
import com.controleremoto.command.ComandosTV;
import com.controleremoto.model.Televisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes: Comandos da TV")
class ComandosTVTest {

    private Televisao tv;

    @BeforeEach
    void setUp() {
        tv = new Televisao();
    }

    // ===================== LIGAR / DESLIGAR =====================

    @Test
    @DisplayName("LigarTV deve ligar a TV")
    void ligarTVDeveLigar() {
        Comando cmd = new ComandosTV.LigarTV(tv);
        cmd.executar();
        assertTrue(tv.isLigada());
    }

    @Test
    @DisplayName("LigarTV.desfazer deve desligar a TV")
    void ligarTVDesfazerDeveDesligar() {
        tv.ligar();
        Comando cmd = new ComandosTV.LigarTV(tv);
        cmd.desfazer();
        assertFalse(tv.isLigada());
    }

    @Test
    @DisplayName("DesligarTV deve desligar a TV")
    void desligarTVDeveDesligar() {
        tv.ligar();
        Comando cmd = new ComandosTV.DesligarTV(tv);
        cmd.executar();
        assertFalse(tv.isLigada());
    }

    @Test
    @DisplayName("DesligarTV.desfazer deve ligar a TV")
    void desligarTVDesfazerDeveLigar() {
        Comando cmd = new ComandosTV.DesligarTV(tv);
        cmd.desfazer();
        assertTrue(tv.isLigada());
    }

    // ===================== VOLUME =====================

    @Test
    @DisplayName("AumentarVolume deve aumentar volume em 1")
    void aumentarVolumeDeveIncrementar() {
        tv.ligar();
        int volumeInicial = tv.getVolume();
        new ComandosTV.AumentarVolume(tv).executar();
        assertEquals(volumeInicial + 1, tv.getVolume());
    }

    @Test
    @DisplayName("DiminuirVolume deve diminuir volume em 1")
    void diminuirVolumeDeveDecrementar() {
        tv.ligar();
        tv.setVolume(50);
        new ComandosTV.DiminuirVolume(tv).executar();
        assertEquals(49, tv.getVolume());
    }

    @Test
    @DisplayName("AumentarVolume não deve ultrapassar volume máximo")
    void aumentarVolumeNaoDeveUltrapassarMaximo() {
        tv.ligar();
        tv.setVolume(Televisao.getVolumeMax());
        new ComandosTV.AumentarVolume(tv).executar();
        assertEquals(Televisao.getVolumeMax(), tv.getVolume(), "Volume não deve ultrapassar máximo");
    }

    @Test
    @DisplayName("DiminuirVolume não deve ir abaixo do mínimo")
    void diminuirVolumeNaoDeveIrAbaixoMinimo() {
        tv.ligar();
        tv.setVolume(Televisao.getVolumeMin());
        new ComandosTV.DiminuirVolume(tv).executar();
        assertEquals(Televisao.getVolumeMin(), tv.getVolume(), "Volume não deve ir abaixo do mínimo");
    }

    @Test
    @DisplayName("DefinirVolume deve setar volume exato e desfazer deve restaurar")
    void definirVolumeDeveSetarEDesfazer() {
        tv.ligar();
        int volumeOriginal = tv.getVolume();
        Comando cmd = new ComandosTV.DefinirVolume(tv, 75);
        cmd.executar();
        assertEquals(75, tv.getVolume());
        cmd.desfazer();
        assertEquals(volumeOriginal, tv.getVolume(), "Volume deve voltar ao original após desfazer");
    }

    @Test
    @DisplayName("DefinirVolume deve lançar exceção para volume inválido")
    void definirVolumeInvalidoDeveLancarExcecao() {
        tv.ligar();
        Comando cmd = new ComandosTV.DefinirVolume(tv, 150);
        assertThrows(IllegalArgumentException.class, cmd::executar);
    }

    // ===================== CANAL =====================

    @Test
    @DisplayName("ProximoCanal deve avançar o canal")
    void proximoCanalDeveAvancar() {
        tv.ligar();
        int canalInicial = tv.getCanal();
        new ComandosTV.ProximoCanal(tv).executar();
        assertEquals(canalInicial + 1, tv.getCanal());
    }

    @Test
    @DisplayName("CanalAnterior deve voltar o canal")
    void canalAnteriorDeveVoltar() {
        tv.ligar();
        tv.setCanal(5);
        new ComandosTV.CanalAnterior(tv).executar();
        assertEquals(4, tv.getCanal());
    }

    @Test
    @DisplayName("ProximoCanal deve voltar ao canal mínimo ao passar do máximo")
    void proximoCanalDeveVoltarAoMinimo() {
        tv.ligar();
        tv.setCanal(Televisao.getCanalMax());
        new ComandosTV.ProximoCanal(tv).executar();
        assertEquals(Televisao.getCanalMin(), tv.getCanal(), "Canal deve fazer wrap para o mínimo");
    }

    @Test
    @DisplayName("CanalAnterior deve ir ao máximo ao tentar voltar do mínimo")
    void canalAnteriorDeveIrAoMaximo() {
        tv.ligar();
        tv.setCanal(Televisao.getCanalMin());
        new ComandosTV.CanalAnterior(tv).executar();
        assertEquals(Televisao.getCanalMax(), tv.getCanal(), "Canal deve fazer wrap para o máximo");
    }

    @Test
    @DisplayName("IrParaCanal deve ir ao canal específico e desfazer restaura")
    void irParaCanalDeveSetarEDesfazer() {
        tv.ligar();
        int canalOriginal = tv.getCanal();
        Comando cmd = new ComandosTV.IrParaCanal(tv, 50);
        cmd.executar();
        assertEquals(50, tv.getCanal());
        cmd.desfazer();
        assertEquals(canalOriginal, tv.getCanal(), "Canal deve voltar ao original após desfazer");
    }

    // ===================== COMANDO NULO =====================

    @Test
    @DisplayName("ComandoNulo não deve fazer nada nem lançar exceção")
    void comandoNuloNaoDeveFazerNada() {
        Televisao tvNova = new Televisao();
        Comando nulo = new ComandosTV.ComandoNulo();
        assertDoesNotThrow(nulo::executar);
        assertDoesNotThrow(nulo::desfazer);
        assertEquals("Nenhum Comando", nulo.getNome());
        assertFalse(tvNova.isLigada(), "TV não deve ser afetada pelo ComandoNulo");
    }

    // ===================== NOMES DOS COMANDOS =====================

    @Test
    @DisplayName("Todos os comandos devem ter nome não nulo e não vazio")
    void todosComandosDevemTerNome() {
        tv.ligar();
        tv.setVolume(50);
        tv.setCanal(5);
        Comando[] comandos = {
                new ComandosTV.LigarTV(tv),
                new ComandosTV.DesligarTV(tv),
                new ComandosTV.AumentarVolume(tv),
                new ComandosTV.DiminuirVolume(tv),
                new ComandosTV.DefinirVolume(tv, 30),
                new ComandosTV.ProximoCanal(tv),
                new ComandosTV.CanalAnterior(tv),
                new ComandosTV.IrParaCanal(tv, 10),
                new ComandosTV.ComandoNulo()
        };
        for (Comando cmd : comandos) {
            assertNotNull(cmd.getNome(), "Nome do comando não deve ser nulo: " + cmd.getClass().getSimpleName());
            assertFalse(cmd.getNome().isBlank(), "Nome do comando não deve estar vazio: " + cmd.getClass().getSimpleName());
        }
    }
}
