public class Protudo {
    String nome;
    Double preco;

    Protudo(){}

    Protudo(String nome, Double preco){
        this.nome = nome;
        this.preco = preco;
        if(preco < 0.0){
            this.preco = 0.0;
        }
    }

    String tagPreco(){
        return String.format("Nome: %s\nPreço: R$%.2f", nome, preco);
    }
}
