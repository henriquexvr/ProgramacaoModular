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

    // Questao 1: Metodo valorAReceber
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
            throw new IllegalArgumentException("Nota invalida. Avaliacao de professor: 0 a 5");
        }
        notasAvaliacao.add(nota);
    }

    public String getNome() {
        return nome;
    }
}
