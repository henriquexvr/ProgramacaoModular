import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
    Calculadora calc;
    
    @Test
    public void testandoMetodoDeSoma(){
        calc = new Calculadora(10, 15);

        calc.somar();
        String resultado = calc.toString();

        assertEquals("25,00", resultado);
    }

    @Test
    public void testandoMetodoDeSubtracao(){
        calc = new Calculadora(25, 17);
        
        calc.subtrair();
        String resultado = calc.toString();

        assertEquals("8,00", resultado);
    }

    @Test
    public void testandoMetodoDeMultiplicacao(){
        calc = new Calculadora(8, 47);

        calc.multiplicar();
        String resultado = calc.toString();

        assertEquals("376,00", resultado);
    }

    @Test
    public void testandoMetodoDeDivisao(){
        calc = new Calculadora(38, 17);
        
        calc.dividir();
        String resultado = calc.toString();

        assertEquals("2,24", resultado);
    }
}
