int receberTempoDuracao(){
    return Integer.parseInt((IO.readln("Escreva o tempo de duracao do seu album: ")));
}

int calcularQntdDiscos(int duracaoAlbum, int TEMPO_POR_DISCO){
    return (duracaoAlbum + TEMPO_POR_DISCO - 1) / TEMPO_POR_DISCO;
}


double calcularValor(double PRECO_POR_DISCO, int qntdDiscos){
    return (PRECO_POR_DISCO * qntdDiscos);
}

String formatarValores(double valorConfeccao, double valorTotal){
    return String.format("Valor confecaao p/ copia do album: R$%.2f\nValor total a pagar: R$%.2f", valorConfeccao, valorTotal);
}
void main(){
    int TEMPO_POR_DISCO = 24;
    double PRECO_POR_DISCO = 35.0;
    double PRECO_SERVICOS = 420.0;

    int duracaoAlbum = receberTempoDuracao();
    int qntdDiscos = calcularQntdDiscos(duracaoAlbum, TEMPO_POR_DISCO);
    double valorConfeccao = calcularValor(PRECO_POR_DISCO, qntdDiscos);

    double valorTotal = valorConfeccao + PRECO_SERVICOS;

    IO.println(formatarValores(valorConfeccao, valorTotal));
}