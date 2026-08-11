# PADRÕES DE PROJETO - Guia Completo

## O QUE É UM PADRÃO DE PROJETO?

Um padrão de projeto é uma **solução reutilizável para um problema recorrente**. É como uma "receita de bolo" que já foi testada e funciona.

Em vez de inventar a roda toda vez, você usa uma solução que já funciona.

---

## PADRÕES MAIS IMPORTANTES PARA A PROVA

---

### 1. FACTORY METHOD (Fábrica)

#### O Problema

Você tem várias classes que fazem coisas parecidas, mas não sabe em tempo de código **qual classe criar**.

```java
// ❌ SEM FACTORY - código ruim
Pedido pedido;
if (tipo.equals("local")) {
    pedido = new PedidoLocal();
} else if (tipo.equals("entrega")) {
    pedido = new PedidoEntrega(distancia);
}
// E se amanhã surgir "retirada"? Precisa mexer aqui de novo!
```

#### A Solução

Uma **classe fábrica** que sabe qual implementação criar.

```java
// A FÁBRICA decide qual classe criar
public class PedidoFactory {
    public static Pedido criar(String tipo, double distancia) {
        return switch (tipo) {
            case "local" -> new PedidoLocal();
            case "entrega" -> new PedidoEntrega(distancia);
            default -> throw new IllegalArgumentException("Tipo inválido");
        };
    }
}

// Uso
Pedido pedido = PedidoFactory.criar("entrega", 5.0);
```

#### Como Funciona

```
Cliente → PedidoFactory.criar("local") → PedidoLocal
Cliente → PedidoFactory.criar("entrega") → PedidoEntrega
```

A fábrica **esconde** a complexidade de criar o objeto certo.

#### Quando Usar

- Quando você não sabe **qual classe** criar em tempo de código
- Quando a criação do objeto envolve **lógica complexa**
- Quando quer **centralizar** a criação de objetos

#### No Código Xulambs

```java
// Exemplo: Gerador de Pedidos
public class GeradorPedidos {
    public static Pedido gerar(String tipo) {
        return switch (tipo) {
            case "local" -> new PedidoLocal();
            case "entrega" -> new PedidoEntrega(Math.random() * 20);
            default -> new PedidoLocal();
        };
    }
}
```

---

### 2. STRATEGY (Estratégia)

#### O Problema

Você tem um comportamento que **muda** dependendo do contexto, e não quer usar `if-else` ou `switch` cheio.

```java
// ❌ SEM STRATEGY - código ruim
class Pedido {
    double calcularDesconto(String tipoCliente) {
        if (tipoCliente.equals("junior")) return 0;
        else if (tipoCliente.equals("pleno")) return 0.1;
        else if (tipoCliente.equals("senior")) return 0.2;
        // E se surgir "diamante"? Precisa mexer aqui!
    }
}
```

#### A Solução

Cada comportamento vira uma **classe separada** que implementa uma interface.

```java
// A INTERFACE define o contrato
interface IFidelidade {
    double descontoPedido(Pedido pedido);
}

// Cada estratégia é uma classe
class XulambsJunior implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return 0; // sem desconto
    }
}

class XulambsPleno implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return pedido.precoAPagar() * 0.1; // 10%
    }
}

class XulambsSenior implements IFidelidade {
    @Override
    public double descontoPedido(Pedido pedido) {
        return pedido.precoAPagar() * 0.2; // 20%
    }
}

// O Pedido usa a estratégia
class Cliente {
    private IFidelidade categoria; // pode ser qualquer uma
    
    public void registrarPedido(Pedido pedido) {
        double desconto = categoria.descontoPedido(pedido); // polimorfismo
        pedido.aplicarDesconto(desconto);
    }
}
```

#### Como Funciona

```
Cliente com categoria Junior
    → categoria.descontoPedido(pedido)
    → XulambsJunior.descontoPedido() retorna 0

Cliente com categoria Pleno
    → categoria.descontoPedido(pedido)
    → XulambsPleno.descontoPedido() retorna 10%
```

#### Quando Usar

- Quando um comportamento **muda** dependendo do contexto
- Quando você quer **trocar comportamento** em tempo de execução
- Quando quer evitar `if-else` ou `switch` longos

---

### 3. TEMPLATE METHOD (Método Modelo)

#### O Problema

Várias classes têm o **mesmo fluxo**, mas passos diferentes.

