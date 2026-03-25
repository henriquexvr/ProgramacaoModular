public class Aluno {

	private String nome;
	private int numero;
	private double notaTotal;
	private int qntdAulas;
	private int qntdAvaliacoes;
	private double porcentFalta;
	private static final double NOTA_MAX = 25d;
	private static final int MAX_AVALIACOES = 4;

	public Aluno(String nome, int numero) {
		this.nome = nome;
		this.numero = numero;
    	this.qntdAulas = 0;
		this.qntdAulas = 0;
    	notaTotal = 0;
    	qntdAvaliacoes = 0;
	}

	//public double frequenciaAluno(int qntdFaltas) {
		
	//}

	public double calculaNota(double nota) {
		if(nota > NOTA_MAX){	
			nota = NOTA_MAX;
		}
		qntdAvaliacoes++;
		if(qntdAvaliacoes <= MAX_AVALIACOES){
			notaTotal += nota;
		}
		return notaTotal;
	}

	public boolean estaAprovado(){
		return notaTotal >= 60;
	}

	//public String toString() {
		
	//}

}
