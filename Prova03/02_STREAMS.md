# STREAMS - Guia Completo

## O QUE É UMA STREAM?

Uma Stream é um **tubo por onde os dados passam**. Em cada etapa do tubo, você pode **filtrar, transformar ou agregar** os dados.

Pense numa linha de produção de fábrica:
```
Matéria-prima → [Corte] → [Costura] → [Embalagem] → Produto final
```

Stream faz a mesma coisa, mas com dados:
```
Dados brutos → [Filtrar] → [Transformar] → [Coletar] → Resultado
```

---

## POR QUE USAR STREAM?

### Antes (com loop - forma imperativa)

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
List<Integer> paresDobrados = new ArrayList<>();

for (Integer n : numeros) {
    if (n % 2 == 0) {           // filtrar pares
        paresDobrados.add(n * 2); // dobrar
    }
}
// Resultado: [4, 8, 12, 16]
```

### Depois (com Stream - forma declarativa)

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

List<Integer> paresDobrados = numeros.stream()
    .filter(n -> n % 2 == 0)    // filtrar pares
    .map(n -> n * 2)            // dobrar
    .toList();                   // coletar
// Resultado: [4, 8, 12, 16]
```

**Vantagens:**
1. **Legibilidade** - lê de cima para baixo como uma receita
2. **Menos código** - não precisa criar lista auxiliar
3. **Encadeamento** - cada operação retorna outra stream

---

## COMO CRIAR UMA STREAM

```java
// A partir de uma Collection
List<String> nomes = Arrays.asList("Ana", "Bruno", "Carlos");
Stream<String> stream = nomes.stream();

// A partir de um Array
int[] numeros = {1, 2, 3, 4, 5};
IntStream stream = Arrays.stream(numeros);

// Valores avulsos
Stream<String> stream = Stream.of("A", "B", "C");
```

---

## OPERAÇÕES INTERMEDIÁrias

Retornam **outra stream** (podem ser encadeadas).

### 1. filter(predicate) - Filtrar

Mantém apenas elementos que atendem uma condição (retorna `true`).

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);

List<Integer> pares = numeros.stream()
    .filter(n -> n % 2 == 0)  // mantém só pares
    .toList();
// Resultado: [2, 4, 6]
```

**Analogia:** É um peneira. Só deixa passar o que interessa.

### 2. map(function) - Transformar

Transforma cada elemento em algo diferente.

```java
List<String> nomes = Arrays.asList("ana", "bruno", "carlos");

List<String> maiusculas = nomes.stream()
    .map(String::toUpperCase)  // transforma para maiúscula
    .toList();
// Resultado: ["ANA", "BRUNO", "CARLOS"]
```

**Analogia:** É uma máquina que transforma. Cada item entra e sai diferente.

### 3. distinct() - Remover Duplicatas

```java
List<Integer> numeros = Arrays.asList(1, 2, 2, 3, 3, 3);

List<Integer> unicos = numeros.stream()
    .distinct()
    .toList();
// Resultado: [1, 2, 3]
```

### 4. sorted(comparator) - Ordenar

```java
List<Integer> numeros = Arrays.asList(3, 1, 4, 1, 5, 9, 2);

// Ordenação natural
List<Integer> ordenados = numeros.stream()
    .sorted()
    .toList();
// Resultado: [1, 1, 2, 3, 4, 5, 9]

// Ordenação customizada
List<String> nomes = Arrays.asList("Bruno", "Ana", "Carlos");
List<String> ordenados = nomes.stream()
    .sorted((a, b) -> a.compareTo(b))  // ordem alfabética
    .toList();
// Resultado: ["Ana", "Bruno", "Carlos"]
```

### 5. peek(consumer) - Olhar (para debug)

Executa uma ação em cada elemento **sem alterar a stream**. Útil para ver o que está passando.

```java
List<Integer> numeros = Arrays.asList(1, 2, 3);

List<Integer> resultado = numeros.stream()
    .peek(n -> System.out.println("Antes: " + n))  // mostra: 1, 2, 3
    .map(n -> n * 2)
    .peek(n -> System.out.println("Depois: " + n)) // mostra: 2, 4, 6
    .toList();
