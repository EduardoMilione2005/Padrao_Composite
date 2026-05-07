package com.controleremoto.test;

import com.controleremoto.model.Televisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes: Televisao (Model)")
class TelevisaoTest {

    private Televisao tv;

    @BeforeEach
    void setUp() {
        tv = new Televisao();
    }

    @Test
    @DisplayName("TV deve iniciar desligada com volume 10 e canal 1")
    void tvDeveIniciarComEstadoCorreto() {
        assertFalse(tv.isLigada(), "TV deve iniciar desligada");
        assertEquals(10, tv.getVolume(), "Volume inicial deve ser 10");
        assertEquals(1, tv.getCanal(), "Canal inicial deve ser 1");
    }

    @Test
    @DisplayName("Deve ligar e desligar a TV corretamente")
    void deveLigarEDesligar() {
        tv.ligar();
        assertTrue(tv.isLigada());
        tv.desligar();
        assertFalse(tv.isLigada());
    }

    @Test
    @DisplayName("Não deve alterar volume quando a TV estiver desligada")
    void naoDeveAlterarVolumeComTVDesligada() {
        int volumeInicial = tv.getVolume();
        tv.aumentarVolume();
        tv.diminuirVolume();
        assertEquals(volumeInicial, tv.getVolume(), "Volume não deve mudar com TV desligada");
    }

    @Test
    @DisplayName("Não deve alterar canal quando a TV estiver desligada")
    void naoDeveAlterarCanalComTVDesligada() {
        int canalInicial = tv.getCanal();
        tv.proximoCanal();
        tv.canalAnterior();
        assertEquals(canalInicial, tv.getCanal(), "Canal não deve mudar com TV desligada");
    }

    @Test
    @DisplayName("Deve aceitar volume entre 0 e 100")
    void deveAceitarVolumeValido() {
        assertDoesNotThrow(() -> tv.setVolume(0));
        assertDoesNotThrow(() -> tv.setVolume(100));
        assertDoesNotThrow(() -> tv.setVolume(50));
    }

    @Test
    @DisplayName("Deve rejeitar volume fora do intervalo [0, 100]")
    void deveRejeitarVolumeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tv.setVolume(-1));
        assertThrows(IllegalArgumentException.class, () -> tv.setVolume(101));
    }

    @Test
    @DisplayName("Deve aceitar canal entre 1 e 999")
    void deveAceitarCanalValido() {
        assertDoesNotThrow(() -> tv.setCanal(1));
        assertDoesNotThrow(() -> tv.setCanal(999));
        assertDoesNotThrow(() -> tv.setCanal(100));
    }

    @Test
    @DisplayName("Deve rejeitar canal fora do intervalo [1, 999]")
    void deveRejeitarCanalInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tv.setCanal(0));
        assertThrows(IllegalArgumentException.class, () -> tv.setCanal(1000));
    }

    @Test
    @DisplayName("Volume deve fazer wrap ao chegar no máximo (não ultrapassa)")
    void volumeNaoUltrapassaMaximo() {
        tv.ligar();
        tv.setVolume(100);
        tv.aumentarVolume();
        assertEquals(100, tv.getVolume(), "Volume não deve ultrapassar 100");
    }

    @Test
    @DisplayName("Volume deve respeitar mínimo (não fica negativo)")
    void volumeNaoFicaNegativo() {
        tv.ligar();
        tv.setVolume(0);
        tv.diminuirVolume();
        assertEquals(0, tv.getVolume(), "Volume não deve ficar negativo");
    }
}
