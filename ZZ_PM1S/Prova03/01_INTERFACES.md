# INTERFACES - Guia Completo

## O QUE É UMA INTERFACE?

Uma interface é um **contrato**. Ela diz: "Qualquer classe que implementar essa interface, é OBRIGADA a ter esses métodos."

Pense assim: quando você assina um contrato de aluguel, o contrato diz o que você DEVE fazer (pagar aluguel, cuidar do imóvel). A interface faz a mesma coisa com classes.

---

## POR QUE EXISTE INTERFACE?

### Problema sem Interface

Imagine um sistema de pagamento:

```java
class PagamentoCartao {
    public void processar(double valor) {
        System.out.println("Processando cartão: " + valor);
    }
}

class PagamentoBoleto {
    public void processar(double valor) {
        System.out.println("Processando boleto: " + valor);
    }
}

class PagamentoPix {
    public void processar(double valor) {
        System.out.println("Processando PIX: " + valor);
    }
}
```

**O problema:** Cada classe tem seu próprio nome, mas faz a mesma coisa. Quando você quer processar pagamento, não sabe qual classe usar.

### Solução com Interface

```java
// 1. Define o CONTRATO (o que todas devem fazer)
interface MetodoPagamento {
    void processar(double valor);
    boolean validar();
}

// 2. Cada classe CUMPRE o contrato
class PagamentoCartao implements MetodoPagamento {
    private String numeroCartao;
    
    public PagamentoCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    
    @Override
    public void processar(double valor) {
        System.out.println("Processando cartão: " + valor);
    }
    
    @Override
    public boolean validar() {
        return numeroCartao.length() == 16;
    }
}

class PagamentoBoleto implements MetodoPagamento {
    @Override
    public void processar(double valor) {
        System.out.println("Processando boleto: " + valor);
    }
    
    @Override
    public boolean validar() {
        return true; // boleto sempre válido
    }
}

class PagamentoPix implements MetodoPagamento {
    private String chave;
    
    public PagamentoPix(String chave) {
        this.chave = chave;
    }
    
    @Override
    public void processar(double valor) {
        System.out.println("Processando PIX para " + chave + ": " + valor);
    }
    
    @Override
    public boolean validar() {
        return chave != null && !chave.isEmpty();
    }
}
```

**Agora:** Você pode usar qualquer pagamento sem saber qual é:

```java
class Pedido {
    private MetodoPagamento metodo;
    
    public Pedido(MetodoPagamento metodo) {
        this.metodo = metodo; // aceita QUALQUER implementação
    }
    
    public void finalizarCompra(double valor) {
        if (metodo.validar()) {
            metodo.processar(valor);
        }
    }
}

// Na prática:
Pedido p1 = new Pedido(new PagamentoCartao("1234567890123456"));
Pedido p2 = new Pedido(new PagamentoPix("sua@chave.pix"));
Pedido p3 = new Pedido(new PagamentoBoleto());
```

**Benefício:** Para adicionar Apple Pay, Google Pay, etc., você só cria nova classe. NÃO mexe em Pedido.

---

## REGRAS DA INTERFACE

### 1. Toda classe que implementa, DEVE ter TODOS os métodos

```java
interface IProduto {
    double valorAPagar();
    String getNome();
}

// ❌ ERRADO - esqueceu getNome()
class Pizza implements IProduto {
    @Override
    public double valorAPagar() { return 29.0; }
    // ERRO DE COMPILAÇÃO - falta getNome()
}

// ✅ CORRETO
class Pizza implements IProduto {
    @Override
    public double valorAPagar() { return 29.0; }
    
    @Override
    public String getNome() { return "Pizza"; }
}
```

### 2. Interface NÃO tem construtor (não pode instanciar)

```java
// ❌ ERRADO
MetodoPagamento m = new MetodoPagamento(); // ERRO!

// ✅ CORRETO - usa a implementação
MetodoPagamento m = new PagamentoCartao("1234567890123456");
```

### 3. Variável do tipo interface recebe qualquer implementação

```java
MetodoPagamento m1 = new PagamentoCartao("...");
MetodoPagamento m2 = new PagamentoPix("...");
MetodoPagamento m3 = new PagamentoBoleto();
```

### 4. Interface pode estender outra interface

```java
interface IProduto {
    double valorAPagar();
}

interface IPersonalizavel {
    int maxIngredientes();
}

// IProdutoEspecial herda tudo de IProduto e IPersonalizavel
interface IProdutoEspecial extends IProduto, IPersonalizavel {
    String getDescricao();
}
```

---

## INTERFACE vs HERANÇA

### Quando usar HERANÇA?

Quando as classes têm uma relação **"É UM"** natural.

```java
// Animal É UM ser vivo
class Animal { ... }
class Cachorro extends Animal { ... } // Cachorro É UM Animal
class Gato extends Animal { ... }     // Gato É UM Animal
```

### Quando usar INTERFACE?

Quando as classes **FAZEM** a mesma coisa, mas não são naturalmente relacionadas.

```java
// Pizza e Sanduiche não são da mesma "família"
// mas ambos PODEM SER personalizados
interface IPersonalizavel {
    int maxIngredientes();
}

class Pizza implements IPersonalizavel { ... }
class Sanduiche implements IPersonalizavel { ... }
```

### Regra de Ouro

