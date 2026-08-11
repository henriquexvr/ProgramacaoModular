import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContaBancariaTest {
    ContaBancaria conta;
    
    @BeforeEach
    public void setUp(){
        conta = new ContaBancaria();
    }
            
    @Test
    void depositarComSaldoNegativo(){
        //Arrange
        conta.deposito(-10.0);
        //Act
         Double resultado = conta.deposito(100.0);   
        //Assert   
        assertEquals(87, resultado);
    }
    @Test
    void depositarComSaldoPositivo(){
        //Arrange
        
        //Act
         Double resultado = conta.deposito(100.0);   
        //Assert   
        assertEquals(100, resultado);
    }
}
