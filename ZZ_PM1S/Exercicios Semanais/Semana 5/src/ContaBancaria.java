public class ContaBancaria {
    private String identificacao;
    private String CPF;
    private Double saldo;
    private Double limite;

    public ContaBancaria(){
        identificacao = "00000";
        CPF = "000.000.000-00";
        saldo = 0.0;
        limite = saldo / 2;
    }

    public Double deposito(Double valor){ 
        if(saldo < 0){
            saldo += valor - (valor * 0.03);
        }else{
            saldo += valor;
        }
        return saldo;
    }
}
