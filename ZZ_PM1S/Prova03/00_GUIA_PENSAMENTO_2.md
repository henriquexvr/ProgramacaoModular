# GUIA DE PENSAMENTO 2 — ANÁLISE DE DIAGRAMA UML + MODIFICAÇÕES

> **Para provas onde o professor dá um diagrama UML + um texto pedindo modificações.**

---

## VISÃO GERAL

| Situação | Abordagem |
|----------|-----------|
| **Criar do zero** | Identificar classes → Criar tudo |
| **Diagrama + modificações** | Entender o que existe → Adicionar/modificar o que falta |

### Regra de Ouro

> **Não mude o que o diagrama mostra a menos que o enunciado peça EXPLICITAMENTE.**
> Se o enunciado diz "adicionar", é adicionar — não recriar.

---

## FLUXO DE 5 PASSOS

### Passo 1: LER O DIAGRAMA (antes de qualquer coisa)

Antes de ler o enunciado, entenda o que JÁ EXISTE.

**O que identificar:**
- Quais classes já existem?
- Quais são os relacionamentos (herança, composição, dependência)?
- Quais métodos e atributos já estão definidos?
- Quais interfaces já existem?

**Pergunta-chave:** "O que JÁ EXISTE aqui?"

---

### Passo 2: LER O ENUNCIADO (o que pedem para mudar)

Agora sim, leia o que o professor quer.

**O que marcar:**
- **Verbos de CRIAÇÃO:** "adicionar", "criar", "incluir" → precisa criar algo novo
- **Verbos de MODIFICAÇÃO:** "substituir", "alterar", "modificar" → muda o que já existe
- **Verbos de MANTER:** "manter", "conservar" → não mexer

**Pergunta-chave:** "O que PRECISA MUDAR?"

---

### Passo 3: MAPEAR O QUE VAI PARA ONDE

| Enunciado pede... | Conceito Java | O que fazer |
|-------------------|---------------|-------------|
| "Adicionar nova forma de desconto" | **Strategy** | Criar interface + implementações |
| "Criar novo tipo de pedido" | **Herança + Factory** | Filho + método na factory |
| "Adicionar funcionalidade sem quebrar" | **Decorator** | Classe embrulho |
| "Notificar quando algo mudar" | **Observer** | Interface + lista de observadores |
| "Criar pedido sem saber qual" | **Factory** | Método estático |
| "Mesmo fluxo, passos diferentes" | **Template** | Classe abstrata + método final |
| "Valores fixos (estados, tipos)" | **Enum** | Enumeração |
| "Validar regras de negócio" | **Exceção** | IllegalArgumentException |

---

### Passo 4: DESENHAR NO PAPEL (antes de codar)

1. **Copie** o diagrama original (raspido, não precisa ser perfeito)
2. **Marque em VERMELHO** o que será ADICIONADO
3. **Marque em AZUL** o que será MODIFICADO
4. **Confirme:** "Isso quebra algo do diagrama original?"

---

### Passo 5: IMPLEMENTAR NA ORDEM CORRETA

```
1. O que NÃO muda      → manter intacto
2. O que é ADICIONADO  → novas classes/interfaces
3. O que é MODIFICADO  → alterações em classes existentes
4. TESTAR              → confirmar que o que já existia continua funcionando
```

---

## EXEMPLOS PRÁTICOS

---

### EXEMPLO 1: ADICIONAR DESCONTO POR CATEGORIA (Strategy)

#### Diagrama Original

```
┌─────────────────┐
│     Pedido      │
├─────────────────┤
│ - itens: List   │
│ - total: double │
├─────────────────┤
│ + adicionar()   │
│ + calcularTotal │
└─────────────────┘

┌─────────────────┐
│    IProduto     │ (interface)
├─────────────────┤
│ + valorAPagar() │
└─────────────────┘
```

#### Enunciado

> "Adicionar suporte a desconto por fidelidade. Cada cliente pode ser Junior, Pleno ou Senior. Junior não tem desconto, Pleno tem 10% e Senior tem 20%. O desconto é aplicado sobre o total do pedido."

#### Resolução Passo a Passo

**Passo 1: O que já existe?**
- Pedido (classe concreta)
- IProduto (interface)