```java
// ❌ SEM TEMPLATE - código repetido
class PedidoLocal {
    void processar() {
        System.out.println("1. Criar pedido");
        System.out.println("2. Adicionar itens");
        System.out.println("3. Calcular taxa de serviço"); // diferente
        System.out.println("4. Fechar pedido");
    }
}

class PedidoEntrega {
    void processar() {
        System.out.println("1. Criar pedido");
        System.out.println("2. Adicionar itens");
        System.out.println("3. Calcular taxa de entrega"); // diferente
        System.out.println("4. Fechar pedido");
    }
}
```

#### A Solução

A classe mãe define o **fluxo**, as filhas definem os **passos específicos**.

```java
// Classe abstrata com o fluxo
abstract class Pedido {
    // Template Method - define o fluxo
    public final void processar() {
        criarPedido();
        adicionarItens();
        calcularTaxa();  // método abstrato - cada filho implementa diferente
        fecharPedido();
    }
    
    private void criarPedido() {
        System.out.println("1. Criar pedido");
    }
    
    private void adicionarItens() {
        System.out.println("2. Adicionar itens");
    }
    
    protected abstract void calcularTaxa(); // cada filho sabe como fazer
    
    private void fecharPedido() {
        System.out.println("4. Fechar pedido");
    }
}

// Filhas implementam o passo específico
class PedidoLocal extends Pedido {
    @Override
    protected void calcularTaxa() {
        System.out.println("3. Calcular taxa de serviço: 10%");
    }
}

class PedidoEntrega extends Pedido {
    @Override
    protected void calcularTaxa() {
        System.out.println("3. Calcular taxa de entrega: R$5-12");
    }
}
```

#### Como Funciona

```
PedidoLocal p = new PedidoLocal();
p.processar();
// 1. Criar pedido
// 2. Adicionar itens
// 3. Calcular taxa de serviço: 10%
// 4. Fechar pedido

PedidoEntrega p2 = new PedidoEntrega();
p2.processar();
// 1. Criar pedido
// 2. Adicionar itens
// 3. Calcular taxa de entrega: R$5-12
// 4. Fechar pedido
```

#### Quando Usar

- Quando várias classes têm o **mesmo fluxo** mas passos diferentes
- Quando quer **evitar repetição** de código
- Quando o fluxo é **fixo** mas os passos variam

---

### 4. DECORATOR (Decorador)

#### O Problema

Você quer **adicionar comportamentos** a um objeto **sem modificar** a classe original.

```java
// ❌ SEM DECORATOR - herança problemática
class Pizza { ... }
class PizzaComBorda extends Pizza { ... }
class PizzaComBordaEChocolate extends Pizza { ... }
class PizzaComBordaEChocolateECheddar extends Pizza { ... }
// Cresce infinitamente!
```

#### A Solução

Uma **classe decoradora** que "embrulha" o objeto original e adiciona comportamento.

```java
// Interface comum
interface IProduto {
    double valorAPagar();
    String getDescricao();
}

// Classe base
class PizzaSimples implements IProduto {
    @Override
    public double valorAPagar() { return 29.0; }
    
    @Override
    public String getDescricao() { return "Pizza simples"; }
}

// Decorador abstrato
abstract class DecoradorPizza implements IProduto {
    protected IProduto pizzaDecorada;
    
    public DecoradorPizza(IProduto pizza) {
        this.pizzaDecorada = pizza;
    }
}

// Decorador concreto: borda
class ComBorda extends DecoradorPizza {
    public ComBorda(IProduto pizza) {
        super(pizza);
    }
    
    @Override
    public double valorAPagar() {
        return pizzaDecorada.valorAPagar() + 5.0; // adiciona borda
    }
    
    @Override
    public String getDescricao() {
        return pizzaDecorada.getDescricao() + " com borda";
    }
}

// Decorador concreto: extra queijo
class ComExtraQueijo extends DecoradorPizza {
    public ComExtraQueijo(IProduto pizza) {
        super(pizza);
    }
    
    @Override
    public double valorAPagar() {
        return pizzaDecorada.valorAPagar() + 3.0; // adiciona queijo
    }
    
    @Override
    public String getDescricao() {
        return pizzaDecorada.getDescricao() + " extra queijo";
    }
}
```

#### Como Usar

```java
// Pizza simples
IProduto p1 = new PizzaSimples();
System.out.println(p1.valorAPagar());  // 29.0
System.out.println(p1.getDescricao()); // "Pizza simples"

// Pizza com borda
IProduto p2 = new ComBorda(new PizzaSimples());
System.out.println(p2.valorAPagar());  // 34.0
System.out.println(p2.getDescricao()); // "Pizza simples com borda"

// Pizza com borda E extra queijo
IProduto p3 = new ComExtraQueijo(new ComBorda(new PizzaSimples()));
System.out.println(p3.valorAPagar());  // 37.0
System.out.println(p3.getDescricao()); // "Pizza simples com borda extra queijo"
```

