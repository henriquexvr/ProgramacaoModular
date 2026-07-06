public class MaterialDidatico implements IMaterial{
    private String nome;
    private double valor;

    @Override
    public double valorTotal(){
        return valor;
    }
}
