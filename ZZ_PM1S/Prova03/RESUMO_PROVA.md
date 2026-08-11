# RESUMO PROVA - PROGRAMAÇÃO MODULAR (3ª Prova)

**Professor:** João Caram (caram@pucminas.br)
**Base de Código:** XulambsPizza/XulambsFoods (3 versões)

---

## REGRAS INFORMAIS DO PROFESSOR

1. **NÃO ENTRE EM PÂNICO** - primeiro é entender o problema
2. **NÃO VIAJE** - não invente coisas que não foram pedidas
3. **NÃO INTERESSA QUEM FEZ/COMO FAZER** - interessa o QUE foi feito

---

## 1. FUNDAMENTOS JAVA

### Entrada de Dados
```java
String texto = IO.readln(); // SEMPRE retorna String
int idade = Integer.parseInt(texto);    // String → int
double nota = Double.parseDouble(texto); // String → double
```

### Métodos de String
| Método | O que faz |
|--------|-----------|
| `charAt(posicao)` | Retorna caractere na posição |
| `indexOf(busca)` | Retorna índice da primeira ocorrência (-1 se não encontrar) |
| `length()` | Quantidade de caracteres |
| `replace(original, novo)` | Substitui todas as ocorrências |
| `split(separador)` | Divide string em array |
| `substring(inicio, final)` | Pedaço da string |

```java
String linha = "João,25,BH";
String[] partes = linha.split(","); // ["João", "25", "BH"]
```

---

## 2. PRINCÍPIOS DA POO

### O que é uma Classe?
- **Atributos** = dados (estado)
- **Métodos** = operações (comportamento)
- Classe tem que **responder coisas** e ter os **dados para responder**

### POO segundo Alan Kay
> "As classes vão fazer uma troca de mensagens."

Objetos se comunicam via **chamadas de métodos**, não acessam dados uns dos outros diretamente.

### Construtor
- Chamado no momento da criação do objeto
- **DEVE validar dados** antes de atribuir
- Sem validação = "não serve para nada" (objeto pode ficar em estado inválido)

```java
public Aluno(String nome, int idade) {
    if (nome == null || nome.isBlank()) 
        throw new IllegalArgumentException("Nome inválido");
    if (idade < 0) 
        throw new IllegalArgumentException("Idade inválida");
    this.nome = nome;
    this.idade = idade;
}
```

---

## 3. PRINCÍPIOS SOLID

### S - Single Responsibility (Responsabilidade Única)
> "Uma classe deve ter apenas um motivo para mudar."

**Teste:** Se na resposta "O que essa classe faz?" tem "e" ou "ou", provavelmente faz demais.

```java
// ❌ ERRADO - múltiplas responsabilidades
class Aluno {
    void calcularMedia() { ... }
    void imprimirNaTela() { ... } // problema!
}

// ✅ CORRETO
class Aluno {
    void calcularMedia() { ... }
}
class ExibidorDeAluno {
    void imprimirNaTela(Aluno a) { ... }
}
```

### O - Open/Closed (Aberto/Fechado)
> "Aberta para extensão, fechada para modificação."

Novo comportamento = **nova classe**, não modificar código existente.

```java
// ❌ ERRADO - toda vez que surge novo tipo, adiciona else if
class Notificador {
    void notificar(String tipo, String msg) {
        if (tipo.equals("email")) enviarEmail(msg);
        else if (tipo.equals("sms")) enviarSMS(msg);
        // PERIGOSO - cresce infinitamente
    }
}

// ✅ CORRETO - usa herança + polimorfismo
abstract class Notificador {
    abstract void notificar(String msg);
}
class NotificadorEmail extends Notificador { ... }
class NotificadorSMS extends Notificador { ... }
```

### L - Liskov Substitution (Substituição de Liskov)
> "Subtipos devem ser substituíveis por seus supertipos sem quebrar o programa."

```java
// ❌ VIOLAÇÃO - Quadrado quebra contrato do Retangulo
class Retangulo {
    void setLargura(int l) { this.largura = l; }
    void setAltura(int a) { this.altura = a; }
}
class Quadrado extends Retangulo {
    void setLargura(int l) {
        this.largura = l;
        this.altura = l; // força os dois iguais - QUEBRA!
    }
}
```

