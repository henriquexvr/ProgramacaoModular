int[] lerInteiros(){
        int[] lados = new int[3];

        for (int i = 0; i < lados.length; i++) {
            lados[i] = Integer.parseInt(IO.readln("Escreva o tamanho do lado "+(i+1)+": "));
        }
        return lados;
    }

    String compararLados(int lados[]){
        String tipoTriangulo = "Triangulo Escaleno";
        if(lados[0] == lados[1] && lados[1] == lados[2]){
            tipoTriangulo = "Triangulo Equilatero";
        }

        if((lados[0] == lados[1] && lados[1] != lados[2]) || (lados[1] == lados[2] && lados[0] != lados[2])){
            tipoTriangulo = "Triangulo Isosceles";
        }
        return tipoTriangulo;
    }

void main(){
    int lados[] = lerInteiros();
    IO.println(compararLados(lados));
}
    
    

