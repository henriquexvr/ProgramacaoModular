import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UniversidadeTest {
    @Test
    public void atividadeComplementarNaoGeraValoresAcimaDoLimite(){
        //Arrange
        ACG atividade = new ACG("Atividade Complementar", "Estagio", 1500);
        //Act
        int resposta = atividade.gerarCreditos();
        //Assert
        assertEquals(4, resposta);
    }
}
