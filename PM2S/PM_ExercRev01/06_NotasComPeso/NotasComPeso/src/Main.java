public double parseDouble(String s){
    return Double.parseDouble(s);
}

public double calcularNotaTotal(List<Double> notas){
    double notaTotal = 0;
    int porcentagemNota = 20;
    double pontuacaoMaximaPossivel = 40;
    if(notas.size() == 2){
        porcentagemNota = 60;
        pontuacaoMaximaPossivel = 200;
    }
    for(double nota: notas){
        notaTotal += nota;
    }
    return (notaTotal / pontuacaoMaximaPossivel ) * porcentagemNota;
}

public ArrayList<Double> preencherAtividades(){
    ArrayList<Double> notas = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
        notas.add(parseDouble(IO.readln("Escreva nota da atividade "+(i+1)+" (0 a 10): ")));
    }
    return  notas;
}

public ArrayList<Double> preencherProvas(){
    ArrayList<Double> notas = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
        notas.add(parseDouble(IO.readln("Escreva nota da prova "+(i+1)+" (0 a 100): ")));
    }
    return  notas;
}

void main(){
    List<Double> notasAtividades = preencherAtividades(); //20% da nota final
    List<Double> notasProvas = preencherProvas(); // 60% da nota final
    double notaTrabalho = parseDouble(IO.readln("Escreva a nota do trabalho (0 a 20): ")); //20% da nota final
    double notaProvas = calcularNotaTotal(notasProvas);
    double notaAtivdades = calcularNotaTotal(notasAtividades);
    double notaFinal = notaProvas + notaAtivdades + notaTrabalho;

    //notasAtividades.stream().mapToDouble(Double::doubleValue).sum());
    IO.println("Nota total atividades: "+ notaAtivdades);
    //notasProvas.stream().mapToDouble(Double::doubleValue).sum()
    IO.println("Nota total prova: "+ notaProvas);
    IO.println("Nota final: "+ notaFinal);
}