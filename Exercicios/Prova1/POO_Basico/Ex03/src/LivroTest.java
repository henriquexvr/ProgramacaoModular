import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LivroTest {
    Livro livro1;
    @BeforeEach
    public void setUp(){
        livro1 = new Livro("Machado", "Um livro de machados", 277);
    }
    @Test
    public void avaliacaoMediaDoLivro(){
        livro1.avaliar(5);
        livro1.avaliar(4);
        livro1.avaliar(3);
        livro1.avaliar(2);
        double resultado = livro1.mediaAvaliacoes();

        assertEquals(3.5, resultado);
    }

    @Test 
    public void tempoEstimadoDeLeituraEmHorasEMinutos(){

        String result = livro1.estimativaLeitura(3);

        assertEquals("13 horas e 51 minutos", result);
    }
}
