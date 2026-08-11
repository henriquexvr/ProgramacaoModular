import java.util.LinkedList;

public class Aluno {

	private String nome;
	private String matricula;
	private int codigoCurso;
	private String descricao;
	private int cargaHoraria;
	private int totalCreditos;
	private LinkedList<String> atividades = new LinkedList<String>();
	private static final int MAX_CREDITOS = 4;
	private static final int MIN_CREDITOS = 10;
	private static final int CARGA_PROFISSIONAL = 30;
	private static final int CARGA_ESTAGIO = 45;
	private static final int CARGA_EXTENSAO = 20;

	public Aluno(String nome, String matricula, int codigoCurso) {
		this.nome = nome;
		this.matricula = matricula;
		this.codigoCurso = codigoCurso;
		cargaHoraria = 0;
		totalCreditos = 0;
	}

	public int gerarCreditos(int qntdHoras, int tipoDeAtividade) {
		cargaHoraria += qntdHoras;

		switch (tipoDeAtividade) {
            case 1:
                totalCreditos += retornaCreditos(qntdHoras, CARGA_PROFISSIONAL);
                break;
            case 2:
                totalCreditos += retornaCreditos(qntdHoras, CARGA_ESTAGIO);
                break;
            case 3:
                totalCreditos += retornaCreditos(qntdHoras, CARGA_EXTENSAO);
                break;
			default:
				return totalCreditos;
        }
    return totalCreditos;
	}

	private int retornaCreditos(int qntdHoras, int cargaAtividades){
				int creditos = qntdHoras/cargaAtividades;
				int creditosCalculados = 0;

                if(creditos > MAX_CREDITOS){
                    creditosCalculados += MAX_CREDITOS;
        		}else{
					creditosCalculados += creditos;
				}
				
		return creditosCalculados;
	}

	public int contarCreditos() {
		
	}

	public int numAtividades() {
		
	}

	public boolean EstaAprovado() {
		boolean formado = false;
		if(totalCreditos >= MAX_CREDITOS && se ele fez mais de dois tipos de atividade){
			formado = true;
		}
		return formado;
	}
}