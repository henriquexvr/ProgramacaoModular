# UML - Guia Completo para Diagramas de Classes

## O QUE É UML?

UML (Unified Modeling Language) é uma **linguagem visual** para descrever como o sistema funciona. É como um "mapa" do código.

**Para que serve na prova:** Desenhar como as classes se comunicam, quem tem o quê, e quem depende de quem.

---

## DIAGRAMA DE CLASSES - PARTES

Um diagrama de classes tem **3 partes**:

```
+------------------+
|   NomeClasse     |  ← Nome da classe
+------------------+
| - atributo1: tipo|  ← Atributos (dados)
| - atributo2: tipo|
+------------------+
| + metodo1(): tipo|  ← Métodos (comportamento)
| + metodo2(): tipo|
+------------------+
```

### Exemplo Completo

```
+------------------+
|      Pizza       |
+------------------+
| - preco: double  |
| - ingredientes: int|
+------------------+
| + valorAPagar(): double|
| + adicionarIngredientes(qtd: int): int|
+------------------+
```

---

## MODIFICADORES DE ACESSO

| Símbolo | Significado | Exemplo |
|---------|-------------|---------|
| `+` | **public** - acessível por todos | `+ nome: String` |
| `-` | **private** - acessível só pela classe | `- preco: double` |
| `#` | **protected** - acessível pela classe e filhas | `# desconto: double` |
| `~` | **package** - acessível pelo mesmo pacote | `~ id: int` |

### Exemplo

```
+------------------+
|      Pedido      |
+------------------+
| - id: int        |  ← private - só Pedido acessa
| # itens: List    |  ← protected - Pedido e filhas acessam
| ~ data: LocalDate|  ← package - mesmo pacote acessa
| + aberto: boolean|  ← public - todos acessam
+------------------+
```

---

## RELACIONAMENTOS ENTRE CLASSES

### 1. ASSOCIAÇÃO (seta simples →)

Uma classe **usa** outra, sem posse.

```
+--------+       +--------+
| Pedido |  ───> | Cliente|
+--------+       +--------+
```

**Exemplo:** Pedido usa Cliente para consultar dados.

**Palavra-chave:** "USA"

```
 Pedido usa Cliente
     │
     │  usa
     ▼
+--------+       +--------+
| Pedido |  ───> | Cliente|
+--------+       +--------+
```

---

### 2. AGREGAÇÃO (losango vazio ◇)

Uma classe **TEM** outra, mas a outra pode existir sozinha.

```
+--------+  ◇──── +--------+
| Turma  |        | Aluno  |
+--------+        +--------+
```

**Exemplo:** Turma tem Alunos, mas Aluno existe mesmo sem Turma.

**Palavra-chave:** "TEM"

**O losango fica do lado de quem TEM/POSSUI.**

```
+--------+  ◇──── +--------+
| Turma  |        | Aluno  |
+--------+        +--------+
     │                 │
     │ TEM             │ EXISTE SOZINHO
     └─────────────────┘
```

---

### 3. CONTENÇÃO / COMPOSIÇÃO (losango cheio ◆)

Uma classe **CONTÉM** outra, e a outra **NÃO existe** sem a primeira.

```
+--------+  ◆──── +--------+
| Pedido |        | Pizza  |
+--------+        +--------+
```

**Exemplo:** Pedido contém Pizzas. Se o Pedido some, as Pizzas também.

**Palavra-chave:** "CONTÉM"

**O losango fica do lado de quem CONTÉM.**

```
+--------+  ◆──── +--------+
| Pedido |        | Pizza  |
+--------+        +--------+
     │                 │
     │ CONTÉM          │ NÃO EXISTE SOZINHA
     └─────────────────┘
```

---

### DIFERENÇA: AGREGAÇÃO vs CONTENÇÃO

| Tipo | Losango | Quando usar | Exemplo |
|------|---------|-------------|---------|
| **Agregação** | Vazio ◇ | "TEM" - a outra existe sozinha | Turma tem Aluno |
| **Contenção** | Cheio ◆ | "CONTÉM" - a outra não existe sozinha | Pedido contém Pizza |

**Teste rápido:**
- Se o "dono" sumir, a "parte" continua existindo? **SIM** → Agregação
- Se o "donos" sumir, a "parte" some junto? **SIM** → Contenção

---

## HERANÇA (seta com triângulo)

Classe filha herda da classe mãe. Seta com **triângulo vazio** apontando para a mãe.

```
+--------+
| Animal |
+--------+
    △
    │
+--------+        +--------+
| Cachorro|        |  Gato  |
+--------+        +--------+
```

