import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ConsultaAgendada extends Consulta {

	private static final double DESCONTO = 0.2;

	public ConsultaAgendada(String paciente, String docIdentidade, LocalDateTime dataHora) {
		
	}
	
	@Override
	public double calcularValor() {
		double desconto = 0;
		if(validarAntecedencia){
			desconto = DESCONTO;
		}
		return desconto;
	}


	private boolean validarAntecedencia() {
        long diasAntecedencia = ChronoUnit.DAYS.between(
            this.dataAgendamento.toLocalDate(), 
            this.getDataHora().toLocalDate()
        );

    }
}
