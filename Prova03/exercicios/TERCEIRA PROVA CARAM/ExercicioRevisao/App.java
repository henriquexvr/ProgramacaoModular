import java.util.ArrayList;
import java.util.List;

public class App {

	private List<Veiculo> veiculos = new ArrayList<>();
	private List<Roteiro> roteiros = new ArrayList<>();

	public int cadastroVeiculo(Veiculo v) {
		if(v == null){
			throw new IllegalArgumentException("Veiculo não foi criado corretamente");
		}
		veiculos.add(v);
		return veiculos.size();
	}

	public void cadastroRoteiro(Roteiro r) {
		
	}

	public Veiculo veiculoIndicado(Roteiro roteiro) {
	return veiculos.stream().filter(veiculo -> veiculo.motorUtilizado().calcularAutonomia() >= roteiro.distancia())
							.findFirst()
							.orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado"));
	}

}
