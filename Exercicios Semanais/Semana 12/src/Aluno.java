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
