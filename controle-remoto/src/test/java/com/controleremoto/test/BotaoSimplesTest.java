package com.controleremoto.test;

import com.controleremoto.command.Comando;
import com.controleremoto.command.ComandosTV;
import com.controleremoto.composite.BotaoSimples;
import com.controleremoto.model.Televisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes: BotaoSimples (Leaf do Composite)")
class BotaoSimplesTest {

    private Televisao tv;
    private BotaoSimples botaoLigar;
    private BotaoSimples botaoVolumeMais;

    @BeforeEach
    void setUp() {
        tv = new Televisao();
        Comando ligar = new ComandosTV.LigarTV(tv);
        Comando desligar = new ComandosTV.DesligarTV(tv);
        botaoLigar = new BotaoSimples("Power", "Liga/Desliga a TV", ligar, desligar);

        tv.ligar();
        Comando aumentar = new ComandosTV.AumentarVolume(tv);
        Comando diminuir = new ComandosTV.DiminuirVolume(tv);
        botaoVolumeMais = new BotaoSimples("Vol+", "Aumenta o volume", aumentar, diminuir);
    }

    @Test
    @DisplayName("Deve ligar a TV ao pressionar botão Power")
    void deveLigarTVAoPressionar() {
        assertFalse(tv.isLigada() && botaoLigar.isPressionado());

        // Cria TV desligada e pressiona
        Televisao tvNova = new Televisao();
        BotaoSimples power = new BotaoSimples("Power", "Liga TV",
                new ComandosTV.LigarTV(tvNova), new ComandosTV.DesligarTV(tvNova));

        power.pressionar();

        assertTrue(tvNova.isLigada(), "TV deve estar ligada após pressionar Power");
        assertTrue(power.isPressionado(), "Botão deve estar marcado como pressionado");
    }

    @Test
    @DisplayName("Deve desligar a TV ao soltar botão Power (toggle)")
    void deveDesligarTVAoSoltar() {
        Televisao tvNova = new Televisao();
        tvNova.ligar();
        BotaoSimples power = new BotaoSimples("Power", "Liga/Desliga",
                new ComandosTV.LigarTV(tvNova), new ComandosTV.DesligarTV(tvNova));

        power.soltar();

        assertFalse(tvNova.isLigada(), "TV deve estar desligada após soltar Power");
        assertFalse(power.isPressionado(), "Botão deve estar marcado como não pressionado");
    }

    @Test
    @DisplayName("Deve aumentar volume ao pressionar Vol+")
    void deveAumentarVolumeAoPressionar() {
        int volumeInicial = tv.getVolume();
        botaoVolumeMais.pressionar();
        assertEquals(volumeInicial + 1, tv.getVolume(), "Volume deve aumentar em 1");
    }

    @Test
    @DisplayName("Deve diminuir volume ao soltar Vol+ (desfazer)")
    void deveDiminuirVolumeAoSoltar() {
        int volumeInicial = tv.getVolume();
        botaoVolumeMais.pressionar();
        botaoVolumeMais.soltar();
        assertEquals(volumeInicial, tv.getVolume(), "Volume deve voltar ao original após soltar");
    }

    @Test
    @DisplayName("Deve retornar nome e descrição corretamente")
    void deveRetornarNomeEDescricao() {
        assertEquals("Power", botaoLigar.getNome());
        assertEquals("Liga/Desliga a TV", botaoLigar.getDescricao());
    }

    @Test
    @DisplayName("Botão com comando nulo não deve lançar exceção")
    void botaoComComandoNuloNaoDeveLancarExcecao() {
        BotaoSimples botaoVazio = new BotaoSimples("Vazio", "Sem ação", null, null);
        assertDoesNotThrow(botaoVazio::pressionar);
        assertDoesNotThrow(botaoVazio::soltar);
    }

    @Test
    @DisplayName("Deve retornar o comando pressionar corretamente")
    void deveRetornarComandoPressionar() {
        assertNotNull(botaoLigar.getComandoPressionar());
        assertEquals("Ligar TV", botaoLigar.getComandoPressionar().getNome());
    }
}
