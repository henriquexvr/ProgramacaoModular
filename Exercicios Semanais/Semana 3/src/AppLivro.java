void main(){
    int qtndAvalicoes, numeroPags;
    Livro livro1 = new Livro();
    String tituloLivro = IO.readln("\nQual o titulo livro? ");
    livro1.titulo = tituloLivro;
    String nomeAutor = IO.readln("Qual o nome do autor livro? ");
    livro1.autor = nomeAutor;
    String numeroPaginasStr = IO.readln("Quantas paginas tem o livro? ");
    livro1.numPags = numeroPags = Integer.parseInt(numeroPaginasStr);
    String qtndAvaliacoesSrt = IO.readln("Quantas pessoas leram esse livro? ");
    qtndAvalicoes = Integer.parseInt(qtndAvaliacoesSrt);
    IO.println("Tamanho do livro: "+ livro1.tamanhoLivro(numeroPags));
    IO.println("Media de avaliacoes: "+livro1.avaliacaoMedia(qtndAvalicoes) +" estrelas");
}