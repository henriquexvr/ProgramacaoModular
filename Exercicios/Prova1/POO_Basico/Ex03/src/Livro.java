public class Livro {
	private String titulo;
	private String autor;
	private int numeroPags;
	private double avaliacaoMedia;
	private int totalEstrelas;
	private int qntdAvaliacoes;
	private String tempoEstimado;
	private static final int MIN_PAGS = 49;
	private static final int MIN_ESTRELAS = 0;
	private static final int MAX_ESTRELAS = 5;

	public Livro(String autor, String titulo, int numPags) {
		this.autor = autor;
		this.titulo = titulo;
		numeroPags = numPags;
		if(numeroPags <= 0){
			numeroPags = MIN_PAGS;
		}
	}

	public double avaliar(int numEstrelas) {
		if(numEstrelas >= MIN_ESTRELAS && numEstrelas <= MAX_ESTRELAS){
			totalEstrelas += numEstrelas;
			qntdAvaliacoes++;
		}
		return numEstrelas;
	}

	public double mediaAvaliacoes() {
		avaliacaoMedia = (double)totalEstrelas / qntdAvaliacoes;
		return avaliacaoMedia;
	}

	public String estimativaLeitura(int tempoPagina) {
    	int tempoTotalMin = tempoPagina * numeroPags;
    	int horas = tempoTotalMin / 60;
    	int minutos = tempoTotalMin % 60;
		tempoEstimado = String.format("%d horas e %d minutos", horas, minutos);
    	return tempoEstimado;
	}

	public String toString() {
		return String.format("Livro: %s\nAutor: %s\nTamanho: %d Paginas \nAvaliacao: %.2f Estrelas\nEstimativa de tempo: %s", titulo, autor, numeroPags, avaliacaoMedia, tempoEstimado);
	}
}
