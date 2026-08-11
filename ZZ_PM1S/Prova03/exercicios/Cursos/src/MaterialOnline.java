public class MaterialOnline implements IMaterial{
    private int duracaoAula;
    private String nome;
    
    public int duracaoAula(){
        return duracaoAula;
    }

    @Override
    public double valorTotal(){
        return 0;
    }
}
