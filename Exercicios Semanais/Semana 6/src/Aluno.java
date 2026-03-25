public class Aluno {

	private String nome;
	private int numero;
	private double notaTotal;
	private double porcentFalta;
	private int qntdAvaliacoes;
	private static final double NOTA_MAX = 25d;
	private static final double MAX_PROVAS = 4d;

	public Aluno(String nome, int numero) {
		this.nome = nome;
    	this.numero = numero;
    	this.notaTotal = 0;
    	this.qntdAvaliacoes = 0;
	}

	public double frequenciaAluno(int qntdFaltas) {
		
	}

	public double calculaNota(double nota) {
		if(nota > NOTA_MAX){	
			nota = NOTA_MAX;
		}
		qntdAvaliacoes++;
		if(qntdAvaliacoes <= MAX_PROVAS){
			notaTotal += nota;
		}
		return notaTotal;
	}

	public String toString() {
		
	}

}
