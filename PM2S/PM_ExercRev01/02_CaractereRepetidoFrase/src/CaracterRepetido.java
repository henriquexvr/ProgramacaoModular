
int contarCaracterRepetido(String frase, String caracterEscolhido){
    int qntdRepeticoes = 0;

    for(int i = 0; i < frase.length() ; i++){
        if(frase.charAt(i) == caracterEscolhido.charAt(0)){ //charAt retorna o caractere na posição indicada, se o caracter for igual ao escolhido, incrementa 1
            qntdRepeticoes++;
        }
    }
    return qntdRepeticoes;
}

void main(){
    String frase;
    String caracterEscolhido;

    frase = IO.readln("Escreva uma frase: ");
    caracterEscolhido = IO.readln("Escreva a letra a ser contada: ");

    IO.println(String.format("O caracter %s repete %d vezes na frase escrita.", caracterEscolhido, contarCaracterRepetido(frase, caracterEscolhido)));
}