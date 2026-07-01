# GUIA DEFINITIVO PARA IR BEM NA PROVA 3

**Professor:** João Caram
**Disciplina:** Programação Modular
**Base:** XulambsPizza / Exercícios semanais

---

## COMO FUNCIONA A PROVA

A prova vai te dar um **enunciado** (situacao-problema) e pedir:
1. **Diagrama UML** (classes, relacionamentos, atributos, métodos)
2. **Código Java** (métodos específicos, às vezes classes inteiras)

**Regra de ouro:** Não entre em pânico. Leia o enunciado 2 vezes. Marque tudo.

---

## PARTE 1: COMO LER O ENUNCIADO (passo-a-passo)

### Passo 1: Marque TODOS os substantivos → são CANDIDATOS a classe

Exemplo:
> "Um Pedido pode conter diversas Pizzas. Cada Pizza tem um preço."

Substantivos: **Pedido**, **Pizza** → criar classes

### Passo 2: Marque TODOS os verbos → são CANDIDATOS a métodos

Exemplo:
> "O aluno pode matricular-se em cursos. O professor avalia os cursos."

Verbos: **matricular**, **avaliar** → criar métodos

### Passo 3: Marque expressões-chave para parâmetros

| Expressão no enunciado | Significado |
|------------------------|-------------|
| "a partir de X" | X é **parâmetro** |
| "recebendo X" | X é **parâmetro** |
| "para determinado X" | X é **parâmetro** |
| "quando X acontece" | X pode ser **parâmetro** ou **condição** |

### Passo 4: Pergunte "EU SOU ou EU FAÇO?"

| Pergunta | Resposta |
|----------|----------|
| "Classe A É UM tipo de B?" | **Herança** (A extends B) |
| "Classe A FAZ algo que B também faz?" | **Interface** (A implements I) |
| "Classe A TEM uma instância de B?" | **Composição/Agregação** (atributo) |
| "Classe A USA B momentaneamente?" | **Associação** (parâmetro) |

### Passo 5: Identifique o FIXO vs o MUDA

| O que é | Tipo |
|---------|------|
| Conjunto fixo de valores (gasolina, etanol) | **Enum** |
| Comportamento que muda dependendo do contexto | **Interface** |
| Código compartilhado entre classes filhas | **Classe abstrata** |
|fluxo fixo com passos diferentes | **Template Method** |

---

## PARTE 2: COMO DESENHAR O DIAGRAMA UML

### Símbolos que VOCÊ PRECISA saber

| Símbolo | Nome | Quando usar |
|---------|------|-------------|
| `+` | public | Método/atributo acessível por todos |
| `-` | private | Método/atributo só pela classe |
| `#` | protected | Classe e filhas |
| `~` | package | Mesmo pacote |
| `◆` | Losango cheio | Contenção (A CONTÉM B, B não existe sem A) |
| `◇` | Losango vazio | Agregação (A TEM B, B existe sozinha) |
| `→` | Seta simples | Associação (A USA B) |
| `△` | Triângulo vazio | Herança (A É UM B) |
| `△` tracejado | Triângulo tracejado | Implementação (A IMPLEMENTA interface) |
| `<<interface>>` | Stereotype | Marca interface |
| `<<enum>>` | Stereotype | Marca enum |

### Regra do losango

**O losango SEMPRE fica do lado de quem TEM/CONTÉM.**

```
+--------+  ◆──── +--------+
| Pedido |        | Pizza  |   ← Pedido CONTÉM Pizza
+--------+        +--------+
     │                 │
     │ CONTÉM          │ NÃO EXISTE SOZINHA
     └─────────────────┘
```

### Regra da herança

**O triângulo aponta para a MÃE.**

```
    +--------+
    | Animal |   ← mãe
    +--------+
        △
        │
+--------+
|Cachorro|      ← filha
+--------+
```

### Regra da interface

**Triângulo TRACEJADO (linhas tracejadas).**

```
+------------------+
| <<interface>>    |
|    IProduto      |
+------------------+
        △  ← tracejado
        │
+--------+
|  Pizza |
+--------+
```

### Checklist UML antes de entregar

