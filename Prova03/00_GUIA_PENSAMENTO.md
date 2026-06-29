# GUIA DE PENSAMENTO PARA PROGRAMAÇÃO MODULAR (3ª Prova)

## COMO LER UM ENUNCIADO SEM TRAVAR

### Passo 1: Identifique os SUBSTANTIVOS e VERBOS

Quando ler o enunciado, faça duas listas:

| Marcar | O que provavelmente é |
|--------|----------------------|
| **Substantivos** (nomes) | Classes, atributos |
| **Verbos** (ações) | Métodos, responsabilidades |

**Exemplo:** "Um Pedido pode conter vários Produtos e calcular o valor total"
- Substantivos → `Pedido`, `Produto` → classes
- Verbos → `conter`, `calcular valor total` → métodos/atributos

### Passo 2: Pergunte-se "EU SOU ou EU FAÇO?"

| Pergunta | Solução |
|----------|---------|
| "Eu **SOU** esse algo?" | **Herança** (`extends`) |
| "Eu **FAÇO** esse algo?" | **Interface** (`implements`) ou **Composição** (atributo) |

**Exemplo:** "PedidoEntrega É UM Pedido" → `extends Pedido`
**Exemplo:** "Pizza FAÇO valorAPagar()" → `implements IProduto`

### Passo 3: Identifique o que MUDA vs o que é FIXO

| Tipo | Solução Java |
|------|-------------|
| Comportamento que **muda** | Interface + Strategy |
| Valores **fixos** (estados, tipos) | `enum` |
| Mesmo fluxo, passos diferentes | Template Method |
| Criar objetos diferentes | Factory Method |
| Adicionar funcionalidade sem modificar | Decorator |

---

## MAPA DE DECISÃO: "QUAL CONCEITO USAR?"

```
O enunciado pede...
│
├─ Uma classe que NÃO pode ser instanciada?
│  → Classe ABSTRATA (abstract class)
│
├─ Um contrato que várias classes devem cumprir?
│  → INTERFACE (interface)
│
├─ Um conjunto fixo de valores (estados, tipos)?
│  → ENUM (enum)
│
├─ Um valor que pode ou não existir?
│  → OPTIONAL
│
├─ Dados que mudam de comportamento?
│  → STRATEGY (interface + implementações)
│
├─ Um fluxo fixo com passos variáveis?
│  → TEMPLATE METHOD (classe abstrata + override)
│
├─ Criar objetos sem saber qual classe exata?
│  → FACTORY METHOD (método estático)
│
├─ Adicionar funcionalidade sem modificar?
│  → DECORATOR (embrulhar objeto)
│
├─ Quando algo muda, vários precisam saber?
│  → OBSERVER (notificação)
│
├─ Processar/filtrar/transformar uma lista?
│  → STREAM (filter, map, reduce, toList)
│
├─ Ordenar, comparar dois valores?
│  → COMPARATOR + LAMBDA
│
├─ Verificar uma condição?
│  → PREDICATE + LAMBDA
│
├─ Transformar um valor em outro?
│  → FUNCTION + LAMBDA
│
├─ Executar uma ação (sem retorno)?
│  → CONSUMER + LAMBDA
│
└─ Valores que NÃO mudam (constantes)?
   → static final + nome em MAIÚSCULAS
```

---

## POR QUE UM CONCEITO SERIA MELHOR QUE OUTRO?

### Interface vs Herança

| Critério | Herança (`extends`) | Interface (`implements`) |
|----------|---------------------|-------------------------|
| Relação | "É UM" natural | "FAÇO" algo |
| Classes relacionadas? | Sim (mesma família) | Não necessariamente |
| Múltipla? | NÃO (1 só mãe) | SIM (várias interfaces) |
| Quando usar | Animal → Cachorro | Pizza e Sanduiche → IProduto |

**Regra:** Se as classes são naturalmente da mesma família → herança. Se apenas fazem a mesma coisa → interface.

### ArrayList vs LinkedList

| Situação | Use |
|----------|-----|
| Acesso por índice (`get(i)`) | ArrayList |
| Inserções/remoções no início/meio | LinkedList |
| Implementar fila/pilha | LinkedList |
| **Não sabe qual usar** | **ArrayList** (90% dos casos) |

**Sempre declarar como `List<>`, nunca como `ArrayList<>` ou `LinkedList<>`**

### Stream vs Loop For

| Critério | Loop For | Stream |
|----------|----------|--------|
| Legibilidade | Imperativo (como fazer) | Declarativo (o que fazer) |
| Complexidade | Aumenta com filtros | Mantém legível |
| Performance | Ligeiramente melhor | Negligenciável na prova |
| Quando usar | Iteração simples | Filtros + transformações encadeadas |

