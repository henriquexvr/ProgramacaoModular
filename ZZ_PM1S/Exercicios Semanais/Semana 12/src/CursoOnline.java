import java.util.ArrayList;
import java.util.List;

public class CursoOnline extends Curso implements Avaliavel {
    private static final double PORCENT_AULA = 0.2;
    private static final int AVALIACAO_MAX = 10;
    private static final int AVALIACAO_MIN = 0;
    private List<Integer> notasAvaliacao;

    public CursoOnline(String nome, double valorHora, List<VideoAula> videoAulas) {
        super(nome, calcularCarga(videoAulas), valorHora);
        this.notasAvaliacao = new ArrayList<>();
    }

    private static int calcularCarga(List<VideoAula> videoAulas) {
        int soma = 0;
        for (VideoAula v : videoAulas) {
            soma += v.duracaoAula();
        }
        return (int) (soma * (1 + PORCENT_AULA));
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
            throw new IllegalArgumentException("Nota invalida. Minimo: " + AVALIACAO_MIN + ", Maximo: " + AVALIACAO_MAX);
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
