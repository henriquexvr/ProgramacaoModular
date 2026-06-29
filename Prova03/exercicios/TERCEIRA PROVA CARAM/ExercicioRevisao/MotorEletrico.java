public class MotorEletrico implements Motor {

	private int cargaBateria;
	private double autonomiaMax;
	private double autonomia;
	private double consumoBateria;

	public double calcularAutonomia() {
		return cargaBateria * consumoBateria;
	}

	public double calcularAutonomiaMaxima() {
		
	}

}
