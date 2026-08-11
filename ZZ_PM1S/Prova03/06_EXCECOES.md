# TRATAMENTO DE EXCEÇÕES - Guia Completo

## O QUE É UMA EXCEÇÃO?

Uma exceção é um **erro que acontece durante a execução do programa**. É o Java dizendo: "Algo de errado aconteceu, e eu não sei o que fazer!"

### Analogia

Imagine que você está dirigindo:
- **Sem tratamento:** Você bate o carro e fica no chão esperando alguém ajudar
- **Com tratamento:** Você tem um airbag e cinto de segurança - o erro acontece, mas você está protegido

Exceções são o "airbag" do seu programa.

---

## POR QUE EXISTEM EXCEÇÕES?

### Sem Exceções - Código Perigoso

```java
// ❌ Sem tratamento de erro
int dividir(int a, int b) {
    return a / b;  // e se b for 0?
}

// O programa trava com ArithmeticException!
```

### Com Exceções - Código Seguro

```java
// ✅ Com tratamento
int dividir(int a, int b) {
    try {
        return a / b;
    } catch (ArithmeticException e) {
        System.out.println("Erro: divisão por zero!");
        return 0;
    }
}
```

**Agora:** O programa não trava. Ele trata o erro e continua.

---

## COMO FUNCIONA

### Hierarquia de Exceções

```
Throwable (tudo que pode ser lançado)
  ├── Error (erros GRAVES da JVM - NÃO trate)
  │     ├── OutOfMemoryError
  │     ├── StackOverflowError
  │     └── ...
  │
  └── Exception (erros que você PODE tratar)
        ├── RuntimeException (UNCHECKED - não é obrigatório tratar)
        │     ├── NullPointerException
        │     ├── ArrayIndexOutOfBoundsException
        │     ├── IllegalArgumentException
        │     ├── ArithmeticException
        │     └── ...
        │
        └── Checked Exceptions (OBRIGATÓRIO tratar)
              ├── IOException
              ├── FileNotFoundException
              ├── ClassNotFoundException
              └── ...
```

### Checked vs Unchecked

| Tipo | Obrigatório tratar? | Quando usar |
|------|---------------------|-------------|
| **Unchecked** (RuntimeException) | NÃO | Erros de programação (null, índice inválido, etc) |
| **Checked** (Exception) | SIM | Erros previsíveis (arquivo não encontrado, rede caiu, etc) |

---

## ESTRUTURA TRY-CATCH-FINALLY

### Try-Catch

```java
try {
    // Código que PODE dar erro
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    // Código que roda SE der o erro específico
    System.out.println("Erro de aritmética: " + e.getMessage());
}
// Output: Erro de aritmética: / by zero
```

### Try-Catch com Múltiplos Catch

```java
try {
    // Código que pode dar vários tipos de erro
    int[] numeros = {1, 2, 3};
    int valor = numeros[5];  // ArrayIndexOutOfBoundsException
    int resultado = 10 / 0;  // ArithmeticException
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Índice inválido: " + e.getMessage());
} catch (ArithmeticException e) {
    System.out.println("Aritmética: " + e.getMessage());
} catch (Exception e) {
    // catch genérico - pega qualquer outro erro
    System.out.println("Erro geral: " + e.getMessage());
}
```

**Importante:** Os catches são avaliados **de cima para baixo**. O primeiro que "pegar" o erro, executa.

### Try-Catch-Finally

```java
try {
    FileReader arquivo = new FileReader("dados.txt");
    // lê o arquivo...
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado!");
} finally {
    // SEMPRE executa, com ou sem erro
    System.out.println("Finalizando...");
}
```

**O finally é útil para:**
- Fechar conexões
- Liberar recursos
- Limpar memória

### Try-with-resources (Java 7+)

```java
// Antes - precisa fechar manualmente
FileReader arquivo = null;
try {
    arquivo = new FileReader("dados.txt");
    // lê...
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado!");
} finally {
    if (arquivo != null) {
        try {
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Depois - automático!
try (FileReader arquivo = new FileReader("dados.txt")) {
    // lê...
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado!");
}
// arquivo.close() é chamado automaticamente!
```

---

## LANÇANDO EXCEÇÕES

### throw - Lançar uma exceção

```java
public void setNome(String nome) {
    if (nome == null || nome.isEmpty()) {
        throw new IllegalArgumentException("Nome não pode ser vazio!");
        // throw LANÇA a exceção
    }
    this.nome = nome;
}
```

### throws - Declarar que o método pode lançar

```java
// Método declara que pode lançar IOException
public void lerArquivo(String caminho) throws IOException {
    FileReader arquivo = new FileReader(caminho);
    // ...
}

// Quem CHAMA o método precisa tratar ou declarar
public static void main(String[] args) {
    try {
        lerArquivo("dados.txt");
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo!");
    }
}
```

### Diferença entre throw e throws

