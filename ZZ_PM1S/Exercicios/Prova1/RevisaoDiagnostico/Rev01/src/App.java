public class App {
    void main(){
        String frase, escolha;
        do{
            frase = IO.readln("Escreva uma frase: ");
            for(int i = frase.length(); i > 0; i--){
                IO.print(frase.charAt(i - 1));
            }
            escolha = IO.readln("\nQuando desejar sair, escreva a palavra 'Fim': ");
        }while(escolha == "Fim");
    }
}
