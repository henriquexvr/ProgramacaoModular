# COLLECTIONS FRAMEWORK - Guia Completo

## O QUE É UMA COLEÇÃO?

Uma coleção é um **conjunto de dados que podem ser percorridos**. É como um "container" que guarda vários elementos.

### Analogia

- **Array** é como um prato fixo - cabe exatamente X coisas
- **Coleção** é como uma panela - você vai adicionando coisas conforme precisa

---

## POR QUE EXISTEM COLEÇÕES?

### Sem Coleções - Código Limitado

```java
// Array - tamanho fixo
String[] nomes = new String[3];
nomes[0] = "Ana";
nomes[1] = "Bruno";
nomes[2] = "Carlos";
// E se quiser adicionar "Diana"? Precisa criar novo array!
```

### Com Coleções - Código Flexível

```java
// ArrayList - tamanho dinâmico
List<String> nomes = new ArrayList<>();
nomes.add("Ana");
nomes.add("Bruno");
nomes.add("Carlos");
nomes.add("Diana");  // cresce automaticamente!
```

---

## HIERARQUIA DAS COLLECTIONS

```
Iterable (pode usar foreach)
  └── Collection
        ├── List (indexada, permite duplicatas)
        │     ├── ArrayList (array dinâmico)
        │     ├── LinkedList (nós encadeados)
        │     └── Vector (antigo, não usar)
        │
        ├── Set (não indexada, NÃO permite duplicatas)
        │     ├── HashSet (hash, ordem aleatória)
        │     ├── TreeSet (árvore, ordenada)
        │     └── LinkedHashSet (hash + ordem de inserção)
        │
        └── Queue (fila - primeiro a entrar, primeiro a sair)
              ├── LinkedList (também é Queue)
              ├── PriorityQueue (prioridade)
              └── ArrayDeque (pilha/fila dupla)

Map (NÃO é Collection - pares chave-valor)
  ├── HashMap (hash, ordem aleatória)
  ├── TreeMap (árvore, ordenada)
  └── LinkedHashMap (hash + ordem de inserção)
```

---

## LIST - Coleção Indexada

### O que é?

Uma **lista** é uma coleção onde cada elemento tem uma **posição (índice)**. Você pode acessar qualquer elemento pelo índice.

### ArrayList vs LinkedList

| Característica | ArrayList | LinkedList |
|----------------|-----------|------------|
| **Estrutura** | Array dinâmico | Nós encadeados |
| **Acesso por índice** | ⚡ Rápido O(1) | 🐌 Lento O(n) |
| **Inserção no meio** | 🐌 Lento O(n) | ⚡ Rápido O(1)* |
| **Memória** | Menos overhead | Mais overhead |
| **Melhor para** | Acesso por índice | Inserções/remoções frequentes |

*Desde que você já tenha a referência do nó

### Regra de Ouro

> **Na dúvida, use ArrayList.** É a escolha padrão em 90% dos casos!

### ArrayList - Como Usar

```java
import java.util.ArrayList;
import java.util.List;

// Criar
List<String> nomes = new ArrayList<>();

// Adicionar
nomes.add("Ana");           // adiciona no final
nomes.add("Bruno");
nomes.add(0, "Carlos");    // adiciona na posição 0

// Acessar
String primeiro = nomes.get(0);  // "Carlos"
int tamanho = nomes.size();      // 3

// Modificar
nomes.set(1, "Diana");    // substitui "Bruno" por "Diana"

// Remover
nomes.remove(0);           // remove por índice
nomes.remove("Diana");     // remove por objeto

// Verificar
boolean tem = nomes.contains("Ana");  // true
boolean vazia = nomes.isEmpty();       // false

// Limpar
nomes.clear();             // remove tudo
```

### LinkedList - Como Usar

```java
import java.util.LinkedList;
import java.util.List;

// Criar
List<String> fila = new LinkedList<>();

// Adicionar
fila.add("Primeiro");
fila.addLast("Último");
fila.addFirst("Início");    // ⭐ rápido!

// Acessar
String primeiro = fila.getFirst();  // ⭐ rápido!
String ultimo = fila.getLast();     // ⭐ rápido!

// Remover
String removido = fila.removeFirst();  // ⭐ rápido!
String removido2 = fila.removeLast();  // ⭐ rápido!

// Operações de fila (Queue)
fila.offer("Novo item");    // adiciona
String proximo = fila.poll();  // remove e retorna primeiro
String espiar = fila.peek();   // visualiza sem remover
```

### Quando Usar Cada?

| Situação | Use |
|----------|-----|
| Acesso por índice frequente | ArrayList |
| Inserções/remoções no início/meio | LinkedList |
| Implementar fila/pilha | LinkedList |
| Não sabe qual usar | ArrayList |

---

## SET - Coleção sem Duplicatas

### O que é?

Um **set** é uma coleção que **NÃO permite elementos duplicados**. Se você tenta adicionar algo que já existe, é ignorado.

### HashSet - Rápido, ordem aleatória

```java
import java.util.HashSet;
import java.util.Set;

Set<String> frutas = new HashSet<>();
frutas.add("Maçã");
frutas.add("Banana");
frutas.add("Maçã");  // ignorado - já existe!

System.out.println(frutas);  // [Banana, Maçã] (ordem aleatória)
System.out.println(frutas.size());  // 2
```

### TreeSet - Ordenado

