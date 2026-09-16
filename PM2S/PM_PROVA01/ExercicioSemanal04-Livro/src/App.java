import java.lang.reflect.Array;
import java.util.ArrayList;

public void avaliar(List<Livro> livros){
    String nomeLivro = IO.readln("Escreva o nome do livro: ");
    int nota;
    Livro livroEncontrado = null;
    for (Livro l : livros) {
        if(l.getTitulo().equals(nomeLivro)){
            livroEncontrado = l;
        }
    }
    if(livroEncontrado != null){
        IO.println("Livro encontrado!");
        nota = (Integer.parseInt(IO.readln("Avalie o livro (0 a 5): ")));
        livroEncontrado.avaliarLivro(nota);
    }

}

void main(){
    Livro livro1 = new Livro("Livro dos Livros", "Isabelle Araujo", 500);
    Livro livro2 = new Livro("Livro ruim", "Henrique", 120);

    List<Livro> livros = new ArrayList<>();

    livros.add(livro1);
    livros.add(livro2);

    avaliar(livros);

    for (Livro livro : livros) {
        IO.println(livro.dadosLivro());
    }
}