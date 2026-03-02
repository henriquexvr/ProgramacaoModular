public class Produto {
    String descricao;
    double precoCusto;
    double margemLucro;
    public Produto (String desc, double precoCusto, double margemLucro){
        descricao = desc;
        this.precoCusto = 0.01;
        this.margemLucro = 0.10;
        if(precoCusto > 0.01){
            this.precoCusto = precoCusto;
        }
        if(margemLucro > 10 && margemLucro <= 50){
            this.margemLucro = margemLucro/100;
        }
    }

    public double valorVenda(){
        return precoCusto + precoCusto*margemLucro;
    }

    public String toString(){
        return String.format("%s: R$ %.2", descricao, valorVenda());
    }
}
