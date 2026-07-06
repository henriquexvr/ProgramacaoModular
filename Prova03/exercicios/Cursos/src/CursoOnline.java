import java.util.ArrayList;
import java.util.List;

public class CursoOnline extends Curso {
    private static final double TUTORIA = 0.2;
    private int totalAvaliacoes;
    private int qntdAvaliacoes;
    private List<MaterialOnline> material = new ArrayList<>();


    public CursoOnline(String nome, int codigo, int cargaHoraria, double valorHora, IMaterial material) {
        super(nome, codigo, cargaHoraria, valorHora, material);
    }


    @Override
    public double valorTotal(){
        return valorAulas();
    }

    @Override
    public double valorAulas(){
        return valorHora * cargaHoraria;
    }

    public int incluirMaterial(MaterialOnline online){
        if(online == null){
            throw new  IllegalArgumentException("Material Online não foi encontrado");
        } 
        material.addLast(online);
        return material.size();
    }
}