// Resultado: [2, 4, 6]
```

---

## OPERAÇÕES TERMINAIS

Finalizam a stream e retornam um **resultado concreto** (não stream).

### 1. toList() - Coletar como Lista

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> maioresQue3 = numeros.stream()
    .filter(n -> n > 3)
    .toList();
// Resultado: [4, 5]
```

### 2. count() - Contar

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

long quantidade = numeros.stream()
    .filter(n -> n > 3)
    .count();
// Resultado: 2
```

### 3. forEach(consumer) - Executar Ação

```java
List<String> nomes = Arrays.asList("Ana", "Bruno", "Carlos");

nomes.stream()
    .forEach(n -> System.out.println("Olá, " + n));
// Imprime:
// Olá, Ana
// Olá, Bruno
// Olá, Carlos
```

### 4. reduce(biFunction) - Reduzir a Um Valor

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

int soma = numeros.stream()
    .reduce(0, Integer::sum);
// Resultado: 15 (0 + 1 + 2 + 3 + 4 + 5)
```

### 5. min(comparator) e max(comparator)

```java
List<Integer> numeros = Arrays.asList(3, 1, 4, 1, 5, 9);

Optional<Integer> minimo = numeros.stream()
    .min(Integer::compare);
// Resultado: Optional[1]

Optional<Integer> maximo = numeros.stream()
    .max(Integer::compare);
// Resultado: Optional[9]
```

### 6. sum() e average()

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

int soma = numeros.stream()
    .mapToInt(Integer::intValue)
    .sum();
// Resultado: 15

OptionalDouble media = numeros.stream()
    .mapToInt(Integer::intValue)
    .average();
// Resultado: OptionalDouble[3.0]
```

### 7. anyMatch, allMatch, noneMatch

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

boolean temPar = numeros.stream()
    .anyMatch(n -> n % 2 == 0);
// Resultado: true (tem pelo menos um par)

boolean todosMaioresQueZero = numeros.stream()
    .allMatch(n -> n > 0);
// Resultado: true (todos são maiores que 0)

boolean nenhumNegativo = numeros.stream()
    .noneMatch(n -> n < 0);
// Resultado: true (nenhum é negativo)
```

### 8. findFirst() - Encontrar Primeiro

```java
List<String> nomes = Arrays.asList("Ana", "Bruno", "Carlos");

Optional<String> primeiro = nomes.stream()
    .filter(n -> n.startsWith("B"))
    .findFirst();
// Resultado: Optional[Bruno]
```

---

## EXEMPLOS PRÁTICOS COMPLETOS

### Exemplo 1: Filtrar e Transformar

```java
List<String> nomes = Arrays.asList("Ana", "Bruno", "Carlos", "Ana", "Diana");

// Nomes únicos em maiúscula, ordenados
List<String> resultado = nomes.stream()
    .distinct()                    // remove duplicatas
    .map(String::toUpperCase)      // transforma para maiúscula
    .sorted()                      // ordena alfabeticamente
    .toList();
// Resultado: ["ANA", "BRUNO", "CARLOS", "DIANA"]
```

### Exemplo 2: Filtrar por Condição e Contar

```java
List<String> frutas = Arrays.asList("maçã", "banana", "abacate", "laranja", "banana");

// Quantas frutas têm mais de 5 letras?
long quantidade = frutas.stream()
    .filter(f -> f.length() > 5)
    .count();
// Resultado: 3 (banana, abacate, laranja)
```

### Exemplo 3: Transformar e Coletar

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

// Dobrar cada número e coletar apenas os pares
List<Integer> resultado = numeros.stream()
    .map(n -> n * 2)              // [2, 4, 6, 8, 10]
    .filter(n -> n % 2 == 0)     // [2, 4, 6, 8, 10] (todos são pares)
    .toList();
// Resultado: [2, 4, 6, 8, 10]
```

### Exemplo 4: Com Objetos Complexos

```java
class Pessoa {
    String nome;
    int idade;
    
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
}