**Use Stream quando:** precisa filtrar + transformar + coletar em sequência.

### Lambda vs Classe Separada

| Critério | Classe Separada | Lambda |
|----------|-----------------|--------|
| Código | Muito código boilerplate | Uma linha |
| Quando usar | Interface com múltiplos métodos | Interface funcional (1 método) |
| Legibilidade | Verborrágica para coisas simples | Limpa |

---

## PADRÕES DE COMPORTAMENTO NO XULAMBS

### Herança (Herarquia natural)
```
Pedido (abstrata)
  ├── PedidoLocal     (herda tudo, override em calcularTaxa)
  └── PedidoEntrega   (herda tudo, override em calcularTaxa + validação)
```
**Por que herança?** PedidoLocal e PedidoEntrega **SÃO** pedidos. A diferença é inerente à natureza.

### Interface (Comportamento que muda)
```
IProduto (contrato: valorAPagar())
  ├── Pizza implements IProduto, IPersonalizavel
  ├── Sanduiche implements IProduto
  ├── Bebida implements IProduto
  └── Sobremesa implements IProduto

IFidelidade (contrato: descontoPedido())
  ├── XulambsJunior implements IFidelidade
  ├── XulambsPleno implements IFidelidade
  └── XulambsSenior implements IFidelidade
```
**Por que interface?** Pizza e Sanduiche não são da mesma família, mas ambos PODEM SER produtos. O desconto MUDA dependendo da categoria.

### Strategy (Desconto por categoria)
```java
// O Cliente não precisa saber qual categoria é
// Ele só chama: categoria.descontoPedido(pedido)
// Cada implementação sabe o que fazer
```

### Factory (Criar pedidos)
```java
// GeradorPedidos decide qual criar baseado no tipo
// "local" → PedidoLocal
// "entrega" → PedidoEntrega
```

---

## RESUMO DOS PADRÕES DE COMPORTAMENTO

### Strategy (Comportamento que MUDA)

**Problema:** O mesmo tipo de coisa pode ter várias formas de funcionar.
**Solução:** Interface + implementações.

```
IFidelidade (contrato)
  ├── XulambsJunior  → 0% desconto
  ├── XulambsPleno   → 10% desconto
  └── XulambsSenior  → 20% desconto
```

```java
interface IFidelidade {
    double descontoPedido(Pedido pedido);
}

class XulambsJunior implements IFidelidade {
    public double descontoPedido(Pedido pedido) {
        return 0;
    }
}

class XulambsSenior implements IFidelidade {
    public double descontoPedido(Pedido pedido) {
        return total * 0.20;
    }
}

// Quem cria decide qual usar
Cliente c = new Cliente(new XulambsSenior());
```

**Quando:** Tem vários jeitos de fazer e posso trocar entre eles.

---

### Template Method (Mesmo fluxo, PASSOS diferentes)

**Problema:** Todas as subclasses seguem o mesmo passo a passo, mas cada uma implementa um passo diferente.
**Solução:** Classe abstrata com método final que define o fluxo + método abstrato para o passo variável.

```
Pedido (abstract)
  calcularTotal() → final
    calcularImpostos() → fixo (10%)
    calcularTaxa() → ABSTRATO (cada filho muda)
      ├── PedidoLocal  → R$5
      └── PedidoEntrega → R$10
```

```java
abstract class Pedido {
    // TEMPLATE METHOD — fluxo fixo
    public final double calcularTotal() {
        double impostos = calcularImpostos(); // fixo
        double taxa = calcularTaxa();          // variável
        return valor + impostos + taxa;
    }
    
    protected double calcularImpostos() {
        return valor * 0.10;
    }
    
    protected abstract double calcularTaxa(); // cada filho define
}

class PedidoLocal extends Pedido {
    protected double calcularTaxa() { return 5.00; }
}

class PedidoEntrega extends Pedido {
    protected double calcularTaxa() { return 10.00; }
}
```

**Quando:** O passo a passo é igual, mas cada um faz o passo à sua maneira.

---

### Factory Method (Criar objetos DIFERENTES)

**Problema:** Você não sabe qual classe criar em tempo de programação.
**Solução:** Método estático que decide qual criar baseado em um dado.

```
GeradorPedidos.criar("local")  → PedidoLocal
GeradorPedidos.criar("entrega") → PedidoEntrega
```

```java
class GeradorPedidos {
    public static Pedido criar(String tipo, double valor) {
        switch (tipo.toLowerCase()) {
            case "local":
                return new PedidoLocal(valor);
            case "entrega":
                return new PedidoEntrega(valor);
            default:
                throw new IllegalArgumentException("Tipo desconhecido");
        }
    }
}

Pedido p = GeradorPedidos.criar("local", 50.0);
```

