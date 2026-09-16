public class Livro {
    private String titulo;
    private String autor;
    private int qntdPags;
    private int[] tamanhoPags = {90, 200};
    private int somaAvaliacoes;
    private int qntdAvaliacoes;


    public Livro(String titulo, String autor, int qntdPags) {
        this.titulo = titulo;
        this.autor = autor;
        this.qntdPags = qntdPags;
        if(qntdPags < 0){
            this.qntdPags = 1;
        }
    }

    public String tamanhoLivro(){
        String tamanho = "Curto";
        
        if(qntdPags >= tamanhoPags[0]){
            tamanho = "Medio";
        }
        if(qntdPags > tamanhoPags[1]){
            tamanho = "Longo";
        }
        return tamanho;
    }

    public double avaliarLivro(int nota){
        if(nota >= 0 && nota <= 5){
            somaAvaliacoes += nota;
            qntdAvaliacoes++;
        }
        return calcularAvalicaoMedia();
    }
    
    private double calcularAvalicaoMedia(){
        return (double) somaAvaliacoes / qntdAvaliacoes;
    }

    public String dadosLivro(){
        return String.format("Titulo: %s \nAutor: %s \nTamanho: %s\nAvaliacao: %.2f", titulo, autor, tamanhoLivro(), calcularAvalicaoMedia());
    }

        public String getTitulo() {
            return titulo;
        }
    }


