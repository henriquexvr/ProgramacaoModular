import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarroTest {
    Carro carro1;
    
    @BeforeEach
    public void setUp(){
        carro1 = new Carro("GTA2F69");
    }
    

    @Test
    public void aumentandoVelocidadeDoCarro(){
        
        for (int i = 0; i < 12; i++) {
            carro1.acelerar();
        }
        String velocidadeFinal = carro1.mostrarVelocidade();

        assertEquals("Velocidade atual: 60 Km/h", velocidadeFinal);
    }

    @Test
    public void velocidadeLimitadaDoCarro(){
        for (int i = 0; i < 200; i++) {
            carro1.acelerar();
        }
        String velocidadeFinal = carro1.mostrarVelocidade();

        assertEquals("Velocidade atual: 120 Km/h", velocidadeFinal);
    }

    @Test
    public void diminuindoVelocidadeDoCarro(){
        for (int i = 0; i < 200; i++) {
            carro1.acelerar();
        }
        
        for (int i = 0; i < 10; i++) {
            carro1.freiar();
        }

        String velocidadeFinal = carro1.mostrarVelocidade();

        assertEquals("Velocidade atual: 70 Km/h", velocidadeFinal);

    }
}
