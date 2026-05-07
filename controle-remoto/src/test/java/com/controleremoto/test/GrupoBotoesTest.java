package com.controleremoto.test;

import com.controleremoto.command.ComandosTV;
import com.controleremoto.composite.BotaoSimples;
import com.controleremoto.composite.ComponenteControle;
import com.controleremoto.composite.GrupoBotoes;
import com.controleremoto.model.Televisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes: GrupoBotoes (Composite Node)")
class GrupoBotoesTest {

    private Televisao tv;
    private GrupoBotoes grupoVolume;
    private BotaoSimples botaoVolumeMais;
    private BotaoSimples botaoVolumeMenos;

    @BeforeEach
    void setUp() {
        tv = new Televisao();
        tv.ligar();
        tv.setVolume(50);

        botaoVolumeMais = new BotaoSimples("Vol+", "Aumenta volume",
                new ComandosTV.AumentarVolume(tv), new ComandosTV.DiminuirVolume(tv));
        botaoVolumeMenos = new BotaoSimples("Vol-", "Diminui volume",
                new ComandosTV.DiminuirVolume(tv), new ComandosTV.AumentarVolume(tv));

        grupoVolume = new GrupoBotoes("Controle de Volume", "Grupo dos botões de volume");
        grupoVolume.adicionar(botaoVolumeMais);
        grupoVolume.adicionar(botaoVolumeMenos);
    }

    @Test
    @DisplayName("Deve conter dois filhos após adição")
    void deveConterDoisFilhos() {
        assertEquals(2, grupoVolume.getTotalFilhos(), "Grupo deve ter 2 filhos");
    }

    @Test
    @DisplayName("Ao pressionar grupo, todos os filhos devem ser acionados")
    void aoPressionarGrupoTodosFilhosDevemSerAcionados() {
        int volumeInicial = tv.getVolume(); // 50
        grupoVolume.pressionar();
        // Vol+ aumenta +1 (51), Vol- diminui -1 (50) → volume volta ao mesmo
        assertEquals(volumeInicial, tv.getVolume(),
                "Após pressionar grupo (Vol+ e Vol-), volume deve ser o mesmo");
    }

    @Test
    @DisplayName("Deve remover componente corretamente")
    void deveRemoverComponente() {
        grupoVolume.remover(botaoVolumeMenos);
        assertEquals(1, grupoVolume.getTotalFilhos(), "Grupo deve ter 1 filho após remoção");
        assertEquals("Vol+", grupoVolume.getFilhos().get(0).getNome());
    }

    @Test
    @DisplayName("Não deve permitir adicionar componente nulo")
    void naoDevePermitirComponenteNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> grupoVolume.adicionar(null),
                "Deve lançar exceção ao adicionar nulo");
    }

    @Test
    @DisplayName("Deve suportar grupos aninhados (Composite dentro de Composite)")
    void deveSuportarGruposAninhados() {
        GrupoBotoes grupoCanal = new GrupoBotoes("Controle de Canal", "Botões de canal");
        BotaoSimples proximoCanal = new BotaoSimples("CH+", "Próximo canal",
                new ComandosTV.ProximoCanal(tv), new ComandosTV.CanalAnterior(tv));
        grupoCanal.adicionar(proximoCanal);

        GrupoBotoes grupoGeral = new GrupoBotoes("Controle Geral", "Todos os controles");
        grupoGeral.adicionar(grupoVolume);
        grupoGeral.adicionar(grupoCanal);

        assertEquals(2, grupoGeral.getTotalFilhos(), "Grupo geral deve ter 2 grupos filhos");

        int canalInicial = tv.getCanal();
        grupoGeral.pressionar();
        // Vol+ (+1), Vol- (-1) → volume igual; CH+ avança canal
        assertEquals(canalInicial + 1, tv.getCanal(),
                "Canal deve avançar ao pressionar grupo geral com CH+");
    }

    @Test
    @DisplayName("Deve retornar lista imutável de filhos")
    void deveRetornarListaImutavelDeFilhos() {
        assertThrows(UnsupportedOperationException.class,
                () -> grupoVolume.getFilhos().add(botaoVolumeMais),
                "Lista de filhos deve ser imutável");
    }

    @Test
    @DisplayName("Deve retornar nome e descrição corretamente")
    void deveRetornarNomeEDescricao() {
        assertEquals("Controle de Volume", grupoVolume.getNome());
        assertEquals("Grupo dos botões de volume", grupoVolume.getDescricao());
    }

    @Test
    @DisplayName("Grupo vazio não deve lançar exceção ao pressionar")
    void grupoVazioNaoDeveLancarExcecao() {
        GrupoBotoes grupoVazio = new GrupoBotoes("Vazio", "Sem filhos");
        assertDoesNotThrow(grupoVazio::pressionar);
        assertDoesNotThrow(grupoVazio::soltar);
    }

    @Test
    @DisplayName("Ao soltar grupo, todos os filhos devem ser soltos")
    void aoSoltarGrupoTodosFilhosDevemSerSoltos() {
        int volumeInicial = tv.getVolume(); // 50
        grupoVolume.soltar();
        // soltar Vol+ executa DiminuirVolume (49), soltar Vol- executa AumentarVolume (50)
        assertEquals(volumeInicial, tv.getVolume(),
                "Volume deve ser o mesmo após soltar grupo (simetria)");
    }
}
