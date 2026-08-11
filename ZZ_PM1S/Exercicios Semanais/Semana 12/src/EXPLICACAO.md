# Explicacao Detalhada - PM_EP4_2026

## Visao Geral do Sistema

O sistema gerencia cursos preparatorios para certificacoes de TI.
Existem dois tipos de cursos: Presencial e Online.
Professores lecionam cursos e sao avaliados por alunos.
Alunos se matriculam em cursos e podem avalia-los.

---

## Hierarquia de Classes (Heranca)

```
        Avaliavel (interface)
       /         \
  Professor    CursoOnline
                  |
               Curso (abstrata)
              /         \
   CursoPresencial    CursoOnline
```

### Por que usamos heranca?

CursoPresencial e CursoOnline compartilham atributos comuns (nome, valorHora, cargaHoraria).
A classe abstrata Curso evita repeticao de codigo. Cada subtype implementa valorTotal() e valorAulas() de forma diferente.

### Por que usamos interface?

Avaliavel define um contrato: qualquer classe que implementa ela DEVE ter calcularAvaliacao().
Isso permite polimorfismo - podemos tratar Professor e CursoOnline de forma generica.

---

## Arquivo por Arquivo

### 1. Avaliavel.java (Interface)

```java
public interface Avaliavel {
    double calcularAvaliacao();
}
```

**O que e:** Uma interface (contrato).
**Por que:** Define que classes "avaliaveis" devem calcular uma avaliacao.
**Quem implementa:** Professor e CursoOnline.
**Polimorfismo:** Podemos criar uma lista de Avaliavel e chamar calcularAvaliacao() em qualquer um.

---

### 2. Curso.java (Classe Abstrata)

```java
public abstract class Curso {
    private String nome;
    protected double valorHora;    // protected: acessivel por subclasses
    private int cargaHoraria;

    public Curso(String nome, int cargaHoraria, double valorHora) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.valorHora = valorHora;
    }

    public abstract double valorTotal();   // obrigatorio implementar no filho
    public abstract double valorAulas();   // obrigatorio implementar no filho

    public int qualCargaHoraria() {
        return cargaHoraria;
    }
}
```

**O que e:** Classe abstrata - nao pode ser instanciada diretamente.
**Atributos:**
- `nome` (private): nome do curso
- `valorHora` (protected): valor por hora. e protected PARA QUE as subclasses (CursoPresencial, CursoOnline) possam acessar diretamente
- `cargaHoraria` (private): total de horas do curso

**Metodos abstratos:**
- `valorTotal()`: cada subtype calcula de forma diferente
- `valorAulas()`: cada subtype calcula de forma diferente

**Por que abstract?** Porque nao faz sentido criar um "Curso generico" - so existem Cursos Presenciais ou Online.

---

### 3. CursoPresencial.java

```java
public class CursoPresencial extends Curso {
    private static final int CARGA_MIN = 30;    // constante: carga minima
    private static final int CARGA_MAX = 120;   // constante: carga maxima
    private MaterialDidatico material;           // agregacao

    public CursoPresencial(String nome, int horas, double valorHora, MaterialDidatico material) {
        super(nome, horas, valorHora);   // chama construtor da superclass
        if (horas < CARGA_MIN || horas > CARGA_MAX) {
            throw new IllegalArgumentException("Carga horaria invalida");
        }
        this.material = material;
    }

    @Override
    public double valorTotal() {
        return valorAulas() + material.valorTotal();   // aulas + material
    }

    @Override
    public double valorAulas() {
        return qualCargaHoraria() * valorHora;   // carga * valorHora
    }
}
```

**Constantes usadas:**
- `CARGA_MIN = 30`: valida carga horaria minima
- `CARGA_MAX = 120`: valida carga horaria maxima

**Agregacao:** CursoPresencial TEM UM MaterialDidatico (relacao has-a).
O material pode ser compartilhado entre cursos (conteudo reutilizavel).

**Construtor:**
1. Chama `super()` para inicializar atributos da superclass
2. Valida se horas esta entre 30 e 120
3. Armazena o material

**valorTotal():** Soma o valor das aulas com o valor do material.
**valorAulas():** Multiplica carga horaria pelo valor hora.

---

### 4. CursoOnline.java

