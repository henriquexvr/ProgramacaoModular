public class ACG {

    private static int MAX_CREDITOS = 4;
    private String descricao;
    private double cargaHoraria;
    private int tipo;
    private ECategoria categoria;

    public int creditos() {
        int totalCreditos = categoria.calcularCreditos(cargaHoraria);
        if(totalCreditos > MAX_CREDITOS){
            totalCreditos = MAX_CREDITOS;
        }
        return  totalCreditos;
    }
}