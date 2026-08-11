public enum ECategoria {
    PROFISSIONAL(30), //1 Credito / 30h
    ESTAGIO(45), //1 Credito / 30h
    EXTENSAO(20); //1 Credito / 20h

    private final int horasCategoria;

    ECategoria(int horasCategoria){
        this.horasCategoria = horasCategoria;
    }

    public int cargaHoraria(){
        return horasCategoria;
    }

    public int calculaCreditos(int horasRealizadas){
        return horasRealizadas / this.horasCategoria;
    }
}
