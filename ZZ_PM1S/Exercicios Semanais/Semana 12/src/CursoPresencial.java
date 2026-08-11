public class CursoPresencial extends Curso {
    private static final int CARGA_MIN = 30;
    private static final int CARGA_MAX = 120;
    private MaterialDidatico material;

    public CursoPresencial(String nome, int horas, double valorHora, MaterialDidatico material) {
        super(nome, horas, valorHora);
        if (horas < CARGA_MIN || horas > CARGA_MAX) {
            throw new IllegalArgumentException("Carga horaria invalida. Minimo: 30, Maximo: 120");
        }
        this.material = material;
    }

    @Override
    public double valorTotal() {
        return valorAulas() + material.valorTotal();
    }

    @Override
    public double valorAulas() {
        return qualCargaHoraria() * valorHora;
    }
}