### I - Interface Segregation (Segregação de Interface)
> "Classes devem ser separadas e especializadas."

**Regra USAR vs FAZER:**
- "Eu **SOU** esse algo?" → Herança
- "Eu **FAÇO** esse algo?" → Composição (usa)

### D - Dependency Inversion (Inversão de Dependência)
> "Módulos de alto nível não devem depender de baixo nível. Ambos dependem de abstrações."

```java
// ❌ ERRADO - acoplamento forte
class Relatorio {
    private BancoDados bancoDados;
    public Relatorio() {
        this.bancoDados = new BancoDados(); // dependência concreta
    }
}

// ✅ CORRETO - depende de abstração
interface BaseDados {
    List<Dados> obter();
}
class Relatorio {
    private BaseDados baseDados;
    public Relatorio(BaseDados baseDados) {
        this.baseDados = baseDados; // depende da interface
    }
}
```

---

## 4. ENCAPSULAMENTO

### Modificadores de Acesso
| Modificador | Visível na filha? | Fora do pacote? |
|-------------|-------------------|-----------------|
| `public` | ✅ | ✅ |
| `protected` | ✅ | ❌ |
| `private` | ❌ | ❌ |

### Regras Importantes
- **Classes NUNCA imprimem nada** (System.out.println) - quem imprime é o App.java
- **`static`** = pertence à classe, não ao objeto
- **`final`** = imutável após atribuição
- **Constante** = `static final` + nome em MAIÚSCULAS

```java
class Pizza {
    static int totalCriadas = 0;           // compartilhado
    static final double TAXA = 0.1;        // constante
}
```

---

## 5. LISTAS - ArrayList vs LinkedList

### ArrayList
- Array dinâmico interno
- **Melhor para:** acesso por índice O(1), tamanho previsível
- **Evitar:** inserções/remoções frequentes O(n)

### LinkedList
- Nós encadeados (duplamente encadeada)
- **Melhor para:** inserções/remoções frequentes O(1)
- **Evitar:** acesso por índice O(n)

### Regra de Ouro
> Na dúvida, use **ArrayList**. É a escolha padrão em 90% dos casos!

### Uso Correto
```java
// ✅ CORRETO - usar interface List
List<String> nomes = new ArrayList<>();

// ❌ EVITE - usar classe concreta
ArrayList<String> nomes = new ArrayList<>();
```

### Iteração
```java
// For-Each (sem índice)
for (String item : lista) {
    System.out.println(item);
}

// For tradicional (com índice)
for (int i = 0; i < lista.size(); i++) {
    System.out.println(lista.get(i));
}
```

### Cuidado
```java
// ❌ ERRO - ConcurrentModificationException
for (String item : lista) {
    lista.remove(item);
}

// ✅ CORRETO
lista.removeIf(item -> item.equals("remover"));
```

---

## 6. RELACIONAMENTOS ENTRE CLASSES (UML)

| Tipo | Símbolo | Palavra-chave | Quando usar |
|------|---------|---------------|-------------|
| **Contenção** | Losango cheio ◆ | "Contém" | Elemento não existe sem o conjunto (Pedido contém Pizza) |
| **Agregação** | Losango vazio ◇ | "Tem" | Elemento pode existir independentemente (Turma tem Aluno) |
| **Associação** | Seta simples → | "Usa" | Usa sem posse (Pedido usa Cliente para consulta) |

**O losango fica do lado de quem controla/possui.**

---

## 7. CARTÃO CRC (Class-Responsibility-Collaboration)

| Responsabilidade | Colaboração |
|-------------------|-------------|
| O que a classe FAZ | Com quem INTERAGE |

**Como fazer:**
1. Marcar **SUBSTANTIVOS** → provavelmente são classes
2. Marcar **VERBOS** → provavelmente são responsabilidades
3. Pergunta-chave: "Consigo fazer isso **EXCLUSIVAMENTE** com minhas variáveis primitivas?"
   - **SIM** → colaboração vazia
   - **NÃO** → preencher colaboração

---

## 8. ENUMERADORES (enum)

Tipo especial com **conjunto fixo de valores nomeados**.