- [ ] Losango do lado de quem TEM/CONTÉM?
- [ ] Triângulo de herança apontando para a mãe?
- [ ] Triângulo tracejado para implementação?
- [ ] Atributos com modificador correto (-, #, +)?
- [ ] Métodos com modificador correto?
- [ ] Enum com `<<enum>>`?
- [ ] Interface com `<<interface>>`?
- [ ] Atributos private por padrão?

---

## PARTE 3: COMO ESCREVER O CÓDIGO

### Regra 1: Construtor - SEMPRE validar

```java
public Aluno(String nome, int idade) {
    // PRIMEIRO: validar
    if (nome == null || nome.isBlank())
        throw new IllegalArgumentException("Nome inválido");
    if (idade < 0)
        throw new IllegalArgumentException("Idade inválida");
    // DEPOIS: atribuir
    this.nome = nome;
    this.idade = idade;
}
```

**Por quê?** Se não validar, o objeto pode ficar em estado inválido.

### Regra 2: Construtor da filha - super() é PRIMEIRA LINHA

```java
class CursoPresencial extends Curso {
    private MaterialDitatico material;

    public CursoPresencial(String nome, int horas, double valorHora, MaterialDitatico material) {
        super(nome, horas, valorHora);  // ← PRIMEIRA LINHA, sempre!
        this.material = material;
    }
}
```

**Por quê?** `super()` roda a validação da mãe. Se não chamar, a mãe não valida.

### Regra 3: Atributos sempre private

```java
public class Pizza {
    private double preco;        // ← private
    private List<String> ingredientes;  // ← private
}
```

**Por quê?** Encapsulamento. Quem de fora precisa acessar, usa getter.

### Regra 4: Getter quando precisa acessar de fora

```java
public class Veiculo {
    private Motor motor;

    // Se outro precisa saber qual motor, cria getter:
    public Motor motorUtilizado() {
        return motor;
    }
}
```

### Regra 5: Retorno do tipo certo

```java
// ERRADO - retorna int mas assinatura diz Veiculo
public Veiculo cadastroVeiculo(Veiculo v) {
    veiculos.add(v);
    return veiculos.size();  // size() retorna int!
}

// CORRETO
public int cadastroVeiculo(Veiculo v) {
    veiculos.add(v);
    return veiculos.size();  // int = int
}
```

### Regra 6: Usar List<> não ArrayList<>

```java
// ERRADO - usando classe concreta
private ArrayList<String> nomes = new ArrayList<>();

// CORRETO - usando interface
private List<String> nomes = new ArrayList<>();
```

### Regra 7: Diamond operator sempre

```java
// ERRADO
private List<Veiculo> veiculos = new ArrayList();

// CORRETO
private List<Veiculo> veiculos = new ArrayList<>();
```

### Regra 8: Validação em método que recebe lista

```java
public int adicionar(IProduto item) {
    if (item == null)
        throw new IllegalArgumentException("Produto não foi criado corretamente");
    itens.addLast(item);
    return itens.size();
}
```

---

## PARTE 4: PADRÕES QUE CAEM NA PROVA

### 4.1 Interface + Polimorfismo (TODO exercício cai)

**Interface = contrato.** Cada classe implementa do jeito dela.

```java
// O contrato
public interface IPropulsao {
    double autonomiaAtual();
    double autonomiaMaxima();
}

// Implementação 1: Combustão
public class Combustao implements IPropulsao {
    private double litrosAtuais;
    private double consumoMedio;

    @Override
    public double autonomiaAtual() {
        return litrosAtuais * consumoMedio;
    }
}

// Implementação 2: Elétrico
public class Eletrico implements IPropulsao {
    private double cargaAtual;
    private double consumoBateria;

    @Override
    public double autonomiaAtual() {
        return cargaAtual * consumoBateria;
    }
}
```

**Como usar no código:**
```java
// Variável do tipo interface recebe QUALQUER implementação
IPropulsao motor = new Combustao(litros, consumo);
IPropulsao motor2 = new Eletrico(carga, consumo);
```

### 4.2 Composição (Hibrido)

**Hibrido NÃO herda de ambos.** Ele TEM os dois (composição).

```java
public class Hibrido implements IPropulsao {
    private Combustao motorCombustao;    // TEM um de combustão
    private Eletrico motorEletrico;      // TEM um elétrico
    private IPropulsao motorAtual;       // qual está usando agora

    @Override
    public double autonomiaAtual() {
        return motorAtual.autonomiaAtual();  // delega para o atual
    }

    public IPropulsao alternarPropulsao(String tipo) {
        if (tipo.equals("combustao")) {
            motorAtual = motorCombustao;
        } else {
            motorAtual = motorEletrico;
        }
        return motorAtual;
    }
}
```

### 4.3 Enum (valores fixos)

```java
public enum ECombustivel {
    GASOLINA(12),    // 12 km/l
    ETANOL(8),       // 8 km/l
    FLEX(10.5);      // 10.5 km/l

    private final double consumoMedio;

    // Construtor do enum (sempre private)
    ECombustivel(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }
}

// Uso:
ECombustivel tipo = ECombustivel.GASOLINA;
double consumo = tipo.getConsumoMedio();  // 12
```

### 4.4 Classe Abstrata

**Classe abstrata = não pode instanciar.** Só serve de base para filhas.

```java
public abstract class Curso {
    private String nome;
    protected double valorHora;

    // Método abstrato: filha OBRIGADA a implementar
    public abstract double valorTotal();

    // Método concreto: filha herda
    public String getNome() {
        return nome;
    }
}

// Filha OBRIGADA a implementar valorTotal()
public class CursoPresencial extends Curso {
    @Override
    public double valorTotal() {
        return valorAulas() + material.valorTotal();
    }
}
```

### 4.5 Template Method

**Classe mãe define o FLUXO, filhas definem os PASSOS.**

```java
public abstract class Pedido {
    // Fluxo fixo
    public final void processar() {
        criar();
        adicionarItens();
        calcularTaxa();   // ← cada filho faz diferente
        fechar();
    }

    protected abstract void calcularTaxa();  // filha implementa
}

public class PedidoLocal extends Pedido {
    @Override
    protected void calcularTaxa() {
        // taxa de serviço: 10%
    }
}

public class PedidoEntrega extends Pedido {
    @Override
    protected void calcularTaxa() {
        // taxa de entrega: R$5-12
    }
}
```

### 4.6 Factory Method

**Classe fábrica decide qual criar.**

```java
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
Pedido p = PedidoFactory.criar("entrega", 5.0);
```

### 4.7 Strategy

**Comportamento que muda = interface separada.**

```java
// Interface
interface IFidelidade {
    double descontoPedido(Pedido pedido);
}

// Implementações
class XulambsJunior implements IFidelidade {
    public double descontoPedido(Pedido p) { return 0; }
}

class XulambsPleno implements IFidelidade {
    public double descontoPedido(Pedido p) { return p.precoAPagar() * 0.1; }
}

// Uso
class Cliente {
    private IFidelidade categoria;  // pode trocar em tempo de execução
}
```

---

## PARTE 5: STREAMS (toda prova cai)

### Operações intermediárias (retornam stream - podem ser encadeadas)

| Operação | O que faz | Exemplo |
|----------|-----------|---------|
| `filter(predicate)` | Mantém quem atende condição | `.filter(p -> p.preco > 50)` |
| `map(function)` | Transforma cada elemento | `.map(Pedido::precoAPagar)` |
| `distinct()` | Remove duplicatas | `.distinct()` |
| `sorted()` | Ordena | `.sorted()` |

### Operações terminais (retornam resultado)

| Operação | O que faz | Exemplo |
|----------|-----------|---------|
| `toList()` | Converte para lista | `.toList()` |
| `count()` | Conta | `.count()` |
| `forEach(consumer)` | Executa ação | `.forEach(System.out::println)` |
| `min(comparator)` | Encontra mínimo | `.min(Comparator.comparing(...))` |
| `max(comparator)` | Encontra máximo | `.max(Comparator.comparing(...))` |
| `reduce(accumulator)` | Reduz a um valor | `.reduce(0, Double::sum)` |
| `findFirst()` | Encontra primeiro | `.findFirst()` |
| `orElseThrow()` | Valor ou exceção | `.orElseThrow(() -> new IllegalArgumentException(...))` |

### Receitas prontas (copie e adapte)

**Média de valores:**
```java
double media = pedidos.stream()
    .mapToDouble(Pedido::precoAPagar)
    .average()
    .orElse(0);
```

**Contar com condição:**
```java
long qtd = pedidos.stream()
    .filter(p -> p.precoAPagar() > media)
    .count();
```

**Filtrar + ordenar + coletar:**
```java
List<Pedido> resultado = pedidos.stream()
    .filter(p -> p.incluiDoceDeLeite())
    .sorted(Comparator.comparingDouble(Pedido::precoAPagar))
    .toList();
```

**Encontrar o maior:**
```java
Pedido maior = pedidos.stream()
    .max(Comparator.comparingDouble(Pedido::precoAPagar))
    .orElseThrow(() -> new IllegalArgumentException("Lista vazia"));
```

**Porcentagem:**
```java
double porcentagem = pedidos.stream()
    .filter(Pedido::incluiPizzaMaxIngredientes)
    .count() * 100.0 / pedidos.size();
```

**Melhor veículo para roteiro (usando filter + findFirst):**
```java
public Automovel melhorCarro(Roteiro roteiro) {
    return automoveis.stream()
        .filter(a -> a.propulsor().autonomiaAtual() >= roteiro.distancia())
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Nenhum carro adequado"));
}
```

**Sem usar getClass() nem instanceof (use polimorfismo):**
```java
// Todo Pedido tem precoAPagar() - use isso!
pedidos.stream()
    .filter(p -> p.precoAPagar() > 50)
    .toList();
```

---

## PARTE 6: LAMBDA E INTERFACES FUNCIONAIS

### Sintaxe

```java
// Um parâmetro
x -> x * 2

// Dois parâmetros
(a, b) -> a + b

// Múltiplas linhas
(x) -> {
    System.out.println(x);
    return x.length();
}

// Sem parâmetros
() -> System.out.println("Oi")
```

### Interfaces principais

| Interface | Recebe | Retorna | Exemplo |
|-----------|--------|---------|---------|
| `Comparator<T>` | 2 valores | int | `(p1, p2) -> Integer.compare(p1.idade, p2.idade)` |
| `Consumer<T>` | 1 valor | void | `texto -> System.out.println(texto)` |
| `Function<T,R>` | 1 valor | R | `n -> "Número: " + n` |
| `Predicate<T>` | 1 valor | boolean | `n -> n % 2 == 0` |

### Method Reference (atalho para lambda)

```java
// Lambda
nomes.forEach(nome -> System.out.println(nome));

// Method Reference (mais limpo)
nomes.forEach(System.out::println);
```

---

## PARTE 7: EXCEÇÕES

### Hierarquia

```
Throwable
  ├── Error          → erros da JVM (NÃO tratar)
  └── Exception
        ├── RuntimeException    → unchecked (não obrigatório tratar)
        └── Checked Exceptions  → obrigatório tratar
```

### Regras

```java
// TRY-CATCH-FINALLY
try {
    // código que pode dar erro
} catch (ExcecaoEspecifica e) {
    // código que roda SE der o erro
} catch (Exception e) {
    // genérico - pega qualquer outro
} finally {
    // SEMPRE executa
}

// LANÇAR exceção
throw new IllegalArgumentException("Mensagem de erro");

// Declarar que pode lançar
public void metodo() throws IOException { ... }
```

### Exceção customizada

```java
public class PedidoFechadoException extends IllegalStateException {
    public PedidoFechadoException(int id) {
        super("Não pode adicionar itens em pedido fechado. ID: " + id);
    }
}
```

### Regra de ouro

**NUNCA catch vazio!** Se captura e não faz nada, o erro some silenciosamente.

---

## PARTE 8: SOLID (cairá na prova)

### S - Single Responsibility

Uma classe faz UMA coisa. Se na resposta tem "e" ou "ou", faz demais.

```java
// ERRADO - faz duas coisas
class Aluno {
    void calcularMedia() { ... }
    void imprimirNaTela() { ... }  // problema!
}

// CORRETO - cada um sua responsabilidade
class Aluno {
    void calcularMedia() { ... }
}
class ExibidorDeAluno {
    void imprimirNaTela(Aluno a) { ... }
}
```

### O - Open/Closed

Aberta para extensão, fechada para modificação. Novo comportamento = nova classe.

```java
// ERRADO - toda vez que surge novo tipo, adiciona else if
class Notificador {
    void notificar(String tipo, String msg) {
        if (tipo.equals("email")) enviarEmail(msg);
        else if (tipo.equals("sms")) enviarSMS(msg);
        // cresce infinitamente!
    }
}

// CORRETO - usa herança + polimorfismo
abstract class Notificador {
    abstract void notificar(String msg);
}
class NotificadorEmail extends Notificador { ... }
class NotificadorSMS extends Notificador { ... }
```

### L - Liskov Substitution

Filha substitui mãe sem quebrar o programa.

```java
// ERRADO - Quadrado quebra contrato do Retangulo
class Retangulo {
    void setLargura(int l) { this.largura = l; }
    void setAltura(int a) { this.altura = a; }
}
class Quadrado extends Retangulo {
    void setLargura(int l) {
        this.largura = l;
        this.altura = l;  // força os dois - QUEBRA!
    }
}
```

### I - Interface Segregation

Interfaces especializadas. Uma interface não deve forçar métodos que a classe não usa.

### D - Dependency Inversion

Depender de abstrações (interfaces), não de classes concretas.

```java
// ERRADO
class Relatorio {
    private BancoDados bancoDados;
    public Relatorio() {
        this.bancoDados = new BancoDados();  // dependência concreta
    }
}

// CORRETO
interface BaseDados {
    List<Dados> obter();
}
class Relatorio {
    private BaseDados baseDados;
    public Relatorio(BaseDados baseDados) {
        this.baseDados = baseDados;  // depende da interface
    }
}
```

---

## PARTE 9: CHECKLIST FINAL (copie e leve para a prova)

### Antes de começar

- [ ] Li o enunciado 2 vezes?
- [ ] Marquei todos os substantivos (candidatos a classe)?
- [ ] Marquei todos os verbos (candidatos a método)?
- [ ] Identifiquei os parâmetros ("a partir de" = parâmetro)?
- [ ] Perguntei "EU SOU ou EU FAÇO?" para decidir herança/interface?

### Diagrama UML

- [ ] Losango do lado de quem TEM/CONTÉM?
- [ ] Triângulo de herança apontando para a mãe?
- [ ] Triângulo tracejado para implementação de interface?
- [ ] Atributos private (-) por padrão?
- [ ] Métodos public (+)?
- [ ] Enum com `<<enum>>`?
- [ ] Interface com `<<interface>>`?
- [ ] Contenção (◆) quando a parte não existe sem o todo?
- [ ] Agregação (◇) quando a parte existe sozinha?

### Código Java

- [ ] Construtores com validação (throw IllegalArgumentException)?
- [ ] Construtor filha chama super() como PRIMEIRA LINHA?
- [ ] Atributos private?
- [ ] Usando List<> (não ArrayList<>) como tipo?
- [ ] Diamond operator `<>` presente?
- [ ] @Override nos métodos sobrescritos?
- [ ] Tipo de retorno compatível com o que retorna?
- [ ] Métodos que são chamados de fora são public?
- [ ] Tenho getters para atributos private que precisam ser acessados?
- [ ] Implementei na ordem correta (base → dependente → app)?

### Streams e Lambda

- [ ] Stream criada a partir de Collection?
- [ ] Operações intermediárias retornam stream (encadeáveis)?
- [ ] Operação terminal finaliza a stream?
- [ ] orElseThrow com lambda `() -> new IllegalArgumentException(...)`?
- [ ] Não reutilizando stream já consumida?

### Exceções

- [ ] Try com pelo menos um catch?
- [ ] Catch não vazio?
- [ ] Validações no construtor com IllegalArgumentException?
- [ ] Estados inválidos com IllegalStateException?

---

## PARTE 10: ERROS QUE VOCÊ COMETEU (e como evitar)

| Erro | Como evitar |
|------|-------------|
| Interface chamada "Motor" em vez de "IPropulsao" | Sempre prefixo "I" em interfaces |
| Roteiro com destino + distância | Ler o enunciado com calma - o que ele pede? |
| App só com Veiculos | Perguntar: "o que GUARDA o quê?" → App guarda automoveis E roteiros |
| Retorno tipo errado (Veiculo em vez de int) | Verificar: o que o método retorna? tipo do retorno = tipo real |
| addLast() em ArrayList | ArrayList usa add(). addLast() é de LinkedList |
| Diamond operator ausente | SEMPRE escrever `new ArrayList<>()` |
| orElseThrow sem lambda | Precisa de `() -> new Exception("msg")` |
| Não implementar na ordem | Primeiro: classes base → depois: dependentes → por último: App |

---

## PARTE 11: MENTALIDADE PARA A PROVA

1. **Não entre em pânico** - primeiro entenda o problema
2. **Não viaje** - não invente coisas que não foram pedidas
3. **Não interessa quem fez** - interessa o QUE foi feito
4. **Implemente de baixo para cima** - primeiro as classes base, depois as que dependem delas
5. **Valide tudo** - construtor com validação, métodos com verificação
6. **Use polimorfismo** - variável do tipo interface, instância da implementação
7. **Se não sabe o padrão** - pense: "eu sou" = herança, "eu faço" = interface

---

**Boa prova! Você consegue.**
