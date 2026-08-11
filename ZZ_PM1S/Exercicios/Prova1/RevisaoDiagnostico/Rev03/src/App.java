public int[] preencherVetor(){
    int[] vetor = new int[6];
    for (int i = 0; i < vetor.length; i++) {
        String vetorStr = IO.readln("Escreva um numero: ");
        vetor[i] = Integer.parseInt(vetorStr);
    }
    return vetor;
}

public int[] inverterVetor(int[] vetor){
    int[] vetor2 = new int[6];
    int j = 0;
    for (int i = vetor.length - 1; i >= 0; i--) {    
        vetor2[j] = vetor[i];
        j++;    
    }
    return vetor2;
}

void main(){
    int[] vetor = new int[6];
    int[] vetor2 = new int[6];
    vetor = preencherVetor();
    vetor2 = inverterVetor(vetor);

    for (int i = 0; i < vetor2.length; i++){
        IO.print(vetor2[i]);
        if(i >= 0 && i <= 4){
            IO.print("-");
        }
    }
}