public class ContaCorrente extends Conta {

	private double limite;
	private static final double TAXA = 0.03;

	public ContaCorrente(int id, double limite) {
		super(id);
		this.limite = limite;
	}

	@Override
	public double saque(double valor) {
		super.saque(valor);
		if(saldo() < 0){
			limite += saldo();
		}
		return valor;
	}

	@Override
	public double deposito(double valor) {
		if(saldo() < 0){
			valor += (valor * TAXA);
		}
		return super.deposito(valor);
	}

	@Override
	public String toString() {
		return super.toString() + String.format("\nLimite: %.2f", limite);
	}
}