```java
// Definição
public enum TipoPizza {
    MARGHERITA(25.0), 
    CALABRESA(28.0), 
    PORTUGUESA(30.0);

    private final double preco;

    TipoPizza(double preco) { 
        this.preco = preco; 
    }

    public double getPreco() { 
        return preco; 
    }
}

// Uso
TipoPizza tipo = TipoPizza.CALABRESA;
```

**Vantagens:**
- Autovalidado - compilador rejeita valores fora do conjunto
- Legível - sem números mágicos

**Notação UML:** `<<enum>>` acima do nome, prefixo **E** maiúsculo.

---

## 9. HERANÇA

### Conceito
- **Classe mãe** (genérica) → **Classe filha** (especialista)
- Filha herda atributos e métodos públicos/protetados da mãe

### Construtor da Filha - `super()`
- **OBRIGATÓRIO** chamar `super()` como **PRIMEIRA LINHA**
- Executa a lógica do construtor da mãe (validação)

```java
class Animal {
    String nome;
    public Animal(String nome) {
        if (nome == null || nome.isBlank()) 
            throw new IllegalArgumentException("Nome inválido");
        this.nome = nome;
    }
}

class Cachorro extends Animal {
    String raca;
    public Cachorro(String nome, String raca) {
        super(nome);   // PRIMEIRA LINHA - valida e inicializa
        this.raca = raca;
    }
}
```

### Override
- Mesma assinatura (nome, parâmetros, tipo retorno)
- Usar `@Override` (boa prática - compilador avisa se errar)
- Usar quando a regra de negócio é **diferente** para a filha

```java
class Animal {
    void fazerSom() { System.out.println("..."); }
}
class Cachorro extends Animal {
    @Override
    void fazerSom() { System.out.println("Au!"); }
}
```

---

## 10. POLIMORFISMO

> Capacidade de objeto ser referenciado de várias formas.

```java
Animal a1 = new Cachorro("Rex", "Labrador");
Animal a2 = new Gato("Mimi");

a1.fazerSom();  // "Au!" - dispatch dinâmico
a2.fazerSom();  // "Miau!"
```

**Regra:** Declarar variável pelo tipo da **mãe**, instanciar com a **filha**.

**NUNCA usar `getClass()`** para comparar tipos - usar `instanceof` ou polimorfismo.

---

## 11. CLASSE OBJECT

Toda classe herda de `Object` automaticamente.

### `equals(Object obj)`
- Padrão: compara **referências de memória**
- Sobrescrever para comparar **conteúdo**

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Cachorro)) return false;
    Cachorro outro = (Cachorro) obj;
    return this.nome.equals(outro.nome) && this.raca.equals(outro.raca);
}
```

### `hashCode()`
- Impressão digital numérica
- Se `equals` = true, `hashCode` **DEVE** ser igual

```java
@Override
public int hashCode() {
    return Objects.hash(nome, raca);
}
```

### `toString()`
- Representação textual do objeto
- Sem override: "Classe@endereço" (inútil)

```java
@Override
public String toString() {
    return "Cachorro{nome='" + nome + "', raca='" + raca + "'}";
}
```

---

## 12. CLASSES E MÉTODOS ABSTRATOS

### Classe Abstrata
- Representa uma **ideia/contrato**, não pode ser instanciada
- Pode ter métodos **concretos** e **abstratos**

```java
public abstract class Forma {
    public abstract double area();     // sem implementação
    public abstract double perimetro();

    public void exibir() {             // método concreto
        System.out.println("Área: " + area());
    }
}
```

### Regras
- Qualquer método abstrato → classe deve ser `abstract`
- Subclasse **OBRIGADA** a implementar todos os métodos abstratos
- `new Forma()` causa erro de compilação

---

## 13. FINAL

| Uso | Efeito |
|-----|--------|
| `método final` | Não pode ser sobrescrito |
| `classe final` | Não pode ser herdada (String é final) |
| `variável final` | Constante - não pode ser reatribuída |

---

## 14. PROGRAMAÇÃO DEFENSIVA

### Programação por Contrato
- **Pré-condições:** o que o método exige
- **Pós-condições:** o que o método garante
- **Invariantes:** condições sempre verdadeiras

### Exceções
```java
try {
    abrirArquivo("dados.txt");
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado: " + e.getMessage());
} catch (IOException e) {
    System.out.println("Erro de leitura: " + e.getMessage());
} finally {
    fecharArquivo(); // sempre executa
}
```

### Hierarquia
```
Throwable
  ├── Error          → erros graves da JVM (não tratar)
  └── Exception
        ├── RuntimeException    → erros de programação (unchecked)
        └── Checked Exceptions  → obrigatório tratar
