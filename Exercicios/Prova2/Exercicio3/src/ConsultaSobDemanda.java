import java.time.LocalDateTime;


public class ConsultaSobDemanda extends Consulta {

	private static final double ADICIONAL = -0.1;

	public ConsultaSobDemanda(String paciente, String docIdentidade, LocalDateTime dataHora) {
		super(String paciente, String docIdentidade, LocalDateTime diaConsulta);
	}

	@Override
	public double calcularValor() {
		return ADICIONAL;
	}

}