List<Pessoa> pessoas = Arrays.asList(
    new Pessoa("Ana", 25),
    new Pessoa("Bruno", 30),
    new Pessoa("Carlos", 20),
    new Pessoa("Diana", 35)
);

// Nomes das pessoas com mais de 25 anos, ordenados
List<String> nomes = pessoas.stream()
    .filter(p -> p.idade > 25)              // filtra por idade
    .map(p -> p.nome)                        // extrai só o nome
    .sorted()                                // ordena alfabeticamente
    .toList();
// Resultado: ["Bruno", "Diana"]
```

### Exemplo 5: Usando Method Reference

```java
List<String> nomes = Arrays.asList("ana", "bruno", "carlos");

// Lambda
List<String> maiusculas = nomes.stream()
    .map(nome -> nome.toUpperCase())
    .toList();

// Method Reference (mais limpo)
List<String> maiusculas2 = nomes.stream()
    .map(String::toUpperCase)
    .toList();

// Outro exemplo
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
int soma = numeros.stream()
    .reduce(0, Integer::sum);
```

---

## STREAM E OPTIONAL

Algumas operações retornam Optional porque o resultado pode não existir.

```java
List<Integer> numeros = Arrays.asList(1, 2, 3);

Optional<Integer> maximo = numeros.stream()
    .max(Integer::compare);

// Como usar o Optional
maximo.ifPresent(n -> System.out.println("Máximo: " + n));  // imprime: Máximo: 3

int valor = maximo.orElse(0);  // se não existir, usa 0
int valor2 = maximo.orElseThrow();  // se não existir, lança exceção
```

---

## QUANDO USAR CADA OPERAÇÃO

| Operação | Quando usar |
|----------|-------------|
| `filter` | Quando precisa restringir dados |
| `map` | Quando precisa extrair ou transformar |
| `distinct` | Quando precisa remover duplicatas |
| `sorted` | Quando precisa de ordem |
| `toList` | Quando precisa do resultado como lista |
| `count` | Quando precisa saber quantos elementos |
| `forEach` | Quando precisa executar uma ação (não retorna dado) |
| `reduce` | Quando precisa acumular valores |
| `min/max` | Quando precisa de extremos |

---

## CUIDADOS COMUNS

### 1. Não pode reusar uma Stream

```java
Stream<Integer> stream = numeros.stream();
stream.filter(n -> n > 3);  // funciona
stream.filter(n -> n < 10); // ERRO - stream já consumida
```

### 2. toList() retorna lista imutável (Java 16+)

```java
List<Integer> resultado = numeros.stream()
    .filter(n -> n > 3)
    .toList();  // lista imutável

// Se precisar mutável:
List<Integer> resultado2 = numeros.stream()
    .filter(n -> n > 3)
    .collect(Collectors.toCollection(ArrayList::new));  // lista mutável
```

### 3. forEach não é para coletar

```java
// ❌ ERRADO - forEach não retorna nada
List<String> resultado = new ArrayList<>();
nomes.stream()
    .forEach(n -> resultado.add(n.toUpperCase()));  // funciona mas não é idiomático

// ✅ CORRETO - use map + toList
List<String> resultado = nomes.stream()
    .map(String::toUpperCase)
    .toList();
```

---

## RESUMO RÁPIDO

```
Stream pipeline:
.fonte()              → cria a stream
    .operacao1()      → intermediária (retorna stream)
    .operacao2()      → intermediária (retorna stream)
    .operacaoFinal()  → terminal (retorna resultado)
```

| Tipo | Operações | Exemplo |
|------|-----------|---------|
| **Intermediárias** | filter, map, distinct, sorted, peek | Retornam stream |
| **Terminais** | toList, count, forEach, reduce, min, max | Retornam resultado |

---

## CHECKLIST STREAMS

- [ ] Stream é criada a partir de Collection ou Array?
- [ ] Operações intermediárias retornam stream (encadeáveis)?
- [ ] Operação terminal finaliza a stream?
- [ ] Usando `toList()` para coletar resultado?
- [ ] Não está reusando stream já consumida?
- [ ] forEach para ações, map+toList para coletar?
