public class App {
    void main(){
        Produto prod1 = new Produto("Caderno", 10, 25);
        IO.println(prod1.toString());

        Produto prod2 = new Produto("Bolinha", 10, 200);
        IO.println(prod2.toString());
    }
}