```

**NUNCA catch vazio** - erro desaparece sem aviso!

---

## 15. TESTES JUnit

### Padrão AAA
| Etapa | O que faz |
|-------|-----------|
| **Arrange** | Cria objetos e define dados |
| **Act** | Executa a ação testada |
| **Assert** | Confere se resultado é o esperado |

```java
@Test
void deveCalcularValorTotalCorretamente() {
    // Arrange
    Pedido p = new Pedido();
    p.adicionar(new Pizza(2));
    
    // Act
    double total = p.precoAPagar();
    
    // Assert
    assertEquals(39.0, total, 0.01);
}
```

### Regras
- Todo método deve ser testado
- Nome descritivo (deixar claro o que testa)
- `assertEquals` com `double` **OBRIGATÓRIO** terceiro parâmetro (delta)

---

## 16. INTERFACES

### Conceito
Define um **contrato** que a classe deve cumprir.

```java
interface MetodoPagamento {
    void processar(double valor);
    boolean validar();
}

class PagamentoCartao implements MetodoPagamento {
    @Override
    public void processar(double valor) { ... }
    
    @Override
    public boolean validar() { ... }
}
```

### Regra
> "Programe para a interface, não para a implementação."

### Interface Funcional
- Apenas **um método abstrato**
- Permite uso de **Lambda**

---

## 17. POLIMORFISMO: HERANÇA vs INTERFACE

### Herança
- Usado para **regras naturalmente diferentes**
- Diferenças **inerentes** à natureza
- Ex: Animal → Cachorro, Gato, Pássaro

### Interface
- Usado para **comportamento que muda**
- Diferenças **situacionais**
- Ex: Pedido pode ser preparado, entregue, cancelado

---

## 18. GENÉRICOS

Passar **classe como parâmetro**:

```java
public class Caixa<T> {
    private T objeto;
    
    public void guardar(T obj) {
        this.objeto = obj;
    }
    
    public T retirar() {
        return objeto;
    }
}

// Uso
Caixa<String> caixaTexto = new Caixa<>();
Caixa<Integer> caixaNumero = new Caixa<>();
```

**Diamond Operator:** `<>` - compilador infere o tipo.

---

## 19. JAVA COLLECTIONS FRAMEWORK

### Coleção
Tudo que consegue usar `foreach`.

### Mapas
- Pares **chave-valor**
- **HashMap:** rápido O(1), ordem aleatória
- **TreeMap:** ordenado O(log n)

```java
Map<String, Integer> mapa = new HashMap<>();
mapa.put("Ana", 25);
int idade = mapa.get("Ana");
```

---

## 20. LAMBDA E INTERFACES FUNCIONAIS

### Lambda
```java
// Sintaxe
(parâmetros) -> { corpo }

// Exemplos
x -> x * 2
(a, b) -> a + b
```

### Interfaces Principais
| Interface | Recebe | Retorna | Uso |
|-----------|--------|---------|-----|
| **Comparator** | 2 valores | int | Ordenação |
| **Consumer** | 1 valor | void | Ação |
| **Function** | 1 valor | 1 valor | Transformação |
| **Predicate** | 1 valor | boolean | Verificação |

### Method Reference
```java
// Lambda
nomes.forEach(nome -> System.out.println(nome));

