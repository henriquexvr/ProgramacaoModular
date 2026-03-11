public class Produto {
    String descricao;
    double precoCusto;
    double margemLucro;
    
    public Produto (String desc, double precoCusto, double margemLucro){
        double margemMinima = 10;
        double margemMaxima = 50;

        
        descricao = desc;
        this.precoCusto = 0.01;
        this.margemLucro = 0.10;
        if(precoCusto > 0.01){
            this.precoCusto = precoCusto;
        }
        if(margemLucro > margemMinima && margemLucro <= margemMaxima){
            this.margemLucro = margemLucro;
        }
    }

    public double valorVenda(){
        return precoCusto * (1 + margemLucro/100);
    }

    public String toString(){
        return String.format("%s: R$ %.2", descricao, valorVenda());
    }
}
