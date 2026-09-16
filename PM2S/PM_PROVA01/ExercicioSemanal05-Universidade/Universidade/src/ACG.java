public class ACG {
    private String descricao;
    private String tipoAtividade;
    private int cargaHoraria;
    private static final int CARGA_PROFISSIONAIS = 45;
    private static final int CARGA_ESTAGIO = 30;
    private static final int CARGA_EXTENSAO = 20;
    private static final int MAX_CREDITOS = 4;
    
    public ACG(String descricao, String tipoAtividade, int cargaHoraria) {
        this.descricao = descricao;
        this.tipoAtividade = tipoAtividade;
        this.cargaHoraria = cargaHoraria;
        if(cargaHoraria < 0){
            this.cargaHoraria = 1;
        }
    }
    public int gerarCreditos(){
        int creditos = 0;
        int horasPorCredito = switch(tipoAtividade){
            case "Profissional" -> CARGA_PROFISSIONAIS;
            case "Estagio" -> CARGA_ESTAGIO;
            case "Extensão" -> CARGA_EXTENSAO;
            default -> 0;
        };

        creditos = (int) cargaHoraria / horasPorCredito;
        if(creditos > MAX_CREDITOS;){
            creditos = MAX_CREDITOS;
        }
        return  creditos;
    }

    public String tipoAtividade(){
        return tipoAtividade;
    }
}
