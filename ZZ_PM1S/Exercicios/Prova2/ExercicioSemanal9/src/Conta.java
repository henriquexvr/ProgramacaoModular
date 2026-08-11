public class Conta {

	private int ID;
	private double saldo;

	public Conta(int id) {
		this.ID = id;
		saldo = 0;
	}

	public double saque(double valor) {
		saldo += valor;
		return valor;
	}

	public double deposito(double valor) {
		saldo -= valor;
		return valor;
	}

	public double saldo() {
		return saldo;
	}

	@Override
	public String toString() {
		return String.format("ID Conta: %d\nSaldo: %d", ID, saldo());
	}

}