**Passo 2: O que precisa mudar?**
- "Adicionar" → criar algo novo
- Nova interface IFidelidade
- 3 implementações: Junior, Pleno, Senior
- Pedido precisa aceitar IFidelidade

**Passo 3: Mapeamento**
- "Adicionar nova forma de desconto" → Strategy

**Passo 4: Desenhar no papel**
```
         ADICIONAR (vermelho)
              │
              ▼
┌─────────────────┐
│   IFidelidade   │ ← nova interface
├─────────────────┤
│ + desconto()    │
└─────────────────┘
       │ │ │
       ▼ ▼ ▼
  Junior Pleno Senior

┌─────────────────┐
│     Pedido      │ ← MODIFICAR (azul)
├─────────────────┤
│ - categoria: IFidelidade  ← novo atributo
├─────────────────┤
│ + adicionar()   │
│ + calcularTotal │
│ + setFidelidade │ ← novo método
└─────────────────┘
```

**Passo 5: Implementar**

```java
// 1. O que NÃO muda — IProduto continua igual
interface IProduto {
    double valorAPagar();
}

// 2. O que é ADICIONADO — nova interface
interface IFidelidade {
    double descontoPedido(Pedido pedido);
}

// 3. O que é ADICIONADO — implementações
class XulambsJunior implements IFidelidade {
    public double descontoPedido(Pedido pedido) {
        return 0; // sem desconto
    }
}

class XulambsPleno implements IFidelidade {
    public double descontoPedido(Pedido pedido) {
        double total = pedido.getItens().stream()
            .mapToDouble(IProduto::valorAPagar).sum();
        return total * 0.10;
    }
}

class XulambsSenior implements IFidelidade {
    public double descontoPedido(Pedido pedido) {
        double total = pedido.getItens().stream()
            .mapToDouble(IProduto::valorAPagar).sum();
        return total * 0.20;
    }
}

// 4. O que é MODIFICADO — Pedido recebe nova dependência
class Pedido {
    private List<IProduto> itens;
    private IFidelidade categoria; // NOVO ATRIBUTO
    
    public Pedido() {
        itens = new LinkedList<>();
    }
    
    // NOVO MÉTODO
    public void setFidelidade(IFidelidade categoria) {
        this.categoria = categoria;
    }
    
    public double calcularTotal() {
        double total = itens.stream()
            .mapToDouble(IProduto::valorAPagar).sum();
        
        if (categoria != null) {
            total -= categoria.descontoPedido(this);
        }
        
        return total;
    }
}
```

---

### EXEMPLO 2: CRIAR NOVO TIPO DE PEDIDO (Herança + Factory)

#### Diagrama Original

```
┌─────────────────────┐
│    GeradorPedidos   │
├─────────────────────┤
│ + criar(tipo, valor)│
└──────────┬──────────┘
           │ cria
           ▼
┌─────────────────┐
│     Pedido      │
├─────────────────┤
│ - valor         │
├─────────────────┤
│ + calcularTotal │
└─────────────────┘
```

#### Enunciado

> "Adicionar suporte a pedidos para eventos. Um pedido de evento pode ter até 50 itens e precisa de um nome de evento. A taxa é de 15% sobre o total."

#### Resolução Passo a Passo

**Passo 1: O que já existe?**
- Pedido (classe base)
- GeradorPedidos (factory)

**Passo 2: O que precisa mudar?**
- "Adicionar" → criar algo novo
- Novo filho: PedidoEvento
- Atualizar factory para criar "evento"

**Passo 3: Mapeamento**
- "Criar novo tipo de pedido" → Herança + Factory

**Passo 4: Desenhar no papel**
```
┌─────────────────────┐
│    GeradorPedidos   │ ← MODIFICAR (azul)
├─────────────────────┤
│ + criar(tipo, valor)│   adicionar case "evento"
└──────────┬──────────┘
           │ cria
           ▼
┌─────────────────┐
│     Pedido      │
├─────────────────┤
│ - valor         │
├─────────────────┤
│ + calcularTotal │
└─────────────────┘
       ▲
       │ extends
┌─────────────────┐
│  PedidoEvento   │ ← ADICIONAR (vermelho)
├─────────────────┤
│ - nomeEvento    │
├─────────────────┤
│ + calcularTotal │ (override)
└─────────────────┘
```

