public class Carro {

	private String placa;
	private int velocidade;
	private static final int VELOCIDADE_MAX = 120;
	private static final int VELOCIDADE_MIN = 0;
	private static final int MUDAR_VELOCIDADE = 5;

	public Carro(String placa) {
		this.placa = placa;
		if(this.placa.length() != 7){
			this.placa = "ABC1D23";
		}
		velocidade = 0;
	}

	public int acelerar() {
		int acelerar = MUDAR_VELOCIDADE;
		velocidade += acelerar;
		if(velocidade > VELOCIDADE_MAX){
			velocidade = VELOCIDADE_MAX;
		}
		return velocidade;
	}

	public int freiar() {
		int freiar = MUDAR_VELOCIDADE;
		velocidade -= freiar;
		if(velocidade < VELOCIDADE_MIN){
			velocidade = VELOCIDADE_MIN;
		}
		return velocidade;
	}

	public String mostrarVelocidade() {
		return String.format("Velocidade atual: %d Km/h", velocidade);
	}
}