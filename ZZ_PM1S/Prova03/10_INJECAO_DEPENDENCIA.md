# INJEÇÃO DE DEPENDÊNCIA - Guia Completo

## O QUE É DEPENDÊNCIA?

Quando uma classe **precisa de outra** para funcionar, ela tem uma **dependência**.

```java
class Pedido {
    private IFidelidade categoria; // depende de IFidelidade
}
```

A questão não é SE ter dependência (toda classe tem), mas **COMO criar e injetar** essa dependência.

---

## POR QUE SE PREOCUPAR COM ISSO?

### Problema: Acoplamento Forte

```java
// ❌ ERRADO - Pedido decide QUAL implementação usar
class Cliente {
    private IFidelidade categoria;
    
    public Cliente() {
        this.categoria = new XulambsJunior(); // ACHEI que junior é bom
    }
}
```

**Problemas:**
1. Cliente não deveria saber qual implementação usar
2. Trocar para Pleno = modificar código de Cliente
3. Difícil de testar (não dá para mockar)

### Solução: Injeção de Dependência

```java
// ✅ CORRETO - quem CRIA o Cliente decide a implementação
class Cliente {
    private IFidelidade categoria;
    
    public Cliente(IFidelidade categoria) { // recebe de fora
        this.categoria = categoria;
    }
}
```

**Vantagens:**
1. Cliente não precisa saber qual implementação
2. Trocar = criar novo Cliente com outra implementação
3. Fácil de testar (passa mock)

---

## OS 3 TIPOS DE INJEÇÃO

### 1. Injeção por Construtor (a mais usada)

A dependência é passada pelo **construtor**.

```java
class Cliente {
    private IFidelidade categoria;
    private String nome;
    
    // Injeção por construtor
    public Cliente(String nome, IFidelidade categoria) {
        this.nome = nome;
        this.categoria = categoria; // injetada aqui
    }
}

// Uso
Cliente c1 = new Cliente("Ana", new XulambsJunior());
Cliente c2 = new Cliente("Bruno", new XulambsSenior());
```

**Quando usar:** Sempre que a classe PRECISA da dependência para funcionar.

**Regras:**
- O construtor **DEVE** validar a dependência (não aceitar null)
- A dependência é **obrigatória** (sem ela, não cria o objeto)

```java
public Cliente(String nome, IFidelidade categoria) {
    if (categoria == null)
        throw new IllegalArgumentException("Categoria não pode ser null");
    this.categoria = categoria;
}
```

### 2. Injeção por Setter

A dependência é passada por um **método setter**.

```java
class Cliente {
    private IFidelidade categoria;
    
    // Setter - permite mudar depois
    public void setCategoria(IFidelidade categoria) {
        this.categoria = categoria;
    }
}

// Uso
Cliente c = new Cliente();
c.setCategoria(new XulambsPleno());
// Depois pode mudar
c.setCategoria(new XulambsSenior());
```

**Quando usar:** Quando a dependência é **opcional** ou pode **mudar** em tempo de execução.

### 3. Injeção por Interface (Menos comum na prova)

A classe implementa uma interface que fornece a dependência.

```java
interface Categorizavel {
    IFidelidade getCategoria();
}

class Cliente implements Categorizavel {
    private IFidelidade categoria;
    
    @Override
    public IFidelidade getCategoria() {
        return this.categoria;
    }
}
```

**Quando usar:** Quando múltiplas classes precisam expor a mesma dependência.

---

## COMPARAÇÃO DOS 3 TIPOS

| Tipo | Onde passa | Obrigatória? | Pode mudar? | Quando usar |
|------|-----------|--------------|-------------|-------------|
| **Construtor** | Construtor | Sim | Não | Dependência essencial |
| **Setter** | Método set | Não | Sim | Dependência opcional/dinâmica |
| **Interface** | Implementação | Sim | Não | Múltiplas classes com mesma dependência |

---

## INJEÇÃO DE DEPENDÊNCIA NO XULAMBS

### Exemplo 1: Fidelidade (Strategy + Injeção por Construtor)

```java
class Cliente {
    private IFidelidade categoria;
    
    public Cliente(String nome, IFidelidade categoria) {
        this.nome = nome;
        this.categoria = categoria; // injetada
    }
}

// Quem cria decide a estratégia
Cliente c1 = new Cliente("Ana", new XulambsJunior());
Cliente c2 = new Cliente("Bruno", new XulambsSenior());
```

**Por que injeção por construtor?** O Cliente PRECISA de uma categoria para funcionar. Não faz sentido Cliente sem fidelidade.

