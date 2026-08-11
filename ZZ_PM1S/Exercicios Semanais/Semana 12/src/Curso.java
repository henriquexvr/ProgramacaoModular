public abstract class Curso {
    private String nome;
    protected double valorHora;
    private int cargaHoraria;

    public Curso(String nome, int cargaHoraria, double valorHora) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.valorHora = valorHora;
    }

    public abstract double valorTotal();

    public abstract double valorAulas();

    public int qualCargaHoraria() {
        return cargaHoraria;
    }
}
