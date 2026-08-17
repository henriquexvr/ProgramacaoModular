import java.util.LinkedList;

int lerInteiros(){
    return Integer.parseInt(IO.readln("Escreva numeros inteiros: "));
}

int[] lerNumeros(int qntdNumeros){
    int[] numeros  = new int[qntdNumeros];
    for (int i = 0; i < numeros.length; i++) {
        numeros[i] = lerInteiros();
    }
    return numeros;
}

double calcularMedia(int[] numeros, int qntdNumeros){
    int somaTotal = 0;
    for (int i = 0; i < numeros.length; i++) {
        somaTotal += numeros[i];
    }
    return (double) somaTotal / qntdNumeros;
}


int[] acimaDamedia(double media, int[] numeros){
    int contador = 0;
    int[] acimDaMedia = new int[numeros.length]; //Cria um vetor no maior tamanho possivel e depois incrementa com o contador
    
    for (int i = 0; i < numeros.length; i++) {
        if(numeros[i] > media){
            acimDaMedia[contador] = numeros[i];
            contador++;
        }
    }
    return acimDaMedia;
}


    String resultados(double media, int[] numAcimaMedia){
        String resultado = "A media dos numeros digitados é: "+media+"\nO numeros que estao acima da media sao: ";

        for (int i = 0; i < numAcimaMedia.length; i++) {
            if(numAcimaMedia[i] != 0){
                resultado += numAcimaMedia[i]+" ";
            }
        } 
    return resultado; 
}
void main(){
    IO.println("Escreva a quantidade valores que vao ter: ");
    int qntdNumeros = lerInteiros();
    int[] numeros = lerNumeros(qntdNumeros);
    double media = calcularMedia(numeros, qntdNumeros);
    int[] numAcimaMedia = acimaDamedia(media, numeros);


    IO.print(resultados(media, numAcimaMedia));

    

}