```java
import java.util.ArrayList;
import java.util.List;

public class CursoOnline extends Curso implements Avaliavel {
    private static final double PORCENT_AULA = 0.2;     // 20% de tutoria
    private static final int AVALIACAO_MAX = 10;        // nota maxima
    private static final int AVALIACAO_MIN = 0;         // nota minima
    private List<Integer> notasAvaliacao;

    public CursoOnline(String nome, double valorHora, List<VideoAula> videoAulas) {
        super(nome, calcularCarga(videoAulas), valorHora);   // calcula carga antes
        this.notasAvaliacao = new ArrayList<>();
    }

    // Metodo estatico: nao precisa de objeto para rodar
    private static int calcularCarga(List<VideoAula> videoAulas) {
        int soma = 0;
        for (VideoAula v : videoAulas) {
            soma += v.duracaoAula();
        }
        return (int) (soma * (1 + PORCENT_AULA));   // soma + 20%
    }

    @Override
    public double valorTotal() {
        return qualCargaHoraria() * valorHora;
    }

    @Override
    public double valorAulas() {
        return qualCargaHoraria() * valorHora;
    }

    public void adicionarAvaliacao(int nota) {
        if (nota < AVALIACAO_MIN || nota > AVALIACAO_MAX) {
            throw new IllegalArgumentException("Nota invalida");
        }
        notasAvaliacao.add(nota);
    }

    @Override
    public double calcularAvaliacao() {
        if (notasAvaliacao.isEmpty()) return 0;
        double soma = 0;
        for (int nota : notasAvaliacao) {
            soma += nota;
        }
        return soma / notasAvaliacao.size();
    }
}
```

**Constantes usadas:**
- `PORCENT_AULA = 0.2`: 20% acrescimo para tutoria
- `AVALIACAO_MAX = 10`: nota maxima na avaliacao do curso
- `AVALIACAO_MIN = 0`: nota minima na avaliacao do curso

**Metodo estatico calcularCarga():**
- e `static` porque precisa calcular A ANTES de criar o objeto
- Nao pode acessar `this` (objeto ainda nao existe)
- Soma duracao de todas as videoaulas e multiplica por 1.2 (+20%)

**Por que implements Avaliavel?**
CursoOnline pode ser avaliado por alunos (nota 0 a 10).

**calcularAvaliacao():** Media aritmetica das notas recebidas.

---

### 5. MaterialDidatico.java

```java
import java.util.List;

public class MaterialDidatico {
    private String nome;
    private double valor;
    private List<String> conteudo;

    public MaterialDidatico(String nome, double valor, List<String> conteudo) {
        this.nome = nome;
        this.valor = valor;
        this.conteudo = conteudo;
    }

    public double valorTotal() {
        return valor;
    }
}
```

**O que e:** Classe de apoio que representa material didatico.
**Atributos:**
- `nome`: nome do material
- `valor`: custo do material
- `conteudo`: lista de topicos abordados

**valorTotal():** Retorna o valor do material (simples).

---

### 6. VideoAula.java

```java
public class VideoAula {
    private String titulo;
    private int duracao;

    public VideoAula(String titulo, int duracao) {
        this.titulo = titulo;
        this.duracao = duracao;
    }

    public int duracaoAula() {
        return duracao;
    }
}
```

**O que e:** Classe de apoio que representa uma videoaula.
**Atributos:**
- `titulo`: nome da videoaula
- `duracao`: duracao em minutos

---

### 7. Professor.java (Questao 1)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Professor implements Avaliavel {
    private static final int MULTIPLO_AULAS = 5;
    private String nome;
    private int matricula;
    private Map<Integer, Curso> lecionados;
    private List<Integer> notasAvaliacao;

    public Professor(String nome, int matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.lecionados = new HashMap<>();
        this.notasAvaliacao = new ArrayList<>();
    }

    public int incluir(Curso curso) {
        lecionados.put(curso.hashCode(), curso);
        return lecionados.size();
    }

    // QUESTAO 1
    public double valorAReceber() {
        double soma = 0;
        for (Curso curso : lecionados.values()) {
            soma += curso.valorAulas();
        }
        return soma * MULTIPLO_AULAS;
    }

    @Override
    public double calcularAvaliacao() {
        int somaPositivas = 0;
        int somaNegativas = 0;
        int qtdPositivas = 0;
        for (int nota : notasAvaliacao) {
            if (nota >= 4) {
                somaPositivas += nota;
                qtdPositivas++;
            } else if (nota <= 2) {
                somaNegativas += nota;
            }
        }
        if (qtdPositivas == 0) return 0;
        return (somaPositivas - somaNegativas) / (double) qtdPositivas;
    }

    public void adicionarAvaliacao(int nota) {
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("Nota invalida. Avaliacao: 0 a 5");
        }
        notasAvaliacao.add(nota);
    }
}
```

**Constante usada:**
- `MULTIPLO_AULAS = 5`: multiplicador para calcular valor recebido

**Dados do professor:**
- `lecionados` (Map<Integer, Curso>): cursos que o professor leciona. Usa hashCode() do curso como chave.
- `notasAvaliacao` (List<Integer>): notas que os alunos deram ao professor.

**QUESTAO 1 - valorAReceber():**
```
Para cada curso que o professor leciona:
    soma += curso.valorAulas()
