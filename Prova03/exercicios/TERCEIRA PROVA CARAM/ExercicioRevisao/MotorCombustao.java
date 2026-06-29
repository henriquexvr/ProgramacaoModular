public class MotorCombustao implements Motor {

	private int qntdLitros;
	private double autonomia;
	private double autonomiaMax;
	private double consumoMedio;

	public double calcularAutonomia() {
		return qntdLitros * consumoMedio;
	}

	public double calcularAutonomiaMaxima() {
		
	}

}
