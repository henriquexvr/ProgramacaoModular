public class Exercicio2 {
    static void BasesRetangulo(int largura){
        for(int i = 0; i < largura; i++){
            IO.print("X");
        }
        IO.println();
    }
    static void LateraisRetangulo(int largura, int altura){
        for(int i = 0; i < (altura - 2); i++){
            IO.print("X");

            for(int j = 0; j < (largura-2); j++){
                IO.print(" ");
            }
            IO.println("X");    
        }
    }    
    public static void main(String[] args){
        int largura, altura;
        IO.println("=====Escreva as dimensoes do seu retangulo=====");
        do{
            String larguraStr = IO.readln("Escreva qual a largura: ");
            String alturaStr = IO.readln("Escreva qual a altura: ");
            largura = Integer.parseInt(larguraStr);
            altura = Integer.parseInt(alturaStr);
            if(largura < 2 && altura < 2){
                IO.println("Escreva valores maiores ou iguais a 2.\n");
            }
        }while(largura < 2 && altura < 2);    
        BasesRetangulo(largura);  
        LateraisRetangulo(largura, altura);
        BasesRetangulo(largura);  
    }
}
