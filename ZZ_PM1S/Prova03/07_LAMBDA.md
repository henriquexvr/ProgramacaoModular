# LAMBDA E INTERFACES FUNCIONAIS - Guia Completo

## O QUE É UMA LAMBDA?

Uma Lambda é uma **função anônima** (sem nome). É uma forma de escrever código que pode ser passado como parâmetro.

### Analogia

Imagine que você pede um pedido de delivery:
- **Sem Lambda:** Você pede "quero uma pizza" (não define o que tem nela)
- **Com Lambda:** Você pede "quero uma pizza COM **calabresa e mussarela**" (você define a receita)

Lambda é como definir a "receita" de uma função na hora que você precisa dela.

---

## POR QUE EXISTE LAMBDA?

### Sem Lambda - Código Verborrágico

```java
// Para ordenar uma lista, você precisa criar uma classe separada
Comparator<Pessoa> comparador = new Comparator<Pessoa>() {
    @Override
    public int compare(Pessoa p1, Pessoa p2) {
        return Integer.compare(p1.idade, p2.idade);
    }
};

pessoas.sort(comparador);
```

### Com Lambda - Código Limpo

```java
// Mesma coisa, mas muito mais curto
pessoas.sort((p1, p2) -> Integer.compare(p1.idade, p2.idade));
```

**Vantagens:**
1. **Menos código** - não precisa criar classe separada
2. **Mais legível** - foca no que importa (a lógica)
3. **Mais flexível** - pode passar comportamento como parâmetro

---

## COMO FUNCIONA

### Sintaxe Básica

```
(parâmetros) -> { corpo da função }
```

### Exemplos

```java
// Um parâmetro
x -> x * 2              // dobra um número
(x) -> x * 2           // parênteses opcionais com 1 parâmetro

// Dois parâmetros
(a, b) -> a + b        // soma dois números

// Múltiplas linhas
(texto) -> {
    System.out.println(texto);
    return texto.length();
}

// Sem parâmetros
() -> System.out.println("Olá!")
```

### Onde Usar Lambda?

Lambda só funciona com **interfaces funcionais** (interfaces com 1 método abstrato).

```java
// Interface funcional - PODE usar Lambda
interface Comparador {
    int comparar(String a, String b);
}

Comparador c = (a, b) -> a.compareTo(b);  // ✅ funciona

// Interface com 2 métodos - NÃO pode usar Lambda
interface DuasCoisas {
    void metodo1();
    void metodo2();
}

DuasCoisas d = () -> System.out.println("Oi");  // ❌ ERRO!
```

---

## INTERFACES FUNCIONAIS PRINCIPAIS

Java tem interfaces funcionais prontas no pacote `java.util.function`.

### 1. Comparator<T> - Comparar Dois Valores

**O que faz:** Compara dois valores para decidir quem vem primeiro.

```java
// Sintaxe: Comparator<T>
// Método: int compare(T a, T b)
// Retorna: negativo (a primeiro), 0 (iguais), positivo (b primeiro)

// Exemplo: ordenar por idade
Comparator<Pessoa> porIdade = (p1, p2) -> Integer.compare(p1.idade, p2.idade);
pessoas.sort(porIdade);

// Exemplo: ordenar por nome
Comparator<Pessoa> porNome = (p1, p2) -> p1.nome.compareTo(p2.nome);
pessoas.sort(porNome);

// Exemplo: ordenar do maior para o menor (reverter)
Comparator<Pessoa> porIdadeDecrescente = (p1, p2) -> Integer.compare(p2.idade, p1.idade);
pessoas.sort(porIdadeDecrescente);
```

### 2. Consumer<T> - Consumir um Valor

**O que faz:** Recebe um valor e **não retorna nada** (faz uma ação).

