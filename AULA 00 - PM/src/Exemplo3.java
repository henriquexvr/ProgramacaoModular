public class Exemplo3 {

/*Este método deve receber como parâmetro um
vetor de strings correspondendo a uma lista
de emails e retornar outro vetor contendo
somente os emails que pertençam ao domínio
especificado no segundo parâmetro. */

     public static String[] filtroEmails(String[] emails, String dominio){
        String[] res;
        int contador = 0;
        for(int i = 0; i < emails.length; i++){
            int index = dominio.indexOf(emails[i]);

            if(index != -1){
                contador++;
            }
        }
        return res;
    }
    
    public static void main(String[] args){
        String[] emails = { "caram@pucminas.br", "caram@gmail.com", "engsoftware.lourdes@pucminas.br", "provas.lourdes@pucminas.br", "contact@microsoft.com","education@githubcom", "costumerservice@redhat.com"};
        String dominio = "pucminas.br";

        String[] resultado = filtroEmails(emails, dominio);     
    }
}


