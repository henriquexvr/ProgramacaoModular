public class CursoPresencial extends Curso{
    private MaterialDidatico material;


    public CursoPresencial(String nome, int codigo, int cargaHoraria, double valorHora, IMaterial material) {
        super(nome, codigo, cargaHoraria, valorHora, material);
    }
    

    @Override
    public double valorTotal(){
        return valorAulas() + material.valorTotal();
    }

    @Override
    public double valorAulas(){
        return valorHora * cargaHoraria;
    }
     
    public int incluirMaterial(MaterialDidatico material){
        this.material = material;
        
    }
}
