public int parseInt(String value){

    return Integer.parseInt(value);
}

public boolean validarMes(int mes, int dia){
    boolean estaCerto = false;
    switch(mes){
        case 1, 3, 5, 7, 8, 10, 12 -> {
            if (dia >= 1  && dia <= 31){
                estaCerto = true;
            }
        }
        case 4, 6, 9, 11 -> {
            if (dia >= 1  && dia <= 30){
                estaCerto = true;
            }
        }
        case 2 -> {
            if (dia >= 1  && dia <= 28 || dia == 29){
                estaCerto = true;
            }
        }
    }
    return estaCerto;
}

void main() {
    int dia;
    int mes;
    int ano;
    String data;

    int parada = 0;
    do {
        data = IO.readln("Escreva data do seu nascimento seguindo esse formato: DD/MM/AAAA: ");

        String[] dataSplit = data.split("/");
        dia = parseInt(dataSplit[0]);
        mes = parseInt(dataSplit[1]);
        ano = parseInt(dataSplit[2]);

        parada = 0;
        if(!(validarMes(mes, dia))){
            parada = 1;
            IO.println("Data invalida! Escreva novamente");
        }
    }while (parada == 1);

    IO.println("Data valida! Fim do programa.");

}