**Passo 5: Implementar**

```java
// 1. O que NÃO muda — Pedido continua igual
abstract class Pedido {
    protected double valor;
    
    public double getValor() { return valor; }
    
    public double calcularTotal() {
        return valor;
    }
}

// 2. O que é ADICIONADO — novo filho
class PedidoEvento extends Pedido {
    private String nomeEvento;
    private static final int MAX_ITENS = 50;
    private List<IProduto> itens;
    
    public PedidoEvento(double valor, String nomeEvento) {
        this.valor = valor;
        this.nomeEvento = nomeEvento;
        this.itens = new LinkedList<>();
    }
    
    public int adicionar(IProduto item) {
        if (itens.size() >= MAX_ITENS) {
            throw new IllegalArgumentException("Limite de 50 itens atingido");
        }
        itens.add(item);
        return itens.size();
    }
    
    @Override
    public double calcularTotal() {
        return valor * 1.15; // 15% de taxa
    }
}

// 3. O que é MODIFICADO — factory recebe novo case
class GeradorPedidos {
    public static Pedido criar(String tipo, double valor, String nomeEvento) {
        switch (tipo.toLowerCase()) {
            case "local":
                return new PedidoLocal(valor);
            case "entrega":
                return new PedidoEntrega(valor);
            case "evento":  // NOVO CASE
                return new PedidoEvento(valor, nomeEvento);
            default:
                throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        }
    }
}
```

---

### EXEMPLO 3: ADICIONAR COBERTURAS A PIZZA (Decorator)

#### Diagrama Original

```
┌─────────────────┐
│    IProduto     │ (interface)
├─────────────────┤
│ + valorAPagar() │
│ + getDescricao()│
└─────────────────┘
       ▲
       │ implements
┌─────────────────┐
│  PizzaSimples   │
├─────────────────┤
│ - nome          │
│ - preco         │
├─────────────────┤
│ + valorAPagar() │
│ + getDescricao()│
└─────────────────┘
```

#### Enunciado

> "Adicionar suporte a coberturas extras. Uma pizza pode ter múltiplas coberturas, cada uma com valor adicional. Exemplo: Pizza Margherita (R$40) + Bacon (R$8) + Cheddar (R$5) = R$53."

#### Resolução Passo a Passo

**Passo 1: O que já existe?**
- IProduto (interface)
- PizzaSimples (classe concreta)

**Passo 2: O que precisa mudar?**
- "Adicionar" → criar algo novo
- Decorator abstrato
- Decorator concreto (CoberturaExtra)

**Passo 3: Mapeamento**
- "Adicionar funcionalidade sem quebrar" → Decorator

**Passo 4: Desenhar no papel**
```
┌─────────────────┐
│    IProduto     │
└─────────────────┘
       ▲
       │ implements
┌─────────────────┐       ┌─────────────────┐
│  PizzaSimples   │       │  DecoratorPizza │ ← ADICIONAR (vermelho)
└─────────────────┘       │  (abstract)     │
                          └─────────────────┘
                                  ▲
                                  │ extends
                          ┌─────────────────┐
                          │ CoberturaExtra  │ ← ADICIONAR (vermelho)
                          └─────────────────┘
```

**Passo 5: Implementar**

```java
// 1. O que NÃO muda — IProduto e PizzaSimples continuam iguais
interface IProduto {
    double valorAPagar();
    String getDescricao();
}

class PizzaSimples implements IProduto {
    private String nome;
    private double preco;
    
    public PizzaSimples(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
    
    public double valorAPagar() { return preco; }
    public String getDescricao() { return nome; }
}

// 2. O que é ADICIONADO — decorator abstrato
abstract class DecoratorPizza implements IProduto {
    protected IProduto pizza;
    
    public DecoratorPizza(IProduto pizza) {
        this.pizza = pizza;
    }
    
    public double valorAPagar() {
        return pizza.valorAPagar();
    }
    
    public String getDescricao() {
        return pizza.getDescricao();
    }
}

// 3. O que é ADICIONADO — decorator concreto
class CoberturaExtra extends DecoratorPizza {
    private String cobertura;
    private double valorAdicional;
    
    public CoberturaExtra(IProduto pizza, String cobertura, double valor) {
        super(pizza);
        this.cobertura = cobertura;
        this.valorAdicional = valor;
    }
    
    @Override
    public double valorAPagar() {
        return super.valorAPagar() + valorAdicional;
    }
    
    @Override
    public String getDescricao() {
        return super.getDescricao() + " + " + cobertura;
    }
}

// Uso
IProduto p = new PizzaSimples("Margherita", 40.0);
p = new CoberturaExtra(p, "Bacon", 8.0);
p = new CoberturaExtra(p, "Cheddar", 5.0);
// p.valorAPagar() = 53.0
// p.getDescricao() = "Margherita + Bacon + Cheddar"
```