```java
// Sintaxe: Consumer<T>
// Método: void accept(T t)
// Retorna: nada (void)

// Exemplo: imprimir
Consumer<String> imprimir = texto -> System.out.println(texto);
imprimir.accept("Olá!");  // imprime: Olá!

// Exemplo: enviar email
Consumer<Cliente> enviarEmail = cliente -> {
    String email = cliente.getEmail();
    String mensagem = "Olá, " + cliente.getNome();
    EmailService.enviar(email, mensagem);
};
enviarEmail.accept(cliente);
```

### 3. Function<T, R> - Transformar um Valor

**O que faz:** Recebe um valor e **retorna outro valor** (transforma).

```java
// Sintaxe: Function<T, R>
// Método: R apply(T t)
// T = tipo de entrada, R = tipo de saída

// Exemplo: converter para texto
Function<Integer, String> paraTexto = numero -> "Número: " + numero;
String resultado = paraTexto.apply(42);  // "Número: 42"

// Exemplo: extrair nome
Function<Pessoa, String> extrairNome = pessoa -> pessoa.nome;
String nome = extrairNome.apply(new Pessoa("Ana", 25));  // "Ana"

// Exemplo: calcular preço
Function<Pizza, Double> calcularPreco = pizza -> pizza.valorAPagar();
double preco = calcularPreco.apply(new Pizza(3));  // 44.0
```

### 4. Predicate<T> - Verificar uma Condição

**O que faz:** Recebe um valor e **retorna boolean** (verifica uma condição).

```java
// Sintaxe: Predicate<T>
// Método: boolean test(T t)
// Retorna: true ou false

// Exemplo: verificar se é par
Predicate<Integer> ehPar = numero -> numero % 2 == 0;
boolean resultado = ehPar.test(4);  // true
boolean resultado2 = ehPar.test(5);  // false

// Exemplo: verificar se é maior de idade
Predicate<Pessoa> maiorDeIdade = pessoa -> pessoa.idade >= 18;
boolean podeDirigir = maiorDeIdade.test(new Pessoa("Ana", 25));  // true

// Exemplo: verificar se nome não está vazio
Predicate<String> naoVazio = texto -> texto != null && !texto.isEmpty();
boolean valido = naoVazio.test("Ana");  // true
boolean invalido = naoVazio.test("");    // false
```

### 5. Supplier<T> - Fornecer um Valor

**O que faz:** Não recebe nada e **retorna um valor** (cria algo).

```java
// Sintaxe: Supplier<T>
// Método: T get()
// Retorna: um valor do tipo T

// Exemplo: criar pizza
Supplier<Pizza> criarPizza = () -> new Pizza();
Pizza pizza = criarPizza.get();  // cria uma pizza nova

// Exemplo: data atual
Supplier<LocalDate> dataAtual = () -> LocalDate.now();
LocalDate hoje = dataAtual.get();
```

### Tabela Resumo

| Interface | Recebe | Retorna | Método | Uso |
|-----------|--------|---------|--------|-----|
| `Comparator<T>` | 2 valores | int | `compare(a, b)` | Ordenação |
| `Consumer<T>` | 1 valor | void | `accept(t)` | Ação |
| `Function<T,R>` | 1 valor | R | `apply(t)` | Transformação |
| `Predicate<T>` | 1 valor | boolean | `test(t)` | Verificação |
| `Supplier<T>` | nada | T | `get()` | Criação |

---

## METHOD REFERENCE (Referência de Método)

Method Reference é uma forma **ainda mais simplificada** de escrever Lambda quando você só quer chamar um método que já existe.

### Sintaxe

```
NomeDaClasse::nomeDoMetodo
```

### Exemplos Comparativos

```java
// Lambda
nomes.forEach(nome -> System.out.println(nome));

// Method Reference
nomes.forEach(System.out::println);
```

```java
// Lambda
pessoas.sort((p1, p2) -> p1.compareTo(p2));

// Method Reference
pessoas.sort(String::compareTo);
```

```java
// Lambda
numeros.stream().map(n -> Math.sqrt(n));

// Method Reference
numeros.stream().map(Math::sqrt);
```