| Palavra | Significado | Onde fica |
|---------|-------------|-----------|
| `throw` | **Lança** a exceção | Dentro do método |
| `throws` | **Declara** que pode lançar | Na assinatura do método |

---

## EXCEÇÕES CUSTOMIZADAS

Crie suas próprias exceções para erros específicos do seu domínio.

```java
// Exceção customizada
public class PedidoFechadoException extends IllegalStateException {
    private int idPedido;
    
    public PedidoFechadoException(int id) {
        super("Não pode adicionar itens em pedido fechado.");
        this.idPedido = id;
    }
    
    public int getPedido() {
        return idPedido;
    }
}

// Uso
public int adicionar(IProduto item) {
    if (!podeAdicionar()) {
        throw new PedidoFechadoException(idPedido);
        // lança exceção customizada
    }
    itens.addLast(item);
    return itens.size();
}
```

### Exemplo no Xulambs

```java
public class DistanciaInvalidaException extends IllegalArgumentException {
    public DistanciaInvalidaException() {
        super("Distância deve ser entre 0.1 e 20 km");
    }
}

public PedidoEntrega(double distancia) {
    if (distancia <= 0 || distancia > 20) {
        throw new DistanciaInvalidaException();
    }
    this.distanciaEntrega = distancia;
}
```

---

## EXCEÇÕES NO CÓDIGO XULAMBS

### Exemplo 1: Validação no Construtor

```java
public Cliente(int id, String nome) {
    if (id < 0)
        throw new IllegalArgumentException("Id inválido");
    if (nome == null || nome.length() < 2)
        throw new IllegalArgumentException("Nome inválido");
    
    this.id = id;
    this.nome = nome;
}
```

### Exemplo 2: Estado Inválido

```java
public int adicionar(IProduto item) {
    if (item == null)
        throw new IllegalArgumentException("Produto não foi criado corretamente");
    if (!podeAdicionar())
        throw new PedidoFechadoException(idPedido);
    
    itens.addLast(item);
    return itens.size();
}
```

### Exemplo 3: Desconto Inválido

```java
public double aplicarDesconto(double valor) {
    if (valor < 0 || valor > valorItens())
        throw new IllegalArgumentException("Desconto inválido");
    if (desconto != 0)
        throw new IllegalStateException("Desconto já foi aplicado");
    if (!aberto)
        throw new PedidoFechadoException(idPedido);
    
    desconto = valor;
    return desconto;
}
```

---

## REGRA DE OURO

> **NUNCA deixe um catch vazio!**

```java
// ❌ ERRADO - catch vazio
try {
    // código...
} catch (Exception e) {
    // nada! o erro desaparece!
}

// ✅ CORRETO - trate ou registre
try {
    // código...
} catch (Exception e) {
    System.out.println("Erro: " + e.getMessage());
    // ou
    e.printStackTrace();  // mostra o erro completo
}
```

**Por quê?** Se você captura um erro e não faz nada, o erro some silenciosamente. Você vai gastar horas tentando entender por que o programa não funciona.

---

## PROGRAMAÇÃO POR CONTRATO

### O que é?

As interfaces dos componentes devem ser **especificadas formalmente**:

| Conceito | Significado | Exemplo |
|----------|-------------|---------|
| **Pré-condições** | O que o método **exige** para funcionar | Parâmetro não pode ser nulo |
| **Pós-condições** | O que o método **garante** após executar | Saldo nunca fica negativo |
| **Invariantes** | Condições **sempre** verdadeiras | Nome nunca é vazio |

### Como implementar

```java
public void depositar(double valor) {
    // Pré-condição: valor deve ser positivo
    if (valor <= 0)
        throw new IllegalArgumentException("Valor deve ser positivo");
    
    this.saldo += valor;
    
    // Pós-condição: saldo é positivo
    assert this.saldo >= 0 : "Saldo ficou negativo!";
}
```

---

## RESUMO RÁPIDO

| Comando | Significado |
|---------|-------------|
| `try` | Código que pode dar erro |
| `catch` | Código que roda SE der o erro |
| `finally` | Código que SEMPRE roda |
| `throw` | Lança uma exceção |
| `throws` | Declara que pode lançar |
| `try-with-resources` | Fecha recursos automaticamente |

### Hierarquia

```
Throwable
  ├── Error (NÃO trate)
  └── Exception
        ├── RuntimeException (unchecked)
        └── Checked (obrigatório tratar)
```

---

## CHECKLIST EXCEÇÕES

- [ ] Todo try tem pelo menos um catch ou finally?
- [ ] Catch não está vazio?
- [ ] Exceções customizadas estendem a exceção correta?
- [ ] Validações no construtor lançam IllegalArgumentException?
- [ ] Estados inválidos lançam IllegalStateException?
- [ ] Não está usando catch genérico demais?
- [ ] Recursos são fechados (try-with-resources)?
