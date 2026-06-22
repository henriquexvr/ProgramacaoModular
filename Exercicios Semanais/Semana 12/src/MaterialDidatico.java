import java.util.List;

public class MaterialDidatico {
    private String nome;
    private double valor;
    private List<String> conteudo;

    public MaterialDidatico(String nome, double valor, List<String> conteudo) {
        this.nome = nome;
        this.valor = valor;
        this.conteudo = conteudo;
    }

    public double valorTotal() {
        return valor;
    }
}
