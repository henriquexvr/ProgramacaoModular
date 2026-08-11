public class VideoAula {
    private String titulo;
    private int duracao;

    public VideoAula(String titulo, int duracao) {
        this.titulo = titulo;
        this.duracao = duracao;
    }

    public int duracaoAula() {
        return duracao;
    }
}