**Quando:** Preciso criar algo mas não sei qual exato — decido no momento.

---

### Decorator (Adicionar funcionalidade SEM modificar)

**Problema:** Quero adicionar comportamento a um objeto sem alterar a classe original.
**Solução:** Embrulhar o objeto e adicionar funcionalidade por cima.

```
PizzaSimples (R$40)
  └─ CoberturaExtra "Bacon" (R$8)
       └─ CoberturaExtra "Cheddar" (R$5)
            = R$53
```

```java
// Contrato base
interface IProduto {
    double valorAPagar();
    String getDescricao();
}

// Objeto base
class PizzaSimples implements IProduto {
    public double valorAPagar() { return preco; }
    public String getDescricao() { return nome; }
}

// Decorator abstrato — segura outro IProduto
abstract class DecoratorPizza implements IProduto {
    protected IProduto pizza;
    public DecoratorPizza(IProduto pizza) { this.pizza = pizza; }
    public double valorAPagar() { return pizza.valorAPagar(); }
    public String getDescricao() { return pizza.getDescricao(); }
}

// Decorator concreto — adiciona algo
class CoberturaExtra extends DecoratorPizza {
    private String cobertura;
    private double valorAdicional;
    
    public CoberturaExtra(IProduto pizza, String cobertura, double valor) {
        super(pizza);
        this.cobertura = cobertura;
        this.valorAdicional = valor;
    }
    
    public double valorAPagar() {
        return super.valorAPagar() + valorAdicional;
    }
    
    public String getDescricao() {
        return super.getDescricao() + " + " + cobertura;
    }
}

// Uso
IProduto p = new PizzaSimples("Margherita", 40.0);
p = new CoberturaExtra(p, "Bacon", 8.0);
p = new CoberturaExtra(p, "Cheddar", 5.0);
// p = R$53, "Margherita + Bacon + Cheddar"
```

**Quando:** Quero adicionar algo sem quebrar o que já existe.

---

### Comparação Rápida

| Padrão | Pergunta-chave | Exemplo |
|--------|---------------|---------|
| **Strategy** | "Tem vários jeitos e posso **trocar**?" | IFidelidade (Junior/Pleno/Senior) |
| **Template** | "Fluxo igual, **passos diferentes**?" | PedidoLocal vs PedidoEntrega |
| **Factory** | "Preciso criar algo mas **não sei qual**?" | GeradorPedidos.criar("local") |
| **Decorator** | "Quero **adicionar** sem modificar?" | Pizza + CoberturaExtra |

---

## CHECKLIST ANTES DE ESCREVER CÓDIGO

- [ ] Identifiquei os SUBSTANTIVOS → classes?
- [ ] Identifiquei os VERBOS → métodos?
- [ ] "EU SOU" → herança? "EU FAÇO" → interface?
- [ ] Valores fixos → enum?
- [ ] Comportamento muda → Strategy/Interface?
- [ ] Construtor com validação (IllegalArgumentException)?
- [ ] Atributos private?
- [ ] `List<>` em vez de `ArrayList<>`?
- [ ] `@Override` nos métodos sobrescritos?
- [ ] `super()` como primeira linha no construtor filha?
- [ ] `hashCode()` e `equals()` juntos quando necessário?
- [ ] Sem System.out.println dentro da classe?

---

## ERROS MAIS COMUNS (E COMO EVITAR)

| Erro | Como evitar |
|------|-------------|
| Esquecer `super()` | Sempre primeira linha do construtor filha |
| `ArrayList<String>` como tipo | Usar `List<String>` |
| `getClass()` para comparar | Usar `instanceof` |
| Catch vazio | Sempre tratar a exceção |
| `assertEquals` com double sem delta | Adicionar terceiro parâmetro (0.01) |
| Imprimir dentro da classe | System.out.println só no App.java |
| Não validar no construtor | Lancar IllegalArgumentException |
| Interface com 2+ métodos para Lambda | Interface funcional = 1 método abstrato |

---

## FLUXO DE RESOLUÇÃO DE EXERCÍCIO

```
1. LER o enunciado completo (sem pressa)
2. MARCAR substantivos e verbos
3. DESENHAR no papel as classes e relacionamentos
4. PERGUNTAR: "EU SOU" ou "EU FAÇO"?
5. ESCOLHER o conceito certo (herança/interface/enum/etc)
6. IMPLEMENTAR na ordem:
   a. Enums e constantes
   b. Interfaces
   c. Classes abstratas
   d. Classes concretas
   e. Construtores com validação
   f. Métodos
   g. Testes
7. REVISAR com o checklist
8. RODAR testes
```
