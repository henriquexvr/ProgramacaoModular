void main(){
    Livro livro1 = new Livro("Machado", "Um livro de machados", 277);

    livro1.avaliar(5);
    livro1.avaliar(4);
    livro1.avaliar(3);
    livro1.avaliar(2);

    livro1.mediaAvaliacoes();

    livro1.estimativaLeitura(5);
    IO.print(livro1.toString());
}