Retorna: soma * MULTIPLO_AULAS (5)
```

**calcularAvaliacao() do professor (diferente da do curso):**
- Notas positivas (4 ou 5): entram no somatorio positivo
- Notas negativas (1 ou 2): entram no somatorio negativo
- Nota 3: ignorada (neutra)
- Formula: (somaPositivas - somaNegativas) / qtdPositivas

---

### 8. Aluno.java

```java
import java.util.HashMap;
import java.util.Map;

public class Aluno {
    private String nome;
    private int matricula;
    private Map<Integer, Curso> cursados;

    public Aluno(String nome, int matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.cursados = new HashMap<>();
    }

    public boolean fezCurso(Curso curso) {
        return cursados.containsKey(curso.hashCode());
    }

    public int matricular(Curso curso) {
        cursados.put(curso.hashCode(), curso);
        return cursados.size();
    }
}
```

**Dados do aluno:**
- `cursados` (Map<Integer, Curso>): cursos que o aluno ja fez/matriculou.

**Map<Integer, Curso>:**
- Chave: hashCode() do curso (identificador unico)
- Valor: o objeto Curso

---

### 9. App.java (Questoes 2 e 3)

```java
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        // Criar material
        MaterialDidatico mat = new MaterialDidatico("Java Basico", 50.0, Arrays.asList("POO", "Classes"));

        // Criar videoaulas
        VideoAula v1 = new VideoAula("Introducao", 30);
        VideoAula v2 = new VideoAula("Classes", 45);
        VideoAula v3 = new VideoAula("Heranca", 60);

        // Criar cursos
        CursoPresencial cPresencial = new CursoPresencial("Java Presencial", 40, 25.0, mat);
        CursoOnline cOnline = new CursoOnline("Java Online", 25.0, Arrays.asList(v1, v2, v3));

        // Criar professor e incluir cursos
        Professor prof = new Professor("Joao", 12345);
        prof.incluir(cPresencial);
        prof.incluir(cOnline);

        // Criar aluno e matricular
        Aluno aluno = new Aluno("Maria", 1);
        aluno.matricular(cPresencial);

        // Questao 1: Valor a receber
        System.out.println("Valor a receber: R$ " + prof.valorAReceber());

        // Questao 2: Avaliar professor
        prof.adicionarAvaliacao(5);
        prof.adicionarAvaliacao(4);
        prof.adicionarAvaliacao(2);
        System.out.println("Avaliacao: " + prof.calcularAvaliacao());

        // Questao 3: Valor curso online
        System.out.println("Valor curso online: R$ " + cOnline.valorTotal());
    }
}
```

---

## As 3 Questoes Pontuadas - Explicacao

### Questao 1: valorAReceber() do Professor

**Regra:** O valor recebido e a soma dos valores das aulas dos cursos, multiplicada por 5.

**Codigo:**
```java
public double valorAReceber() {
    double soma = 0;
    for (Curso curso : lecionados.values()) {
        soma += curso.valorAulas();   // polimorfismo!
    }
    return soma * MULTIPLO_AULAS;
}
```

**Por que funciona com qualquer tipo de curso?**
Porque `valorAulas()` e abstrato em Curso.
- Se o curso e CursoPresencial: valorAulas() = cargaHoraria * valorHora
- Se o curso e CursoOnline: valorAulas() = cargaHoraria * valorHora

O Professor nao precisa saber o tipo do curso. Ele chama valorAulas() e cada subtype implementa do seu jeito. Isso e POLIMORFISMO.

**Exemplo numerico:**
- CursoPresencial: 40h * R$25 = R$1.000
- CursoOnline: 162h * R$25 = R$4.050
- Total: (R$1.000 + R$4.050) * 5 = R$25.250

---

### Questao 2: Avaliacao de Professor

**Regra:** Aluno avalia professor com nota 0 a 5.

**Codigo no App:**
```java
prof.adicionarAvaliacao(5);
prof.adicionarAvaliacao(4);
prof.adicionarAvaliacao(2);
```

**Metodo adicionarAvaliacao():**
```java
public void adicionarAvaliacao(int nota) {
    if (nota < 0 || nota > 5) {
        throw new IllegalArgumentException("Nota invalida");
    }
    notasAvaliacao.add(nota);
}
```

**Calculo da avaliacao do professor:**
```java
public double calcularAvaliacao() {
    int somaPositivas = 0, somaNegativas = 0, qtdPositivas = 0;
    for (int nota : notasAvaliacao) {
        if (nota >= 4) {        // positiva: 4 ou 5
            somaPositivas += nota;
            qtdPositivas++;
        } else if (nota <= 2) { // negativa: 1 ou 2
            somaNegativas += nota;
        }
        // nota 3: ignorada (neutra)
    }
    if (qtdPositivas == 0) return 0;
    return (somaPositivas - somaNegativas) / (double) qtdPositivas;
}
```

**Exemplo:** Notas 5, 4, 2
- Positivas: 5 + 4 = 9, qtd = 2
- Negativas: 2
- Resultado: (9 - 2) / 2 = 3.5

---

### Questao 3: Valor do Curso Online

**Regra:** valor = valorHora * cargaHoraria (onde cargaHoraria = videoaulas + 20%)

**Codigo:**
```java
public double valorTotal() {
    return qualCargaHoraria() * valorHora;
}
```

**Calculo da cargaHoraria (no construtor):**
```java
private static int calcularCarga(List<VideoAula> videoAulas) {
    int soma = 0;
    for (VideoAula v : videoAulas) {
        soma += v.duracaoAula();  // 30 + 45 + 60 = 135
    }
    return (int) (soma * (1 + PORCENT_AULA));  // 135 * 1.2 = 162
}
```

**Exemplo:** Videos de 30, 45 e 60 minutos
- Soma: 30 + 45 + 60 = 135
- Com tutoria: 135 * 1.2 = 162h
- Valor: 162 * R$25 = R$4.050

---

## Conceitos SOLID Aplicados

### S - Single Responsibility
Cada classe tem uma unica responsabilidade:
- MaterialDidatico: so gerencia material
- VideoAula: so gerencia videoaulas
- Curso: so gerencia dados comuns de cursos
- Professor: so gerencia professor e suas avaliacoes

### O - Open/Closed
Curso e abstrata. Para adicionar um novo tipo de curso (ex: CursoHibrido), basta criar uma nova subclasse. Nao precisa modificar as classes existentes.

### L - Liskov Substitution
CursoPresencial e CursoOnline podem ser usadas onde Curso for esperado. Todas as subclasses implementam os mesmos metodos abstratos.

### I - Interface Segregation
Avaliavel tem apenas um metodo: calcularAvaliacao(). Classes que nao precisam ser avaliadas nao sao forcadas a implementar metodos desnecessarios.

### D - Dependency Inversion
Professor depende da abstracao Curso (superclass), nao de CursoPresencial ou CursoOnline diretamente.

---

## Principios de Programacao Modular

### Encapsulamento
- Atributos sao `private` (exceto valorHora que e `protected`)
- Acesso通过 metodos publicos (getters, adicionarAvaliacao, etc.)

### Heranca
- CursoPresencial extends Curso
- CursoOnline extends Curso

### Polimorfismo
- Professor.valorAReceber() chama curso.valorAulas() sem saber o tipo
- O Java decide em tempo de execucao qual metodo chamar

### Abstracao
- Curso e abstrata: define o que fazer (valorTotal, valorAulas) mas nao como
- Cada subtype decide como implementar

---

## Constantes do Diagrama UML

| Classe | Constante | Valor | Uso |
|--------|-----------|-------|-----|
| CursoPresencial | CARGA_MIN | 30 | Validacao carga horaria |
| CursoPresencial | CARGA_MAX | 120 | Validacao carga horaria |
| CursoOnline | PORCENT_AULA | 0.2 | Calculo carga (+20% tutoria) |
| CursoOnline | AVALIACAO_MAX | 10 | Validacao nota maxima |
| CursoOnline | AVALIACAO_MIN | 0 | Validacao nota minima |
| Professor | MULTIPLO_AULAS | 5 | Calculo valor recebido |
