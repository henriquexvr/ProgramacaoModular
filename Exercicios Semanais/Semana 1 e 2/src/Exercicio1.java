public class Exercicio1 {
    public static void main(String[] args){
        int vector[], soma = 0, pares = 0, impares = 0, maiorQMedia = 0, menosQMetadeMedia = 0;
        String numero = IO.readln("Quantos numeros você vai digitar? ");
        int n = Integer.parseInt(numero);
        vector = new int[n];
        double media, metadeMedia;        
        for(int i = 0; i < vector.length; i++){
            String numeros = IO.readln("Escreva o numero "+ (i+1) +": ");
            int N = Integer.parseInt(numeros);
            soma += N;
            vector[i] = N;
            if(N % 2 == 0){
                pares++;
            }else{
                impares++;
            }
        }
        media = (double) soma / n;
        metadeMedia = media / 2;
        for(int i = 0; i < vector.length; i++){  
            if(vector[i] > media){
                maiorQMedia++;
            }
            if(vector[i] < metadeMedia){
                menosQMetadeMedia++;
            } 
        }        
        IO.println("Soma total: "+ soma);
        IO.println("Media: "+ media);
        IO.println("Pares: "+ pares);
        IO.println("Impares: "+ impares);
        IO.println("Mais que a media: "+ maiorQMedia);
        IO.println("Menos que metade da media: "+ menosQMetadeMedia);
    }
}