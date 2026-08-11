public abstract class Curso {
    protected static int CARGA_MINIMA = 30; //horas
    protected static int CARGA_MAXIMA = 120; //horas
    private String nome;
    private int codigo;
    protected int cargaHoraria;
    protected double valorHora;
    private IMaterial material;


    public Curso(String nome, int codigo, int cargaHoraria, double valorHora, IMaterial material) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        if(cargaHoraria < CARGA_MINIMA || cargaHoraria > CARGA_MAXIMA){
            throw new IllegalArgumentException("O curso deve ter carga minima de 30horas e carga maxima de 120 horas");
        }
        this.valorHora = valorHora;
        this.material = material;
    }

    protected abstract double valorTotal();
    protected abstract double valorAulas();
    protected abstract int incluirMaterial(IMaterial m);

}