### Quando Usar?

Use Method Reference quando:
- A Lambda só chama um método existente
- Não precisa de lógica adicional

```java
// ✅ Method Reference - só chama toUpperCase
nomes.stream().map(String::toUpperCase)

// ❌ Lambda - precisa de lógica adicional
nomes.stream().map(nome -> "Sr. " + nome)
```

---

## LAMBDA NO CÓDIGO XULAMBS

### Exemplo 1: Ordenar Pedidos

```java
// Comparator com Lambda
List<Pedido> pedidos = new LinkedList<>();
pedidos.sort((p1, p2) -> Double.compare(p1.precoAPagar(), p2.precoAPagar()));
```

### Exemplo 2: Filtrar Pedidos

```java
// Predicate com Lambda
pedidos.stream()
    .filter(p -> p.precoAPagar() > 50)  // só pedidos acima de R$50
    .toList();
```

### Exemplo 3: Extrair Informações

```java
// Function com Lambda
List<String> descricoes = pedidos.stream()
    .map(p -> p.toString())  // extrai a descrição de cada pedido
    .toList();
```

---

## COMBINANDO LAMBDAS

### Encadeamento

```java
// Filtrar + Transformar + Coletar
List<String> resultado = nomes.stream()
    .filter(n -> n.length() > 3)           // Predicate
    .map(String::toUpperCase)              // Function
    .forEach(n -> System.out.println(n));  // Consumer
```

### Composição

```java
// Compor Predicate
Predicate<Integer> ehPar = n -> n % 2 == 0;
Predicate<Integer> ehPositivo = n -> n > 0;

Predicate<Integer> parEPositivo = ehPar.and(ehPositivo);
boolean resultado = parEPositivo.test(4);  // true

Predicate<Integer> parOuPositivo = ehPar.or(ehPositivo);
boolean resultado2 = parOuPositivo.test(3);  // true

Predicate<Integer> naoPar = ehPar.negate();
boolean resultado3 = naoPar.test(3);  // true
```

---

## ERROS COMUNS

### 1. Usar Lambda com interface que não é funcional

```java
interface DuasCoisas {
    void metodo1();
    void metodo2();
}

// ❌ ERRO - interface não é funcional
DuasCoisas d = () -> System.out.println("Oi");
```

### 2. Esquecer o tipo do parâmetro

```java
// ✅ CORRETO - Java infere o tipo
pessoas.sort((p1, p2) -> p1.nome.compareTo(p2.nome));

// ❌ ERRADO - tipo explícito (às vezes necessário, mas geralmente não)
pessoas.sort((Pessoa p1, Pessoa p2) -> p1.nome.compareTo(p2.nome));
```

### 3. Usar Lambda quando Method Reference é melhor

```java
// ❌ Lambda desnecessário
nomes.stream().map(nome -> nome.toUpperCase())

// ✅ Method Reference
nomes.stream().map(String::toUpperCase)
```

---

## RESUMO RÁPIDO

| Conceito | Significado |
|----------|-------------|
| Lambda | Função anônima `(params) -> corpo` |
| Interface funcional | Interface com 1 método abstrato |
| Method Reference | `Classe::metodo` - atalho para Lambda |
| `Comparator` | Compara dois valores |
| `Consumer` | Recebe valor, não retorna |
| `Function` | Recebe valor, retorna outro |
| `Predicate` | Recebe valor, retorna boolean |
| `Supplier` | Não recebe, retorna valor |

### Regra de Ouro

> Use Lambda quando precisar passar **comportamento** como parâmetro.

---

## CHECKLIST LAMBDA

- [ ] Interface é funcional (1 método abstrato)?
- [ ] Sintaxe correta: `(params) -> corpo`?
- [ ] Method Reference usado quando Lambda só chama método?
- [ ] Tipos inferidos corretamente (não precisa explícito)?
- [ ] Lambda com múltiplas linhas usa chaves `{}`?
