public class ProdutoImportado extends Protudo{
    Double taxaAlfandega;
    ProdutoImportado(){
        super();
    }

    ProdutoImportado(String nome, Double preco, Double taxaAlfandega){
        super(nome, preco);
        this.taxaAlfandega = taxaAlfandega;
        if(taxaAlfandega < 0.0){
            this.taxaAlfandega = 0.0;
        }
    }

    Double precoTotal(){
        return preco + taxaAlfandega;
    }

    @Override
    String tagPreco() {
        return super.tagPreco() + "Taxa Alfandega: R$"+ taxaAlfandega;
    }
}
