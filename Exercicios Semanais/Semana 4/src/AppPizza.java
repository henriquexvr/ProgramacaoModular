public class AppPizza {
    public static void main(String[] args) throws Exception {
        int qntdAdicionais;
        Pizza pizza1 = new Pizza();
        do{
            qntdAdicionais = Integer.parseInt(IO.readln("\nQuantidade de adicionais na sua pizza (R$5,00 p/ adicional): "));
            pizza1.adicionarIngredientes(qntdAdicionais);
        }while(qntdAdicionais > 8);
        IO.println(pizza1.cumpomDeVenda());
    }
}
