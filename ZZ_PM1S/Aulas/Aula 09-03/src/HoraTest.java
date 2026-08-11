import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class HoraTest {
    @Test
    public void mostraAHoraComFormatacaoCorreta(){
        //Arrange (Configura)
        Hora hora = new Hora(19, 44, 2);
        
        //Act
        String horaFormatada = hora.toString();
        
        //Assert
        assertEquals("19:44:02", horaFormatada);
    }
}
