# GENERICS (Genéricos) - Guia Completo

## O PROBLEMA QUE GENERICS RESOLVE

### Sem Generics - Código Perigoso

Imagine que você quer criar uma "caixa" que guarda qualquer tipo de coisa:

```java
// Sem generics - usa Object
class Caixa {
    private Object objeto;  // Object é o pai de tudo
    
    public void guardar(Object obj) {
        this.objeto = obj;
    }
    
    public Object retirar() {
        return objeto;
    }
}
```

**O problema:** Tudo que entra vira Object. Quando você tira, precisa lembrar o tipo:

```java
Caixa caixa = new Caixa();
caixa.guardar("Texto");  // String
caixa.guardar(42);       // Integer

// Para usar, precisa de casting
String texto = (String) caixa.retirar();  // funciona
Integer numero = (Integer) caixa.retirar(); // funciona

// Mas e se esquecer o tipo?
String algo = (String) caixa.retirar(); // ERRO EM TEMPO DE EXECUÇÃO!
// caixa guarda um Integer, mas você tenta cast para String
// O compilador não avisa - o erro só aparece rodando!
```

### Com Generics - Código Seguro

```java
// Com generics - tipo definido na criação
class Caixa<T> {
    private T objeto;  // T é o tipo que você definir
    
    public void guardar(T obj) {
        this.objeto = obj;
    }
    
    public T retirar() {
        return objeto;
    }
}
```

**Agora:** O Java sabe o tipo desde o início:

```java
Caixa<String> caixaTexto = new Caixa<>();
caixaTexto.guardar("Olá");      // só aceita String
String texto = caixaTexto.retirar(); // não precisa de casting!

Caixa<Integer> caixaNumero = new Caixa<>();
caixaNumero.guardar(42);        // só aceita Integer
Integer numero = caixaNumero.retirar(); // não precisa de casting!

// caixaTexto.guardar(42); // ERRO DE COMPILAÇÃO - não aceita Integer!
// O Java protege você ANTES de rodar o programa
```

---

## O QUE É UM GENÉRICO?

Genérico é um **parâmetro de tipo**. Assim como um método recebe um número como parâmetro, uma classe genérica recebe um **tipo** como parâmetro.

```java
// Método com parâmetro de valor
void somar(int a, int b) {  // a e b são parâmetros de valor
    int resultado = a + b;
}

// Classe com parâmetro de tipo
class Caixa<T> {  // T é um parâmetro de tipo
    private T objeto;
}
```

### Convenção de Nomes

| Letra | Significado | Quando usar |
|-------|-------------|-------------|
| `T` | Type (Tipo) | Tipo genérico principal |
| `E` | Element (Elemento) | Coleções |
| `K` | Key (Chave) | Mapas |
| `V` | Value (Valor) | Mapas |
| `R` | Return (Retorno) | Métodos |

```java
// Exemplos
class Caixa<T> { ... }           // T = tipo genérico
class Lista<E> { ... }           // E = elemento
class Mapa<K, V> { ... }         // K = chave, V = valor
```

---

## COMO USAR GENERICS

### 1. Em Classes

```java
public class Caixa<T> {
    private T conteudo;
    
    public void guardar(T item) {
        this.conteudo = item;
    }
    
    public T pegar() {
        return conteudo;
    }
}

// Uso
Caixa<String> caixa1 = new Caixa<>();
caixa1.guardar("texto");
String texto = caixa1.pegar();  // tipo seguro

Caixa<Integer> caixa2 = new Caixa<>();
caixa2.guardar(42);
int numero = caixa2.pegar();     // autoboxing + tipo seguro
```

### 2. Em Métodos

```java
public class Utilidades {
    // Método genérico - tipo definido na chamada
    public static <T> T primeiro(T[] array) {
        if (array.length == 0) return null;
        return array[0];
    }
}

// Uso
String[] nomes = {"Ana", "Bruno", "Carlos"};
String primeiro = Utilidades.primeiro(nomes);  // T = String

Integer[] numeros = {1, 2, 3};
Integer primeiroNum = Utilidades.primeiro(numeros);  // T = Integer
```

### 3. Em Interfaces

```java
public interface Comparable<T> {
    int compareTo(T outro);
}

// Implementação
class Pessoa implements Comparable<Pessoa> {
    String nome;
    int idade;
    
    @Override
    public int compareTo(Pessoa outra) {
        return this.nome.compareTo(outra.nome);
    }
}
```

---

## DIAMOND OPERATOR (<>)

O `<>` é uma abreviação. O Java **infere** o tipo automaticamente:

