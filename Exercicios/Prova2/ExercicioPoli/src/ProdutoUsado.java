import java.time.LocalDate;

public class ProdutoUsado extends Protudo{
    LocalDate dataFabricacao;

    public ProdutoUsado() {
        super();
    }

    public ProdutoUsado(String nome, Double preco, LocalDate dataFabricacao) {
        super(nome, preco);
        this.dataFabricacao = dataFabricacao;
        if(dataFabricacao.isBefore(LocalDate.now())){
            dataFabricacao = LocalDate.now();
        }
    }

    @Override
    String tagPreco() {
        return super.tagPreco() + "Data de Fabricação: " + dataFabricacao;
    }
}
