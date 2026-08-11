public class App {
    public static void main(String[] args) throws Exception {
        Conta conta1 = new Conta("12345");

        conta1.deposito(100);
        IO.println(conta1.toString());
        conta1.saque(50);
        IO.println(conta1.toString());
        conta1.saque(100);
        IO.println(conta1.toString());
    }
}
