package com.controleremoto.test;

import com.controleremoto.command.ComandosTV;
import com.controleremoto.composite.BotaoSimples;
import com.controleremoto.composite.ComponenteControle;
import com.controleremoto.composite.ControleRemoto;
import com.controleremoto.composite.GrupoBotoes;
import com.controleremoto.model.Televisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes: ControleRemoto (Raiz do Composite)")
class ControleRemotoTest {

    private Televisao tv;
    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        tv = new Televisao();
        controle = new ControleRemoto("Samsung Smart Remote");

        // Botão Power
        controle.adicionarComponente(new BotaoSimples("Power", "Liga/Desliga",
                new ComandosTV.LigarTV(tv), new ComandosTV.DesligarTV(tv)));

        // Grupo de Volume
        GrupoBotoes grupoVolume = new GrupoBotoes("Volume", "Controle de volume");
        grupoVolume.adicionar(new BotaoSimples("Vol+", "Aumenta volume",
                new ComandosTV.AumentarVolume(tv), new ComandosTV.DiminuirVolume(tv)));
        grupoVolume.adicionar(new BotaoSimples("Vol-", "Diminui volume",
                new ComandosTV.DiminuirVolume(tv), new ComandosTV.AumentarVolume(tv)));
        controle.adicionarComponente(grupoVolume);

        // Grupo de Canal
        GrupoBotoes grupoCanal = new GrupoBotoes("Canal", "Controle de canal");
        grupoCanal.adicionar(new BotaoSimples("CH+", "Próximo canal",
                new ComandosTV.ProximoCanal(tv), new ComandosTV.CanalAnterior(tv)));
        grupoCanal.adicionar(new BotaoSimples("CH-", "Canal anterior",
                new ComandosTV.CanalAnterior(tv), new ComandosTV.ProximoCanal(tv)));
        controle.adicionarComponente(grupoCanal);
    }

    @Test
    @DisplayName("Deve ter modelo correto")
    void deveTerModeloCorreto() {
        assertEquals("Samsung Smart Remote", controle.getModelo());
    }

    @Test
    @DisplayName("Deve ter 3 componentes na raiz (Power, Volume, Canal)")
    void deveTer3ComponentesNaRaiz() {
        assertEquals(3, controle.getTotalComponentes());
    }

    @Test
    @DisplayName("Deve ligar TV ao pressionar Power")
    void deveLigarTVAoPressionarPower() {
        assertFalse(tv.isLigada());
        controle.pressionar("Power");
        assertTrue(tv.isLigada(), "TV deve estar ligada após pressionar Power");
    }

    @Test
    @DisplayName("Deve registrar ação no histórico ao pressionar")
    void deveRegistrarHistoricoAoPressionar() {
        controle.pressionar("Power");
        assertFalse(controle.getHistoricoAcoes().isEmpty(), "Histórico não deve estar vazio");
        assertTrue(controle.getHistoricoAcoes().get(0).contains("Power"));
    }

    @Test
    @DisplayName("Deve limpar histórico corretamente")
    void deveLimparHistorico() {
        controle.pressionar("Power");
        controle.limparHistorico();
        assertTrue(controle.getHistoricoAcoes().isEmpty(), "Histórico deve estar vazio após limpar");
    }

    @Test
    @DisplayName("Deve buscar componente por nome (botão direto)")
    void deveBuscarBotaoPorNome() {
        ComponenteControle power = controle.buscarPorNome("Power");
        assertNotNull(power, "Power deve ser encontrado");
        assertEquals("Power", power.getNome());
    }

    @Test
    @DisplayName("Deve buscar componente por nome dentro de grupo")
    void deveBuscarComponenteDentroDeGrupo() {
        ComponenteControle volMais = controle.buscarPorNome("Vol+");
        assertNotNull(volMais, "Vol+ deve ser encontrado dentro do grupo Volume");
    }

    @Test
    @DisplayName("Deve retornar nulo para componente inexistente")
    void deveRetornarNuloParaComponenteInexistente() {
        ComponenteControle inexistente = controle.buscarPorNome("Botao Inexistente");
        assertNull(inexistente, "Deve retornar nulo para componente não encontrado");
    }

    @Test
    @DisplayName("Não deve permitir adicionar componente nulo")
    void naoDevePermitirComponenteNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> controle.adicionarComponente(null));
    }

    @Test
    @DisplayName("Deve remover componente do controle")
    void deveRemoverComponente() {
        ComponenteControle power = controle.buscarPorNome("Power");
        controle.removerComponente(power);
        assertEquals(2, controle.getTotalComponentes(), "Deve ter 2 componentes após remoção");
        assertNull(controle.buscarPorNome("Power"), "Power não deve ser encontrado após remoção");
    }

    @Test
    @DisplayName("Pressionar botão inexistente não deve lançar exceção")
    void pressionarBotaoInexistenteNaoDeveLancarExcecao() {
        assertDoesNotThrow(() -> controle.pressionar("Inexistente"));
        assertTrue(controle.getHistoricoAcoes().isEmpty(), "Histórico deve permanecer vazio");
    }

    @Test
    @DisplayName("Deve registrar tanto pressionar quanto soltar no histórico")
    void deveRegistrarPresionarESoltarNoHistorico() {
        controle.pressionar("Power");
        controle.soltar("Power");
        assertEquals(2, controle.getHistoricoAcoes().size());
        assertTrue(controle.getHistoricoAcoes().get(0).startsWith("Pressionado:"));
        assertTrue(controle.getHistoricoAcoes().get(1).startsWith("Solto:"));
    }
}
