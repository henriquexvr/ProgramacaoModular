public class Aluno {

	private String nome;
	private int numero;
	private double notaTotal;
	//private double porcentFalta;
	private int qntdAvaliacoes;

	public Aluno(String nome, int numero) {
		this.nome = nome;
    	this.numero = numero;
    	this.notaTotal = 0;
    	this.qntdAvaliacoes = 0;
	}

	//public double frequenciaAluno(int qntdFaltas) {
		
	//}

	public double calculaNota(double nota) {
		if(nota > 25d){	
			nota = 25d;
		}
		qntdAvaliacoes++;
		if(qntdAvaliacoes <= 4){
			notaTotal += nota;
		}
		return notaTotal;
	}

	//public String toString() {
		
	//}

}