---

### EXEMPLO 4: NOTIFICAR CLIENTE QUANDO PEDIDO SAI (Observer)

#### Diagrama Original

```
┌─────────────────┐
│     Pedido      │
├─────────────────┤
│ - itens         │
│ - total         │
├─────────────────┤
│ + adicionar()   │
│ + calcularTotal │
│ + confirmar()   │
└─────────────────┘

┌─────────────────┐
│     Cliente     │
├─────────────────┤
│ - nome          │
│ - telefone      │
├─────────────────┤
│ + getNome()     │
│ + getTelefone() │
└─────────────────┘
```

#### Enunciado

> "Adicionar notificação. Quando um pedido for confirmado, o cliente deve ser notificado. O sistema deve suportar múltiplos observadores (email, SMS, WhatsApp)."

#### Resolução Passo a Passo

**Passo 1: O que já existe?**
- Pedido
- Cliente

**Passo 2: O que precisa mudar?**
- "Adicionar" → criar algo novo
- Interface ObservadorPedido
- Implementações: EmailNotificacao, SmsNotificacao, WhatsappNotificacao
- Pedido precisa notificar observadores

**Passo 3: Mapeamento**
- "Notificar quando algo mudar" → Observer

**Passo 4: Desenhar no papel**
```
┌─────────────────┐
│ ObservadorPedido│ ← ADICIONAR (vermelho)
├─────────────────┤
│ + notificar()   │
└─────────────────┘
       ▲ ▲ ▲
       │ │ │
  Email Sms Whatsapp

┌─────────────────┐
│     Pedido      │ ← MODIFICAR (azul)
├─────────────────┤
│ - observadores  │ ← novo atributo
├─────────────────┤
│ + adicionar()   │
│ + confirmar()   │   agora notifica
│ + adicionarObs  │ ← novo método
└─────────────────┘
```

**Passo 5: Implementar**

```java
// 1. O que é ADICIONADO — interface observador
interface ObservadorPedido {
    void notificar(Pedido pedido, String mensagem);
}

// 2. O que é ADICIONADO — implementações
class EmailNotificacao implements ObservadorPedido {
    public void notificar(Pedido pedido, String mensagem) {
        System.out.println("EMAIL: " + mensagem);
    }
}

class SmsNotificacao implements ObservadorPedido {
    public void notificar(Pedido pedido, String mensagem) {
        System.out.println("SMS: " + mensagem);
    }
}

class WhatsappNotificacao implements ObservadorPedido {
    public void notificar(Pedido pedido, String mensagem) {
        System.out.println("WHATSAPP: " + mensagem);
    }
}

// 3. O que é MODIFICADO — Pedido agora notifica
class Pedido {
    private List<IProduto> itens;
    private List<ObservadorPedido> observadores; // NOVO
    
    public Pedido() {
        itens = new LinkedList<>();
        observadores = new LinkedList<>();
    }
    
    public void adicionarObservador(ObservadorPedido obs) {
        observadores.add(obs);
    }
    
    public void confirmar() {
        String msg = "Pedido confirmado! Total: R$" + calcularTotal();
        
        for (ObservadorPedido obs : observadores) {
            obs.notificar(this, msg);
        }
    }
    
    public double calcularTotal() {
        return itens.stream()
            .mapToDouble(IProduto::valorAPagar).sum();
    }
}

// Uso
Pedido p = new Pedido();
p.adicionarObservador(new EmailNotificacao());
p.adicionarObservador(new SmsNotificacao());
p.confirmar(); // envia para email E SMS
```

