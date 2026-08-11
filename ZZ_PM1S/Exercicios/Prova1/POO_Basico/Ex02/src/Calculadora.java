public class Calculadora {
	private double valor1;
	private double valor2;
	private double resultado;
	
	public Calculadora(double valor1, double valor2) {
		this.valor1 = valor1;
		this.valor2 = valor2;
	}

	public double somar() {
		resultado = valor1 + valor2;
		return resultado;
	}

	public double subtrair() {
		resultado = valor1 - valor2;
		return resultado;
	}

	public double multiplicar() {
		resultado = valor1 * valor2;
		return resultado;
	}

	public double dividir() {
		resultado = valor1 / valor2;
		return resultado;
	}

	public String toString() {
		return String.format("%.2f", resultado);
	}

}
