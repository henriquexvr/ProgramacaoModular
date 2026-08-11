public class MotorHibrido implements Motor {

	private MotorEletrico eletrico;
	private MotorCombustao combustao;
	private boolean usandoEletrico;

	public double calcularAutonomia() {
		
	}

	public double calcularAutonomiaMaxima() {
		return eletrico.calcularAutonomiaMaxima() + combustao.calcularAutonomiaMaxima();
	}

	public boolean mudarMotor() {
	
	}

}
