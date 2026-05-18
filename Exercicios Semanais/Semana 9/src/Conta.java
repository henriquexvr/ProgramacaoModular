public class Conta {

	private double saldo;
	private String id;

	public Conta(String id) {
		this.id = id;
		saldo = 0;
	}

	public double saque(double valor) {
		return saldo -= valor;
	}

	public double deposito(double valor) {
		return saldo += valor;
	}
	@Override
	public String toString() {
		return String.format("ID: %s\nSaldo: R$%.2f\n", id, saldo);
	}

}