**Exemplo:** Cachorro É UM Animal. Gato É UM Animal.

**Palavra-chave:** "É UM"

```
    +--------+
    | Animal |
    +--------+
        △
       / \
      /   \
+--------+  +--------+
|Cachorro|  |  Gato  |
+--------+  +--------+
```

---

## IMPLEMENTAÇÃO DE INTERFACE (seta com triângulo tracejado)

Classe implementa uma interface. Seta com **triângulo vazio tracejado**.

```
+------------------+
| <<interface>>    |
|    IProduto      |
+------------------+
        △
        │
+--------+        +----------+
|  Pizza |        |Sanduiche |
+--------+        +----------+
```

**Exemplo:** Pizza IMPLEMENTA IProduto. Sanduiche IMPLEMENTA IProduto.

**Palavra-chave:** "IMPLEMENTA"

```
+------------------+
| <<interface>>    |
|    IProduto      |
+------------------+
        △
       / \
      /   \
+--------+  +----------+
|  Pizza |  |Sanduiche |
+--------+  +----------+
```

---

## ENUM (enum)

Enum é representado com `<<enum>>` acima do nome.

```
+---------------+
| <<enum>>      |
|   EBorda      |
+---------------+
| TRADICIONAL   |
| CHOCOLATE     |
| CHEDDAR       |
| CATUPiry      |
+---------------+
```

---

## COMO LER UM DIAGRAMA UML

### Exemplo Completo

```
                    +------------------+
                    | <<interface>>    |
                    |    IProduto      |
                    +------------------+
                            △
                           / \
                          /   \
            +-------------+   +----------------+
            | Pizza       |   | Sanduiche      |
            +-------------+   +----------------+
            | - preco: double| | - preco: double |
            +-------------+   +----------------+
            | + valorAPagar()| | + valorAPagar() |
            +-------------+   +----------------+
                    │                   │
                    │                   │
                    ▼                   ▼
            +------------------+  +------------------+
            | <<enum>>        |  | <<enum>>        |
            |    EBorda       |  |    ESobremesa   |
            +------------------+  +------------------+
            | TRADICIONAL     |  | SORVETE         |
            | CHOCOLATE       |  | PUDDING         |
            +------------------+  +------------------+

            +------------------+
            |     Pedido       |
            +------------------+
            | - id: int        |
            | - itens: List    |
            | - aberto: boolean|
            +------------------+
            | + adicionar()    |
            | + fechar()       |
            +------------------+
                    │
                    │ ◆ (contém)
                    ▼
            +------------------+
            |     Pizza        |
            +------------------+
```

### Lendo o Diagrama:

1. **IProduto** é uma interface
2. **Pizza** e **Sanduiche** implementam IProduto
3. **Pedido** contém (◆) Pizza (Contenção)
4. **EBorda** e **ESobremesa** são enums
5. Pedido tem atributos private (-) e métodos públicos (+)

---

## COMO CRIAR UM DIAGRAMA UML

### Passo 1: Identificar as Classes

Leia o enunciado e marque os **substantivos**. Cada substantivo = uma classe.

```
"Um Pedido pode conter diversas Pizzas. Cada Pizza tem um preço."

Substantivos: Pedido, Pizza
→ Criar classes: Pedido, Pizza
```

### Passo 2: Identificar os Atributos

Marque o que cada classe **guarda** (dados).

```
Pedido guarda: id, data, lista de pizzas, aberto
Pizza guarda: preço, quantidade de ingredientes, borda
```

### Passo 3: Identificar os Métodos

Marque o que cada classe **faz** (comportamento).

```
Pedido faz: adicionarPizza(), fecharPedido(), calcularPreco()
Pizza faz: valorAPagar(), adicionarIngredientes()
```

### Passo 4: Identificar os Relacionamentos

Pergunte:

| Pergunta | Relacionamento |
|----------|----------------|
| Classe A **usa** Classe B? | Associação (→) |
| Classe A **TEM** Classe B? | Agregação (◇) |
| Classe A **CONTÉM** Classe B? | Contenção (◆) |
| Classe A **É UM** Classe B? | Herança (△) |
| Classe A **IMPLEMENTA** Interface B? | Implementação (△ tracejado) |

### Passo 5: Desenhar

1. Coloque as classes em retângulos
2. Adicione atributos e métodos
3. Desenhe os relacionamentos com os símbolos corretos
4. O losango sempre fica do lado de quem TEM/CONTÉM

---

## EXEMPLO PRÁTICO: XULAMBS

