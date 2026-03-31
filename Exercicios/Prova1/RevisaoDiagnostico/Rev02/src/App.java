void main(){
    String numeroStr;
    IO.println("\nBem vindo a multiplicação por 11 (Com numeros de dois digitos)");
    do{
        numeroStr = IO.readln("Escreva um numero de dois digitos: ");
        if(numeroStr.length() != 2){
            IO.println("O numero tem que ter dois digitos");
        }
    }while(numeroStr.length() != 2);
    int num1, num2, num3;
    num1 = Integer.parseInt(numeroStr.substring(0, 1));
    num2 = Integer.parseInt(numeroStr.substring(1,2));
    num3 = num1 + num2;
    if(num3 > 9){
        num1 += 1;
        num3-= 10;
    }
    IO.println(String.format("Resultado: %d%d%d", num1, num3, num2));
}