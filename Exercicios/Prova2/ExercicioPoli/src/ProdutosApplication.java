import java.util.ArrayList;
import java.util.List;

public class ProdutosApplication {
    void main(){
        List<Protudo> protudos = new ArrayList<>();
        int numeroProtudos;
        numeroProtudos = Integer.parseInt(IO.readln());

        for (int i = 0; i < numeroProtudos; i++) {
            String escolha = IO.readln("Produto comum (C), Produto Usado (U), Produto Importado(I)?");
            switch (escolha){
                case "C" ->
                case "U" -> 
                case "I" ->

            }
        }
    }



}
