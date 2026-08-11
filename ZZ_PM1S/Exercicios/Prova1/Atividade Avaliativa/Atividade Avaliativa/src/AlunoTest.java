import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlunoTest {
    Aluno aluno1;
    @BeforeEach
    public void setUp(){
        aluno1 = new Aluno("Henrique", "12345678", 0101);
    }
    @Test
    public void creditosCalculadosCorretamente(){

        aluno1.gerarCreditos(60, 1);
        int resultado = aluno1.gerarCreditos(80, 3);
        assertEquals(6, resultado);
    }
    
    @Test
    public void naoGerarCreditosAcimaDoMaximo(){
        aluno1.gerarCreditos(150, 1);
        aluno1.gerarCreditos(225, 2);
        int resultado = aluno1.gerarCreditos(100, 3);
        assertEquals(12, resultado);
    }
}
