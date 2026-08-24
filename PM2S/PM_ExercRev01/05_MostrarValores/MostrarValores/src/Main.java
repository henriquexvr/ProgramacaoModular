public int parseInt(String s){
    return Integer.parseInt(s);
}

public int[] preencherNumeros(int qntd){
    int[] numeros = new int[qntd];
    for (int i = 0; i < qntd; i++) {
        numeros[i] = parseInt(IO.readln("Escreva o numero "+ (i+1)+ ": "));
    }
    return numeros;
}

public int soma(int[] numeros){
    int soma = 0;
    for (int i = 0; i < numeros.length; i++) {
        soma += numeros[i];
    }
    return soma;
}


public void seila(int[] n){

}

public String mostrarResultados(int[] n, double media) {
    int pares = 0;
    int impares = 0;
    int maioresMedia = 0;
    int menosMedia = 0;
    for (int i = 0; i < n.length; i++) {
        if(n[i] % 2 == 0){
            pares++;
        }else{
            impares++;
        }

        if(n[i] < media){
            maioresMedia++;
        }else{
            menosMedia++;
        }
    }
    return String.format("\nQuantidade numeros pares: %d\nQuantidade numeros impares: %d\nMedia: %.2f \nNumeros maiores que a media: %d\nNumeros menores que a media: %d",
                        pares, impares, media, maioresMedia, menosMedia);
}


void main(){
    int qntdNumeros = parseInt(IO.readln("Escreva quantidade de numeros: "));
    int[] numeros = preencherNumeros(qntdNumeros);
    int soma = soma(numeros);
    double media = (double) soma(numeros) / qntdNumeros;

    IO.println("Soma dos numeros: "+ soma + mostrarResultados(numeros, media));

}