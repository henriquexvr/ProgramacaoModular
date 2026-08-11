import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Produto {

	private LocalDate dataVencimento;
	private static int DIAS_VENCIMENTO = 7;
	private static double DESCONTO = 0.5;

	public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, LocalDate dataVencimento) {
		super(descricao, precoCusto, margemLucro);
		this.dataVencimento = dataVencimento;
	}

	public double calcularDesconto() {
		double desconto = 0;
		if(verificarValidade() <= DIAS_VENCIMENTO){
			desconto = super.valorVenda() * DESCONTO;
		}
		return desconto;
	}
	private int verificarValidade() {
		LocalDate data = LocalDate.now();
		return (int) ChronoUnit.DAYS.between(data, dataVencimento);
	}

	@Override
	public double valorVenda() {
		
	}

}