| Pergunta | Resposta |
|----------|----------|
| "Eu **SOU** esse algo?" | Herança |
| "Eu **FAÇO** esse algo?" | Interface/Composição |

---

## EXEMPLO REAL DO XULAMBS

### Interface IProduto

```java
public interface IProduto extends Comparable<IProduto> {
    public double valorAPagar();  
}
```

- Toda classe que é "produto" DEVE ter `valorAPagar()`
- Estende `Comparable` para poder ser ordenada

### Pizza implementa IProduto

```java
public class Pizza implements IProduto, IPersonalizavel {
    @Override
    public double valorAPagar() {
        return borda.valorBorda() + PRECO_BASE + valorAdicionais();
    }
    
    @Override
    public int maxIngredientes() {
        return MAX_INGREDIENTES;
    }
    
    @Override
    public String getNome() {
        return "uma pizza";
    }
}
```

### Sanduiche implementa IProduto

```java
public class Sanduiche implements IProduto {
    @Override
    public double valorAPagar() {
        return precoBase + valorAdicionais();
    }
}
```

### Pedido usa IProduto (não Pizza diretamente)

```java
public abstract class Pedido {
    protected List<IProduto> itens; // aceita QUALQUER produto
    
    public int adicionar(IProduto item) {
        itens.addLast(item);
        return itens.size();
    }
}
```

**Vantaggio:** Pedido não precisa saber se é Pizza ou Sanduiche. Só sabe que é um IProduto.

---

## INTERFACE FUNCIONAL (para Lambda)

Uma interface funcional tem **APENAS UM método abstrato**.

```java
// Interface funcional - 1 método abstrato
interface Comparador {
    int comparar(String a, String b);
}

// Pode usar Lambda
Comparador c = (a, b) -> a.compareTo(b);
```

### Exemplos de Interfaces Funcionais

```java
// Comparator - compara dois valores
Comparator<Pessoa> porIdade = (p1, p2) -> Integer.compare(p1.idade, p2.idade);

// Consumer - recebe valor, não retorna nada
Consumer<String> imprimir = texto -> System.out.println(texto);

// Function - recebe valor, retorna outro valor
Function<Integer, String> paraTexto = n -> "Número: " + n;

// Predicate - recebe valor, retorna boolean
Predicate<Integer> ehPar = n -> n % 2 == 0;
```

---

## CÓDIGO XULAMBS - ANÁLISE

### As Interfaces do Sistema

```java
// IProduto - todo produto tem preço
interface IProduto extends Comparable<IProduto> {
    double valorAPagar();
}

// IPersonalizavel - coisas que podem ter ingredientes
interface IPersonalizavel {
    int maxIngredientes();
}

// IFidelidade - sistema de desconto
interface IFidelidade {
    double descontoPedido(Pedido pedido);
    
    // Método estático - define qual categoria usar
    static IFidelidade definirCategoria(List<Pedido> pedidos) {
        // lógica para definir categoria
    }
}
```

### Implementações

```java
// Pizza implementa AMBAS as interfaces
public class Pizza implements IProduto, IPersonalizavel {
    @Override
    public double valorAPagar() { ... }
    
    @Override
    public int maxIngredientes() { return 8; }
}

// Sanduiche implementa apenas IProduto
public class Sanduiche implements IProduto {
    @Override
    public double valorAPagar() { ... }
}

// Categorias de fidelidade implementam IFidelidade
public class XulambsJunior implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return 0; // sem desconto
    }
}

public class XulambsPleno implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return pedido.precoAPagar() * 0.1; // 10% desconto
    }
}

public class XulambsSenior implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return pedido.precoAPagar() * 0.2; // 20% desconto
    }
}
```

### Como Funciona na Prática

```java
public class Cliente {
    private IFidelidade categoria; // pode ser JUNIOR, PLENO ou SENIOR
    
    public Cliente(int id, String nome) {
        this.categoria = new XulambsJunior(); // começa como junior
    }
    
    public IFidelidade verificarCategoria() {
        // lógica que retorna a categoria correta
        categoria = IFidelidade.definirCategoria(pedidos);
        return categoria;
    }
    
    public int registrarPedido(Pedido novo) {
        double desconto = categoria.descontoPedido(novo); // polimorfismo
        novo.aplicarDesconto(desconto);
        pedidos.addLast(novo);
        return pedidos.size();
    }
}
```

**O que acontece:** O Cliente não precisa saber se é Junior, Pleno ou Senior. Ele só chama `categoria.descontoPedido()` e cada implementação sabe o que fazer.

---

## RESUMO RÁPIDO

| Conceito | Significado |
|----------|-------------|
| `interface` | Contrato - define o que a classe DEVE fazer |
| `implements` | Classe cumpre o contrato |
| `extends` (em interface) | Interface herda de outra interface |
| Variável do tipo interface | Pode receber qualquer implementação |
| Interface funcional | 1 método abstrato - permite Lambda |
| "Programe para interface" | Use `IProduto` em vez de `Pizza` |

---

## CHECKLIST INTERFACES

- [ ] Interface define apenas o que a classe DEVE fazer?
- [ ] Classe implementa TODOS os métodos da interface?
- [ ] Usando variável do tipo interface (não classe concreta)?
- [ ] Interface funcional tem apenas 1 método abstrato?
- [ ] Novo tipo = nova classe implementando interface (não modificar existente)?
