# 📺 Controle Remoto — Padrão Composite

Projeto Java que demonstra a aplicação do **Padrão de Projeto Composite** em um sistema de controle remoto de TV.

---

## 🏗️ Estrutura do Projeto

```
controle-remoto/
├── pom.xml
├── diagrama-classes.mermaid
└── src/main/java/com/controleremoto/
    ├── command/
    │   ├── Comando.java            ← Interface Command
    │   └── ComandosTV.java         ← Comandos concretos (inner classes)
    ├── composite/
    │   ├── ComponenteControle.java ← Interface Componente (Composite)
    │   ├── BotaoSimples.java       ← Leaf (folha)
    │   ├── GrupoBotoes.java        ← Composite (nó)
    │   └── ControleRemoto.java     ← Raiz do Composite
    ├── model/
    │   └── Televisao.java          ← Dispositivo controlado
    └── test/
        ├── TelevisaoTest.java
        ├── ComandosTVTest.java
        ├── BotaoSimplesTest.java
        ├── GrupoBotoesTest.java
        └── ControleRemotoTest.java
```

---

## 🎨 Padrão Composite Aplicado

| Papel Composite        | Classe                |
|------------------------|-----------------------|
| **Component**          | `ComponenteControle`  |
| **Leaf (Folha)**       | `BotaoSimples`        |
| **Composite (Nó)**     | `GrupoBotoes`         |
| **Client**             | `ControleRemoto`      |

### Hierarquia de exemplo:
```
ControleRemoto (Samsung Smart Remote)
├── BotaoSimples (Power)
├── GrupoBotoes (Volume)
│   ├── BotaoSimples (Vol+)
│   └── BotaoSimples (Vol-)
└── GrupoBotoes (Canal)
    ├── BotaoSimples (CH+)
    └── BotaoSimples (CH-)
```

---

## ▶️ Executar os testes

```bash
mvn test
```

---

## 📊 Casos de Teste

| Classe de Teste         | Nº de Testes | O que cobre                                           |
|-------------------------|:------------:|-------------------------------------------------------|
| `TelevisaoTest`         | 10           | Estado inicial, ligar/desligar, limites de volume/canal |
| `ComandosTVTest`        | 15           | Execução e desfazer de todos os comandos              |
| `BotaoSimplesTest`      | 7            | Leaf: pressionar, soltar, null-safety, nomes          |
| `GrupoBotoesTest`       | 8            | Composite: filhos, aninhamento, imutabilidade         |
| `ControleRemotoTest`    | 11           | Raiz: busca, histórico, remoção, edge cases           |
| **Total**               | **51**       |                                                       |

---

## 🛠️ Tecnologias

- **Java 17**
- **JUnit 5** (Jupiter)
- **Maven**