### Enunciado

"Pizza tem preço base e até 8 ingredientes. Pedido pode conter várias pizzas. Pedido pode ser Local ou Entrega."

### Passo 1: Classes

- Pizza
- Pedido
- PedidoLocal (filha de Pedido)
- PedidoEntrega (filha de Pedido)

### Passo 2: Atributos

- Pizza: precoBase, quantidadeIngredientes, borda
- Pedido: id, data, itens, aberto
- PedidoEntrega: distanciaEntrega
- PedidoLocal: (nenhum extra)

### Passo 3: Métodos

- Pizza: valorAPagar(), adicionarIngredientes()
- Pedido: adicionar(), fecharPedido(), precoAPagar() [abstrato]
- PedidoEntrega: precoAPagar(), podeAdicionar()
- PedidoLocal: precoAPagar()

### Passo 4: Relacionamentos

- PedidoLocal **É UM** Pedido → Herança
- PedidoEntrega **É UM** Pedido → Herança
- Pedido **CONTÉM** Pizza → Contenção (◆)
- Pizza **IMPLEMENTA** IProduto → Implementação

### Diagrama Resultante

```
+------------------+
| <<interface>>    |
|    IProduto      |
+------------------+
        △
        │
+--------+         +------------------+
|  Pizza | ◆──────|     Pedido       |
+--------+         +------------------+
| - precoBase: double| | - id: int        |
| - qtdAdicionais: int| | - data: LocalDate |
| - borda: EBorda  | | - itens: List    |
+--------+         | - aberto: boolean |
| + valorAPagar()  | +------------------+
| + adicionar()    | | + adicionar()    |
+--------+         | + fecharPedido()  |
                   | # precoAPagar()*  |
                   +------------------+
                         △
                        / \
                       /   \
          +----------------+  +----------------+
          | PedidoLocal    |  | PedidoEntrega  |
          +----------------+  +----------------+
          | + precoAPagar()|  | - distancia: double|
          +----------------+  +----------------+
                                | + precoAPagar()|
                                +----------------+
```

* método abstrato

---

## CUIDADOS COMUNS

### 1. Losango sempre do lado de quem TEM/CONTÉM

```
// ✅ CORRETO
+--------+  ◆──── +--------+
| Pedido |        | Pizza  |
+--------+        +--------+

// ❌ ERRADO - losango do lado errado
+--------+        +--------+
| Pizza  | ◆──── | Pedido |
+--------+        +--------+
```

### 2. Herança: triângulo aponta para a MÃE

```
// ✅ CORRETO
    +--------+
    | Animal |  ← mãe
    +--------+
        △
        │
+--------+
|Cachorro|  ← filha
+--------+
```

### 3. Interface: triângulo tracejado

```
// ✅ CORRETO - implementação
+------------------+
| <<interface>>    |
|    IProduto      |
+------------------+
        △  ← triângulo tracejado
        │
+--------+
|  Pizza |
+--------+
```

### 4. Atributos: private por padrão

```
// ✅ CORRETO - atributos private
+--------+
|  Pizza |
+--------+
| - preco: double  |  ← private
+--------+

// ❌ ERRADO - atributos public
+--------+
|  Pizza |
+--------+
| + preco: double  |  ← public (ruim!)
+--------+
```

---

## RESUMO DOS SÍMBOLOS

| Símbolo | Nome | Significado |
|---------|------|-------------|
| `+` | public | Acessível por todos |
| `-` | private | Acessível só pela classe |
| `#` | protected | Acessível pela classe e filhas |
| `~` | package | Mesmo pacote |
| `→` | Seta simples | Associação |
| `◇` | Losango vazio | Agregação ("TEM") |
| `◆` | Losango cheio | Contenção ("CONTÉM") |
| `△` | Triângulo | Herança ("É UM") |
| `△` tracejado | Triângulo tracejado | Implementação ("IMPLEMENTA") |
| `<<enum>>` | Stereotype | Enum |
| `<<interface>>` | Stereotype | Interface |

---

## CHECKLIST UML

- [ ] Losango do lado de quem TEM/CONTÉM?
- [ ] Triângulo de herança apontando para a mãe?
- [ ] Triângulo tracejado para implementação de interface?
- [ ] Atributos com modificador correto (-, #, +)?
- [ ] Métodos com modificador correto?
- [ ] Enum com <<enum>>?
- [ ] Interface com <<interface>>?
- [ ] Contenção (◆) quando a parte não existe sem o todo?
- [ ] Agregação (◇) quando a parte existe sozinha?
