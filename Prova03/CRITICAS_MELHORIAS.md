# CRÍTICAS E MELHORIAS — Estudo para Prova 3

> Registro dos erros identificados e pontos de melhoria para não repetir na prova.

---

## 1. MODELAGEM (Diagrama UML)

### Erro 1: Não ler o enunciado com calma

| O que aconteceu | O que deveria ter acontecido |
|-----------------|------------------------------|
| Li rápido e fui direto para listar classes | Ler o enunciado **2 vezes** antes de qualquer coisa |
| Pulei classes importantes (Veiculo, Roteiro) | Marcar **todo substantivo** como candidato a classe |

**Regra:** Se o enunciado diz "cadastrar X", X é uma **classe**. Se diz "registrar Y", Y é uma **classe**.

---

### Erro 2: Pular etapas do guia

| Etapa | O que fiz | O que deveria ter feito |
|-------|-----------|------------------------|
| Passo 1 (Substantivos/Verbos) | Fiz pela metade | Listar TUDO, mesmo que pareça redundante |
| Passo 2 (EU SOU/FAÇO) | Pulei | Sempre perguntar: "isso É UM ou FAZ isso?" |
| Passo 3 (MUDA vs FIXO) | Não apliquei | Identificar o que é fixo (enum) vs o que muda (interface) |

---

### Erro 3: Confundir parâmetro com atributo

| O que fiz | O correto |
|-----------|-----------|
| `carroAdequado()` sem parâmetro | `carroIndicado(roteiro: Roteiro)` — se o enunciado diz "a partir de X", X é parâmetro |
| `registrarRoteiro()` sem parâmetro | `registrarRoteiro(r: Roteiro)` — precisa receber o roteiro |

**Regra:** Toda vez que o enunciado diz "a partir de", "recebendo", "para determinado", é **parâmetro**.

---

### Erro 4: Esquecer relacionamentos

| O que fiz | O correto |
|-----------|-----------|
| Roteiro solto, sem ligação com ninguém | App guarda Roteiros (composição — losango) |
| Não pensei "o que guarda o quê" | Perguntar: "O que GUARDA o que?" → isso é composição |

---

### Erro 5: Interface vs Herança — dúvida sem responder

| Situação | Resposta |
|----------|----------|
| "MotorCombustao É UM Motor?" | Sim → herança ou interface |
| "Motor tem código comum?" | Não (cada um calcula diferente) → **interface** |
| "MotorHibrido tem os dois?" | Herança múltipla não existe → **composição** |

**Regra:** Se cada implementação faz algo diferente, use **interface**. Se tem código compartilhado, use **classe abstrata**.

---

## 2. CÓDIGO JAVA

### Erro 6: Tipo de retorno errado

```java
// ❌ ERRADO — retorna Veiculo mas devolve int
public Veiculo cadastroVeiculo(Veiculo v) {
    veiculos.addLast(v);
    return veiculos.size();  // size() retorna int
}

// ✅ CORRETO
public int cadastroVeiculo(Veiculo v) {
    veiculos.add(v);
    return veiculos.size();
}
```

**Regra:** O tipo de retorno do método deve ser **compatível** com o que ele retorna.

---

### Erro 7: Método que não existe na classe

```java
// ❌ ERRADO — ArrayList não tem addLast()
veiculos.addLast(v);

// ✅ CORRETO — ArrayList usa add()
veiculos.add(v);
```

**Regra:** Verificar quais métodos a classe oferece. `addLast()` é de `LinkedList`, não de `ArrayList`.

---

### Erro 8: Visibilidade dos métodos

```java
// ❌ ERRADO — sem public, visibilidade de pacote
double calcularAutonomia() { ... }

// ✅ CORRETO — público para chamar de outras classes
public double calcularAutonomia() { ... }
```

**Regra:** Métodos que serão chamados de **outra classe** devem ser `public`.

---

### Erro 9: Atributo private sem getter

```java
// ❌ ERRADO — não consigo acessar motor de fora
public class Veiculo {
    private Motor motor;
}
// veiculo.motor → ERRO de compilação

// ✅ CORRETO — adicionar getter
public class Veiculo {
    private Motor motor;
    
    public Motor motorUtilizado() {
        return motor;
    }
}
```

**Regra:** Seprecisa acessar um atributo private de fora, crie um **método público** (getter).

---

### Erro 10: Diamond operator ausente

```java
// ❌ WARNINGS
private List<Veiculo> veiculos = new ArrayList();

// ✅ CORRETO
private List<Veiculo> veiculos = new ArrayList<>();
```

**Regra:** Sempre usar `<>` no construtor do ArrayList.

---

### Erro 11: orElseThrow sem lambda

```java
// ❌ ERRADO — não compila
.orElseThrow("Veiculo não encontrado");

// ✅ CORRETO — precisa de lambda
.orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado"));
```

**Regra:** `orElseThrow` recebe uma **lambda** ou **supplier**, não uma string direta.

---

### Erro 12: Não implementar na ordem correta

| O que fiz | O correto |
|-----------|-----------|
| Tentei fazer `veiculoIndicado()` antes de implementar `calcularAutonomia()` | Primeiro: motores → Veiculo.getMotor() → App |

**Regra:** Implementar de baixo para cima:
1. Classes base (motores)
2. Classes que dependem delas (Veiculo)
3. Classes que usam tudo (App)

---

## 3. PADRÕES DE CÓDIGO DO PROFESSOR

### Padrão 1: Método que adiciona em lista

```java
// Padrão do Pedido.adicionar()
public int adicionar(IProduto item) {
    if(item == null)
        throw new IllegalArgumentException("Produto não foi criado corretamente");
    itens.addLast(item);
    return itens.size();
}
```

### Padrão 2: Stream para selecionar o melhor

```java
// Padrão para a letra B
return veiculos.stream()
    .filter(v -> v.getMotor().calcularAutonomia() >= roteiro.distancia())
    .findFirst()
    .orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado"));
```

### Padrão 3: Método que retorna boolean (mudarMotor)

```java
// Padrão para mudarMotor
public boolean mudarMotor() {
    usandoHibrido = !usandoHibrido;
    return usandoHibrido;
}
```

---

## 4. CHECKLIST ANTES DE ENTREGAR

- [ ] Li o enunciado 2 vezes?
- [ ] Identifiquei todas as classes (substantivos)?
- [ ] Identifiquei todos os métodos (verbos)?
- [ ] Marquei os parâmetros ("a partir de" = parâmetro)?
- [ ] Verifiquei relacionamentos (o que guarda o quê)?
- [ ] O tipo de retorno é compatível?
- [ ] Os métodos são `public`?
- [ ] Tenho getters para atributos private?
- [ ] Implementei na ordem correta (base → dependente → app)?
- [ ] Não há `else` desnecessário?
- [ ] Não há múltiplos `return`?
- [ ] Diamond operator `<>` está presente?

---

## 5. EXERCÍCIO MENTAL RÁPIDO

Para cada método que for implementar, perguntar:

1. **O que recebo?** (parâmetro)
2. **O que tenho?** (atributos/lista)
3. **O que retorno?** (tipo de retorno)
4. **Qual a fórmula/lógica?** (cálculo)