#### Quando Usar

- Quando quer **adicionar funcionalidades** sem modificar a classe
- Quando herança não é viável (cresce infinitamente)
- Quando quer **combinar** comportamentos dinamicamente

---

### 5. OBSERVER (Observador)

#### O Problema

Quando algo muda, **vários outros objetos** precisam ser notificados.

```java
// ❌ SEM OBSERVER - código ruim
class Pedido {
    void mudarEstado() {
        // quando muda o estado, precisa:
        atualizarEstoque();
        enviarEmail();
        notificarCliente();
        gerarRelatorio();
        // E se amanhã precisar de mais uma coisa?
    }
}
```

#### A Solução

O objeto **notifica** quem está interessado, sem saber quem são.

```java
// Interface do observador
interface Observador {
    void notificar(String evento);
}

// Sujeito (quem é observado)
class Pedido {
    private List<Observador> observadores = new ArrayList<>();
    
    public void adicionarObservador(Observador obs) {
        observadores.add(obs);
    }
    
    public void mudarEstado(String novoEstado) {
        // lógica de mudança...
        
        // notifica todos os observadores
        for (Observador obs : observadores) {
            obs.notificar("Estado mudou para: " + novoEstado);
        }
    }
}

// Observadores concretos
class Estoque implements Observador {
    @Override
    public void notificar(String evento) {
        System.out.println("Estoque atualizado: " + evento);
    }
}

class EmailService implements Observador {
    @Override
    public void notificar(String evento) {
        System.out.println("Email enviado: " + evento);
    }
}
```

#### Como Usar

```java
Pedido pedido = new Pedido();
pedido.adicionarObservador(new Estoque());
pedido.adicionarObservador(new EmailService());

pedido.mudarEstado("Fechado");
// Estoque atualizado: Estado mudou para: Fechado
// Email enviado: Estado mudou para: Fechado
```

#### Quando Usar

- Quando **vários objetos** precisam reagir a mudanças de um objeto
- Quando quer **desacoplamento** (o sujeito não precisa saber quem observa)

---

### 6. SINGLETON (Instância Única)

#### O Problema

Você quer garantir que exista **apenas uma instância** de uma classe.

```java
// ❌ SEM SINGLETON - pode criar várias instâncias
Database db1 = new Database();
Database db2 = new Database(); // agora tem 2 conexões!
```

#### A Solução

A classe **controla** sua própria instância.

```java
public class Database {
    private static Database instancia; // única instância
    
    // Construtor privado - ninguém pode criar com new
    private Database() {
        // conecta ao banco
    }
    
    // Método público para pegar a instância
    public static Database getInstancia() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }
}
```

#### Como Usar

```java
Database db1 = Database.getInstancia();
Database db2 = Database.getInstancia();

System.out.println(db1 == db2); // true - são a mesma instância!
```

#### Quando Usar

- Quando precisa de **apenas uma instância** (banco de dados, configurações)
- Quando quer **controlar acesso** a um recurso compartilhado

---

## RESUMO DOS PADRÕES

| Padrão | Problema | Solução |
|--------|----------|---------|
| **Factory** | Qual classe criar? | Fábrica decide |
| **Strategy** | Comportamento muda? | Interface + implementações |
| **Template** | Mesmo fluxo, passos diferentes? | Classe mãe define fluxo, filhas implementam passos |
| **Decorator** | Adicionar comportamento sem modificar? | "Embrulhar" objeto |
| **Observer** | Vários precisam reagir a mudanças? | Notificação automática |
| **Singleton** | Garantir apenas uma instância? | Classe controla sua instância |

---

## COMO IDENTIFICAR QUAL PADRÃO USAR

| Situação | Padrão |
|----------|--------|
| "Preciso criar objetos diferentes dependendo da situação" | Factory |
| "O comportamento muda em tempo de execução" | Strategy |
| "Várias classes têm o mesmo fluxo mas passos diferentes" | Template |
| "Quero adicionar funcionalidade sem modificar a classe" | Decorator |
| "Quando algo muda, vários precisam saber" | Observer |
| "Preciso de apenas uma instância" | Singleton |

---

## CHECKLIST PADRÕES

- [ ] Factory: centraliza criação de objetos?
- [ ] Strategy: comportamento é uma interface separada?
- [ ] Template: fluxo está na classe mãe, passos nas filhas?
- [ ] Decorator: "embrulha" objeto original?
- [ ] Observer: notifica observadores automaticamente?
- [ ] Singleton: garantido apenas uma instância?