---

### EXEMPLO 5: CALCULAR FRETE COM REGRAS DIFERENTES (Template)

#### Diagrama Original

```
┌─────────────────────┐
│      Servico        │
├─────────────────────┤
│ - tipo              │
├─────────────────────┤
│ + executar()        │
└─────────────────────┘

┌─────────────────────┐
│     Pedido          │
├─────────────────────┤
│ - itens             │
├─────────────────────┤
│ + calcularFrete()   │
└─────────────────────┘
```

#### Enunciado

> "Adicionar cálculo de frete. O cálculo tem 3 passos: (1) calcular peso, (2) calcular distância, (3) aplicar regra. Para pedidos locais, a regra é R$5 fixo. Para pedidos de entrega, a regra é R$2 por km. Para pedidos de evento, a regra é R$10 fixo."

#### Resolução Passo a Passo

**Passo 1: O que já existe?**
- Pedido
- Servico

**Passo 2: O que precisa mudar?**
- "Adicionar" → criar algo novo
- Template Method para frete
- 3 subclasses com regras diferentes

**Passo 3: Mapeamento**
- "Mesmo fluxo, passos diferentes" → Template Method

**Passo 4: Desenhar no papel**
```
┌─────────────────┐
│ FreteCalculator │ ← ADICIONAR (vermelho)
├─────────────────┤
│ + calcular()    │ ← final (template)
│ + peso()        │ ← abstrato
│ + distancia()   │ ← abstrato
│ + regra()       │ ← abstrato
└─────────────────┘
       ▲ ▲ ▲
       │ │ │
  Local Entrega Evento
```

**Passo 5: Implementar**

```java
// 1. O que é ADICIONADO — template abstrato
abstract class FreteCalculator {
    
    // TEMPLATE METHOD — fluxo fixo
    public final double calcular(double peso, double distanciaKm) {
        double p = calcularPeso(peso);       // passo 1
        double d = calcularDistancia(distanciaKm); // passo 2
        return aplicarRegra(p, d);           // passo 3 (variável)
    }
    
    protected double calcularPeso(double peso) {
        return peso; // passo padrão
    }
    
    protected double calcularDistancia(double km) {
        return km; // passo padrão
    }
    
    protected abstract double aplicarRegra(double peso, double distancia);
}

// 2. O que é ADICIONADO — implementações
class FreteLocal extends FreteCalculator {
    @Override
    protected double aplicarRegra(double peso, double distancia) {
        return 5.00; // R$5 fixo
    }
}

class FreteEntrega extends FreteCalculator {
    @Override
    protected double aplicarRegra(double peso, double distancia) {
        return distancia * 2.0; // R$2 por km
    }
}

class FreteEvento extends FreteCalculator {
    @Override
    protected double aplicarRegra(double peso, double distancia) {
        return 10.00; // R$10 fixo
    }
}

// 3. O que é MODIFICADO — Pedido usa o template
class Pedido {
    private FreteCalculator frete;
    
    public void setFrete(FreteCalculator frete) {
        this.frete = frete;
    }
    
    public double calcularFrete(double peso, double distancia) {
        return frete.calcular(peso, distancia);
    }
}

// Uso
Pedido p1 = new Pedido();
p1.setFrete(new FreteLocal());
p1.calcularFrete(2.0, 5.0); // R$5

Pedido p2 = new Pedido();
p2.setFrete(new FreteEntrega());
p2.calcularFrete(2.0, 5.0); // R$10 (5km × R$2)
```

---

## CHECKLIST ANÁLISE DE DIAGRAMA

- [ ] Li o diagrama INTEIRO antes de ler o enunciado?
- [ ] Identifiquei todas as classes existentes?
- [ ] Identifiquei todos os relacionamentos?
- [ ] Marquei o que NÃO pode mudar?
- [ ] Marquei o que o enunciado pede para ADICIONAR?
- [ ] Marquei o que o enunciado pede para MODIFICAR?
- [ ] Verifiquei se a modificação quebra algo existente?
- [ ] Implementei na ordem: não muda → adiciona → modifica?
- [ ] Testei que o que já existia continua funcionando?
