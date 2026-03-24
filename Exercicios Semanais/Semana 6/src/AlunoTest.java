import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlunoTest {
    Aluno aluno1;
    @BeforeEach
    public void setUp(){
        aluno1 = new Aluno("Lucas", 12345);
    }
    @Test
    public void alunoEstaAprovado(){
        //Act
        aluno1.calculaNota(10d);
        aluno1.calculaNota(17d);
        aluno1.calculaNota(22d);
       double resultado = aluno1.calculaNota(12d);
        //Assert
        assertEquals(61d, resultado);
    }

    @Test
    public void alunoEstaReprovado(){
        //Act
        aluno1.calculaNota(10d);
        aluno1.calculaNota(12d);
        aluno1.calculaNota(6d);
       double resultado = aluno1.calculaNota(13d);
        //Assert
        assertEquals(41d, resultado);
    }
}