// Method Reference
nomes.forEach(System.out::println);
```

---

## 21. STREAMS

### Conceito
Sequência de elementos com operações de agregação.

### Operações Intermediárias (retornam stream)
| Operação | O que faz |
|----------|-----------|
| `filter(predicate)` | Mantém elementos que atendem condição |
| `map(function)` | Transforma cada elemento |
| `distinct()` | Remove duplicatas |
| `sorted(comparator)` | Ordena |

### Operações Terminais (retornam resultado)
| Operação | O que faz |
|----------|-----------|
| `toList()` | Converte para lista |
| `count()` | Conta elementos |
| `forEach(consumer)` | Executa ação para cada |
| `max(comparator)` | Encontra máximo |
| `reduce(biFunction)` | Reduz para único valor |

```java
List<Integer> pares = numeros.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .toList();
```

---

## 22. OPTIONAL

> Representa valor que pode existir ou não (alternativa segura a null).

```java
// ❌ Ruim - retorna null
public Cliente buscarCliente(int id) { ... return null; }

// ✅ Bom - retorna Optional
public Optional<Cliente> buscarCliente(int id) {
    if (encontrado) return Optional.of(cliente);
    return Optional.empty();
}
```

### Operações
| Operação | O que faz |
|----------|-----------|
| `ifPresent(consumer)` | Executa ação se existe |
| `ifPresentOrElse(c, r)` | Executa ação se existe, outra se não |
| `orElse(valor)` | Retorna valor ou padrão |
| `orElseThrow()` | Retorna valor ou lança exceção |

---

## 23. CÓDIGO XULAMBS - EVOLUÇÃO

### Versão 1: XulambsPizza (Antiga)
- `Pedido` usa `List<IProduto>` (LinkedList)
- `Pizza` implementa `IProduto` e `IPersonalizavel`
- Pedidos: `PedidoLocal` e `PedidoEntrega`
- Fidelidade: `IFidelidade` com `XulambsJunior`, `XulambsPleno`, `XulambsSenior`

### Versão 2: XulambsPizza (atual - v.09)
- Mesma estrutura da versão 1
- Adiciona `ComparadorCliente`, `Dados`, `GeradorClientes`, `GeradorPedidos`, `GeradorProdutos`

### Versão 3: XulambsFoods (Refatoração)
- `Pedido` usa `IComida[]` (array) em vez de List
- Interface `IComida` com `valorFinal()`, `notaDeCompra()`, `adicionarIngredientes()`, `valorAdicionais()`
- `Comida` é uma classe wrapper que delega para `Pizza` ou `Sanduiche`
- `EComida` é um enum com tipos de comida
- `EEstadoPedido` é um enum com estados do pedido
- `EDistancia` é um enum com faixas de distância

### Padrões Observados
- **Herança:** Pedido → PedidoLocal, PedidoEntrega
- **Interface:** IProduto, IComida, IFidelidade, IPersonalizavel
- **Enum:** EBorda, ESobremesa, EBebida, EComida, EEstadoPedido, EDistancia
- **Exceção customizada:** PedidoFechadoException, DistanciaInvalidaException
- **Métodos estáticos:** para cálculos auxiliares
- **Validação:** sempre no construtor, lança IllegalArgumentException

---

## 24. CHECKLIST ANTES DE ENTREGAR

- [ ] Construtores com validação completa?
- [ ] Atributos private?
- [ ] Usando interface List (não ArrayList/LinkedList diretamente)?
- [ ] Override com anotação @Override?
- [ ] Construtor filha chama super() como primeira linha?
- [ ] hashCode e equals sobrescritos quando necessário?
- [ ] toString sobrescrito?
- [ ] Testes com padrão AAA?
- [ ] assertEquals com delta para double?
- [ ] Catch não está vazio?
- [ ] Usando enum para estados fixos?
- [ ] Classes não imprimem nada (sem System.out.println)?
- [ ] Constantes em MAIÚSCULAS com static final?

---

## ERROS MAIS COMUNS NA PROVA

1. **Esquecer `super()`** no construtor da classe filha
2. **Não sobrescrever `hashCode()`** quando sobrescreve `equals()`
3. **Usar `getClass()`** para comparar tipos (usar `instanceof`)
4. **Criar classe abstrata** com `new` (não pode instanciar)
5. **Catch vazio** - erro desaparece
6. **ArrayList como tipo** em vez de List
7. **Esquecer delta** no assertEquals com double
8. **Imprimir dentro da classe** (System.out.println)
9. **Não validar** dados no construtor
10. **Confundir Contenção com Agregação** (losango cheio vs vazio)
