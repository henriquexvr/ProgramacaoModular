public int[] preencherVetor(int tamanho){
    int[] vetor = new int[tamanho];
    for (int i = 0; i < vetor.length; i++) {
        String vetorStr = IO.readln("Escreva um numero: ");
        vetor[i] = Integer.parseInt(vetorStr);
    }
    return vetor;
}

public int[] somaDePares(int[] vetorOriginal){
    int tamanhoNovoVetor = tamanhoDoVetor(vetorOriginal);
    int[] novoVetor = new int[tamanhoNovoVetor];
    int indice = 0;

    for (int i = 0; i < vetorOriginal.length; i += 2) {
        if(i + 1 < vetorOriginal.length){
            int somaDePares = vetorOriginal[i + 1] + vetorOriginal[i];
            novoVetor[indice] = somaDePares;
        }else{
           novoVetor[indice] = vetorOriginal[i] * 2; 
        }
        indice++;           
    }
    return novoVetor;
}

private int tamanhoDoVetor(int[] vetorOriginal){
        int tamanhoNovoVetor = vetorOriginal.length/2;
        if(vetorOriginal.length % 2 != 0){
            tamanhoNovoVetor++;
        }
        
    return tamanhoNovoVetor;
}


void main(){
    String tamanhoVetorStr = IO.readln("Escreva o tamanho do vetor: ");
    int tamanhoVetor = Integer.parseInt(tamanhoVetorStr);
    int[] vetor = preencherVetor(tamanhoVetor);
    vetor = somaDePares(vetor);
    for (int i = 0; i < vetor.length; i++) {
        IO.print(vetor[i]);
        if (i != vetor.length - 1) {
            IO.print("-");
        }
    }
}
