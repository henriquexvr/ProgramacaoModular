public class Livro {
    String titulo;
    String autor;
    int numPags;
    public void registrar(String titulo, String autor, int numPags){
        this.titulo = titulo;
        this.autor = autor;
        this.numPags = numPags;
    }
    public String tamanhoLivro(int numPags){
        String tamanho = " ";
        if(numPags <= 90){
            tamanho = "Curto";
        }else if(numPags >= 91 && numPags <= 200){
            tamanho = "Médio";
        }else{
            tamanho = "Longo";
        }
        return tamanho;
    }
    public double avaliacaoMedia(int qntdAvaliacoes){
        int avaliacao = 0, totalAvaliacao = 0;
        for (int i = 0; i < qntdAvaliacoes; i++) {
            do {
               IO.println("Usuario "+ (i+1)+":"); 
               IO.println("0 a 5 estrelas");
               String avaliacaoStr = IO.readln("Quantas estrelas voce da para o livro? ");
                avaliacao = Integer.parseInt(avaliacaoStr); 
                if(avaliacao < 0 || avaliacao > 5){
                    IO.println("Escreva um valor entre 0 e 5 estrelas. ");
                }
            } while(avaliacao < 0 || avaliacao > 5); 
            totalAvaliacao += avaliacao;   
        }
        return (double)totalAvaliacao / qntdAvaliacoes;
    }
}