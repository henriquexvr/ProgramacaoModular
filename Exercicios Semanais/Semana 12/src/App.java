import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        MaterialDidatico mat = new MaterialDidatico("Java Basico", 50.0, Arrays.asList("POO", "Classes"));

        VideoAula v1 = new VideoAula("Introducao", 30);
        VideoAula v2 = new VideoAula("Classes", 45);
        VideoAula v3 = new VideoAula("Heranca", 60);

        CursoPresencial cPresencial = new CursoPresencial("Java Presencial", 40, 25.0, mat);
        CursoOnline cOnline = new CursoOnline("Java Online", 25.0, Arrays.asList(v1, v2, v3));

        Professor prof = new Professor("Joao", 12345);
        prof.incluir(cPresencial);
        prof.incluir(cOnline);

        Aluno aluno = new Aluno("Maria", 1);
        aluno.matricular(cPresencial);

        // Questao 1
        System.out.println("Valor a receber do professor: R$ " + prof.valorAReceber());

        // Questao 2: Aluno avalia professor (nota 0 a 5)
        prof.adicionarAvaliacao(5);
        prof.adicionarAvaliacao(4);
        prof.adicionarAvaliacao(2);
        System.out.println("Avaliacao do professor: " + prof.calcularAvaliacao());

        // Questao 3
        System.out.println("Valor curso online: R$ " + cOnline.valorTotal());
        System.out.println("Carga horaria curso online: " + cOnline.qualCargaHoraria() + "h");
    }
}