```java
// Antes (Java 7 e anterior)
List<String> nomes = new ArrayList<String>();

// Agora (Java 7+) - diamond operator
List<String> nomes = new ArrayList<>();  // Java infere String

// É a mesma coisa!
```

**Regra:** Se o lado esquerdo já define o tipo, o lado direito pode usar `<>`.

---

## LIMITES DE TIPO (BOUND generics)

Às vezes você quer **restringir** quais tipos podem ser usados.

### Upper Bound (tipos permitidos)

```java
// T deve ser Number ou filha de Number (Integer, Double, etc)
public class CaixaNum<T extends Number> {
    private T valor;
    
    public double getValorDouble() {
        return valor.doubleValue(); // seguro - Number tem doubleValue()
    }
}

// Uso
CaixaNum<Integer> caixa1 = new CaixaNum<>();  // ✅ Integer é Number
CaixaNum<Double> caixa2 = new CaixaNum<>();   // ✅ Double é Number
CaixaNum<String> caixa3 = new CaixaNum<>();   // ❌ ERRO - String não é Number
```

### Multiple Bounds

```java
// T deve implementar Comparable E Serializable
public class Classe<T extends Comparable<T> & Serializable> {
    // ...
}
```

---

## GENERICS NO CÓDIGO XULAMBS

### List<IProduto> - Lista Genérica

```java
public abstract class Pedido {
    protected List<IProduto> itens;  // lista de qualquer IProduto
    
    public Pedido() {
        itens = new LinkedList<>();  // diamond operator
    }
    
    public int adicionar(IProduto item) {
        itens.addLast(item);  // aceita Pizza, Sanduiche, qualquer IProduto
        return itens.size();
    }
}
```

**O que acontece:** `List<IProduto>` significa "uma lista que guarda qualquer coisa que implemente IProduto". Pode ser Pizza, Sanduiche, ou qualquer futuro produto.

### Comparable<Pedido> - Interface Genérica

```java
public abstract class Pedido implements Comparable<Pedido> {
    @Override
    public int compareTo(Pedido outro) {
        double diferenca = this.precoAPagar() - outro.precoAPagar();
        if (diferenca != 0)
            return diferenca > 0 ? 1 : -1;
        return 0;
    }
}
```

**O que acontece:** `Comparable<Pedido>` significa " Pedido pode ser comparado com outro Pedido".

### IFidelidade - Método Genérico

```java
public interface IFidelidade {
    static IFidelidade definirCategoria(List<Pedido> pedidos) {
        // pedidos é List<Pedido> - genérico com Pedido
        int quantidade = pedidos.size();
        // ...
    }
}
```

---

## ERROS COMUNS

### 1. Tipo errado em tempo de compilação

```java
Caixa<String> caixa = new Caixa<>();
caixa.guardar(42);  // ❌ ERRO DE COMPILAÇÃO
// Caixa<String> só aceita String, não Integer
```

### 2. Casting desnecessário com generics

```java
// ❌ ERRADO - não precisa de casting
Caixa<String> caixa = new Caixa<>();
caixa.guardar("texto");
String texto = (String) caixa.pegar();  // casting desnecessário

// ✅ CORRETO - tipo já é String
String texto = caixa.pegar();
```

### 3. Raw types (tipo cru)

```java
// ❌ ERRADO - raw type (sem generics)
List lista = new ArrayList();
lista.add("texto");
String texto = (String) lista.get(0);  // precisa de casting

// ✅ CORRETO - com generics
List<String> lista = new ArrayList<>();
lista.add("texto");
String texto = lista.get(0);  // tipo seguro
```

---

## RESUMO RÁPIDO

| Conceito | Significado |
|----------|-------------|
| `<T>` | Parâmetro de tipo |
| `<>` | Diamond operator - Java infere o tipo |
| `<T extends X>` | T deve ser X ou subtipo de X |
| `List<String>` | Lista que guarda só String |
| `Caixa<T>` | Classe que guarda qualquer tipo |
| `<T> T metodo()` | Método genérico |

### Por que usar Generics?

1. **Segurança** - erro em tempo de compilação, não em execução
2. **Legibilidade** - código mais claro
3. **Performance** - sem casting em runtime

---

## CHECKLIST GENERICS

- [ ] Classe genérica usa `<T>` no nome?
- [ ] Diamond operator `<>` usado quando tipo já definido?
- [ ] Não há casting desnecessário?
- [ ] Não há raw types (listas sem `<T>`)?
- [ ] Limites de tipo usados quando necessário (`extends`)?
