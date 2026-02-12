public class Exemplo1{
    public static void main(String[] args) {
        int vector[], soma = 0;
        String numero = IO.readln("Quantos numeros você vai digitar?");
        int n = Integer.parseInt(numero);
        vector = new int[n];
        double media;
        
        for(int i = 0; i < n; i++){
            String numeros = IO.readln("Escreva o numero "+ (i+1) +":");
            int N = Integer.parseInt(numeros);
            soma += N;
            vector[i] = N;
        }
        IO.println("Soma total: "+ soma);
        media = (double) soma / n;
        IO.println("Media: "+ media);
    }
}