### Exemplo 2: Pedido (Injeção por Construtor)

```java
abstract class Pedido {
    protected List<IProduto> itens;
    
    public Pedido() {
        itens = new LinkedList<>();
    }
    
    public int adicionar(IProduto item) {
        itens.add(item); // IProduto é injetado via parâmetro
        return itens.size();
    }
}

// Cada vez que adiciona um item, injeta uma implementação
pedido.adicionar(new Pizza(3));
pedido.adicionar(new Sanduiche());
```

### Exemplo 3: Pagamento (Injeção por Construtor)

```java
class Pedido {
    private MetodoPagamento metodo;
    
    public Pedido(MetodoPagamento metodo) {
        this.metodo = metodo; // injetado
    }
    
    public void finalizar(double valor) {
        if (metodo.validar()) {
            metodo.processar(valor);
        }
    }
}

// Quem cria o pedido decide o pagamento
Pedido p1 = new Pedido(new PagamentoCartao("1234567890123456"));
Pedido p2 = new Pedido(new PagamentoPix("sua@chave.pix"));
```

---

## INJEÇÃO vs CRIAÇÃO DIRETA

### Criação Direta (ERRADO na maioria dos casos)

```java
class Cliente {
    private IFidelidade categoria;
    
    public Cliente() {
        this.categoria = new XulambsJunior(); // decisão interna
    }
}
```

**Problema:** Quem decide é a classe interna. Difícil de trocar e testar.

### Injeção de Dependência (CORRETO)

```java
class Cliente {
    private IFidelidade categoria;
    
    public Cliente(IFidelidade categoria) {
        this.categoria = categoria; // decisão externa
    }
}
```

**Vantagem:** Quem decide é quem cria o objeto. Flexível e testável.

---

## RELAÇÃO COM SOLID

### Dependency Inversion Principle (DIP)

> "Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações."

```java
// ❌ VIOLAÇÃO - Cliente depende de classe concreta
class Cliente {
    private XulambsJunior categoria; // classe concreta
}

// ✅ CORRETO - Cliente depende de abstração (interface)
class Cliente {
    private IFidelidade categoria; // interface
}
```

**A injeção de dependência é a FERRAMENTA que permite aplicar o DIP.**

### Interface Segregation Principle (ISP)

> "Clientes não devem ser forçados a depender de métodos que não usam."

```java
// ❌ Interface gigante
interface ICadastro {
    void cadastrar();
    void remover();
    void atualizar();
    void buscar();
    void listar();
}

// ✅ Interfaces pequenas e especializadas
interface ICadastravel {
    void cadastrar();
}

interface Buscavel {
    void buscar();
}
```

---

## INJEÇÃO EM TESTES (JUnit)

A injeção de dependência facilita **MUITO** os testes.

### Sem Injeção (Difícil de testar)

```java
class ClienteTest {
    @Test
    void deveAplicarDesconto() {
        Cliente c = new Cliente("Ana");
        // Como testar se não consigo mudar a categoria?
    }
}
```

### Com Injeção (Fácil de testar)

```java
class ClienteTest {
    @Test
    void deveAplicarDescontoSenior() {
        // Arrange
        IFidelidade mockSenior = new XulambsSenior();
        Cliente c = new Cliente("Ana", mockSenior);
        
        // Act
        double desconto = c.calcularDesconto(pedido);
        
        // Assert
        assertEquals(20.0, desconto, 0.01);
    }
}
```

---

## CHECKLIST INJEÇÃO DE DEPENDÊNCIA

- [ ] Dependência declarada como **interface** (não classe concreta)?
- [ ] Construtor com **validação** (não aceitar null)?
- [ ] Quem **cria** o objeto decide a implementação?
- [ ] Classe não decide internamente qual implementação usar?
- [ ] Dependência **obrigatória** → injeção por construtor?
- [ ] Dependência **opcional** → injeção por setter?

---

## RESUMO RÁPIDO

| Conceito | Significado |
|----------|-------------|
| **Dependência** | Classe precisa de outra para funcionar |
| **Injeção** | Passar a dependência de fora (não criar internamente) |
| **Construtor** | Para dependências obrigatórias |
| **Setter** | Para dependências opcionais/dinâmicas |
| **Acoplamento** | O quão "grudadas" estão as classes |
| **Desacoplamento** | Classes independentes (via interface + injeção) |

### Regra de Ouro

> **Uma classe nunca deve decidir QUAL implementação usar.** Quem cria o objeto decide. A classe só sabe que recebe uma interface.
