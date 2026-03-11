public class Pizza {
    int maxIngredientes;
    double precoBase;
    String descricao;
    double valorPorAdicional;
    int quantidadeDeIngredientes;

    Pizza(){
        this.maxIngredientes = 8;
        this.precoBase = 29;
        this.descricao = "";
        this.valorPorAdicional = 5;
        this.quantidadeDeIngredientes = 8;
    }
    public double valorAPagar(){
        return precoBase + (quantidadeDeIngredientes * valorPorAdicional);
    }
    public int adicionarIngredientes(int quantos){
        if(quantos > 8){
            IO.println("Maximo de 8 ingredientes podem ser adicionados");
        }
        return quantidadeDeIngredientes += quantos;
    }
    public String cumpomDeVenda(){
        descricao = String.format("====Cumpom de Venda====\nQuantidade de acrescimos: %d \nPreço Total dos acrescimos: R$%.2f x %d = R$%.2f \nPreço minimo: R$%.2f\nPreço total: R$%.2f\n", quantidadeDeIngredientes, valorPorAdicional, quantidadeDeIngredientes, (quantidadeDeIngredientes * valorPorAdicional),precoBase, valorAPagar());
        return descricao;
    }
}
