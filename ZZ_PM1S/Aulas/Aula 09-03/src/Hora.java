    //*"Responder " a HH: MM: SS
    // Increvementar em minutos (Quantos?) 
    // Comparar com outra (Qual?) */
public class Hora {
    private int horas;
    private int minutos;
    private int segundos;

public Hora(int hora, int minuto, int segundo){
            if(!estaValida()){
                this.horas = 0;
                this.minutos = 0;
                this.segundos = 0;
            }
        }

boolean estaValida(){
        return horas >= 0 && horas <= 23 && minutos >= 0 && minutos <= 59 && segundos >= 0 && segundos <= 59;
    }


/**Converte uma quantidade minutos maior do que 60 em horas e minutos, para manter consistencia de horario */
    void converterMinutos(){
            int horasTotais = this.minutos / 60;
            int minutosRestantes = this.minutos % 60;
            this.horas += horasTotais;
            this.minutos = minutosRestantes;
    }
    
    public Hora incrementar(int quantos){
        Hora novaHora = null;
        
        if(minutos > 0){
            novaHora = new Hora(horas, minutos+quantos, segundos);
            novaHora.converterMinutos();
        }
        return novaHora;
    }

    public boolean estaNaFrente(Hora outra){
        int minhaHora = this.horas * 3600 + this.minutos * 60 + this.segundos;
        int outraHora = outra.horas * 3600 + outra.minutos * 60 + outra.segundos;
        return (minhaHora > outraHora);
    }

    public String toString(){
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}
