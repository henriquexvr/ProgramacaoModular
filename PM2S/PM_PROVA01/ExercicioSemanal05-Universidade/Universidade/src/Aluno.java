import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private int numMatricula;
    private int codigoCurso;
    private List<ACG> atividades = new ArrayList<>();
    private static final int MIN_CREDITOS = 10;

    public Aluno(String nome, int numMatricula, int codigoCurso) {
        this.nome = nome;
        this.numMatricula = numMatricula;
        this.codigoCurso = codigoCurso;
    }

    public int adicionarAtividades(ACG atv){
        atividades.add(atv);
        return  atividades.size();
    }

    public boolean temMaisDeUmTipo(){
        boolean resposta = false;
        String atvBase = atividades.get(0).tipoAtividade();
        for (ACG atv : atividades) {
            if(!atv.tipoAtividade().equals(atvBase)){
                resposta = true;
            }
        }
        return resposta;
    }

    public boolean estaAprovado(){
        return temMaisDeUmTipo() && totalCreditos() >= MIN_CREDITOS;
    }

    public int totalCreditos(){
        int totalCreditos = 0;
        for (ACG atv : atividades) {
            totalCreditos += atv.gerarCreditos();
        }
        return totalCreditos;
    }
}