```java
import java.util.TreeSet;
import java.util.Set;

Set<String> frutas = new TreeSet<>();
frutas.add("Banana");
frutas.add("Maçã");
frutas.add("Abacate");

System.out.println(frutas);  // [Abacate, Banana, Maçã] (ordem alfabética)
```

### Quando Usar Set?

| Situação | Use |
|----------|-----|
| Elementos únicos | Set |
| Não precisa de índice | Set |
| Precisa de ordenação | TreeSet |
| Não precisa de ordenação | HashSet |

---

## MAP - Pares Chave-Valor

### O que é?

Um **mapa** é um conjunto de pares **(chave, valor)**. Cada chave é única e aponta para um valor.

### Analogia

- **List** é como um armário com gavetas numeradas (1, 2, 3...)
- **Map** é como um armário com etiquetas ("meias", "camisas", "cuecas")

### HashMap - Rápido, ordem aleatória

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> idades = new HashMap<>();
idades.put("Ana", 25);
idades.put("Bruno", 30);
idades.put("Carlos", 20);

// Acessar
int idadeAna = idades.get("Ana");  // 25

// Modificar
idades.put("Ana", 26);  // atualiza Ana para 26

// Verificar
boolean tem = idades.containsKey("Ana");     // true
boolean temValor = idades.containsValue(30); // true

// Remover
idades.remove("Carlos");

// Iterar
for (String nome : idades.keySet()) {
    System.out.println(nome + ": " + idades.get(nome));
}
```

### TreeMap - Ordenado

```java
import java.util.TreeMap;
import java.util.Map;

Map<String, Integer> idades = new TreeMap<>();
idades.put("Carlos", 20);
idades.put("Ana", 25);
idades.put("Bruno", 30);

System.out.println(idades);  
// {Ana=25, Bruno=30, Carlos=20} (ordem alfabética das chaves)
```

### Quando Usar Map?

| Situação | Use |
|----------|-----|
| Precisa buscar algo rápido | Map |
| Dados são pares chave-valor | Map |
| Não precisa de ordenação | HashMap |
| Precisa de ordenação | TreeMap |

---

## OPERAÇÕES COMUNS EM TODAS AS COLEÇÕES

### Adicionar

```java
collection.add(elemento);  // retorna true se adicionou
```

### Remover

```java
collection.remove(elemento);  // retorna true se removeu
```

### Verificar

```java
boolean tem = collection.contains(elemento);
boolean vazia = collection.isEmpty();
int tamanho = collection.size();
```

### Limpar

```java
collection.clear();  // remove tudo
```

### Iterar

```java
// For-each
for (String item : collection) {
    System.out.println(item);
}
```

---

## COLLECTIONS XULAMBS

### List<IProduto> em Pedido

```java
public abstract class Pedido {
    protected List<IProduto> itens;  // ArrayList ou LinkedList
    
    public Pedido() {
        itens = new LinkedList<>();  // LinkedList porque adicionar no final é O(1)
    }
    
    public int adicionar(IProduto item) {
        itens.addLast(item);
        return itens.size();
    }
    
    protected double valorItens() {
        double preco = 0;
        for (IProduto produto : itens) {
            preco += produto.valorAPagar();
        }
        return preco;
    }
}
```

### Collections.sort()

```java
// Ordenar itens do pedido
Collections.sort(itens);  // usa Comparable

// Ou com Comparator
itens.sort((p1, p2) -> Double.compare(p1.valorAPagar(), p2.valorAPagar()));
```

---

## OPTIONAL - Tratando Ausência de Valor

### O que é?

Optional representa um valor que **pode existir ou não**. É uma alternativa segura a `null`.

### Por que usar?

```java
// ❌ Ruim - retorna null
public Cliente buscarCliente(int id) {
    // ...
    return null;  // e agora?
}

// ✅ Bom - retorna Optional
public Optional<Cliente> buscarCliente(int id) {
    if (encontrado) {
        return Optional.of(cliente);
    }
    return Optional.empty();
}
```

### Operações

```java
Optional<String> nome = Optional.of("Ana");

// ifPresent - executa se existe
nome.ifPresent(n -> System.out.println(n));  // imprime: Ana

// orElse - retorna valor ou padrão
String valor = nome.orElse("Desconhecido");  // "Ana"

// orElseThrow - retorna valor ou lança exceção
String valor2 = nome.orElseThrow();  // "Ana"

// isPresent - verifica se existe
boolean existe = nome.isPresent();  // true
```

---

## RESUMO RÁPIDO

| Coleção | Característica | Quando usar |
|---------|----------------|-------------|
| **ArrayList** | Array dinâmico, rápido por índice | Padrão para listas |
| **LinkedList** | Nós encadeados, rápido para inserir/remover | Filas, pilhas |
| **HashSet** | Sem duplicatas, rápido | Elementos únicos |
| **TreeSet** | Sem duplicatas, ordenado | Elementos únicos ordenados |
| **HashMap** | Chave-valor, rápido | Buscar por chave |
| **TreeMap** | Chave-valor, ordenado | Buscar por chave ordenado |

### Regra de Ouro

> **Na dúvida, use ArrayList para listas e HashMap para mapas.**

---

## CHECKLIST COLLECTIONS

- [ ] Usando interface List (não classe concreta)?
- [ ] ArrayList para acesso por índice?
- [ ] LinkedList para inserções/remoções frequentes?
- [ ] Set para elementos únicos?
- [ ] Map para pares chave-valor?
- [ ] Não está usando raw types (lista sem `<T>`)?\boxed{8}
