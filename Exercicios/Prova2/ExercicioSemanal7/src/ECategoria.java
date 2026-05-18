public enum ECategoria {

    public int PROFISSIONAL = 30;
    public int ESTAGIO = 45;
    public int EXTENSAO = 20;
    private int horasAtividade;

     ECategoria(int horas) {
        horasAtividade = horas;
    }
    public int calcularCreditos(double cargaHoraria) {
        return (int) (cargaHoraria / horasAtividade);
    }
}