package Cultivos;

public class Forragem extends Cultivo {

    private String destinoAnimal;
    private int ciclosPorAno;

    public Forragem(String id, double areaPlantada, String dataPlantio, String destinoAnimal, int ciclosPorAno) {
        super(id, areaPlantada, dataPlantio);
        this.destinoAnimal = destinoAnimal;
        this.ciclosPorAno = ciclosPorAno;
    }

    @Override
    public double calcularRendimento() {
        return getAreaPlantada() * 5000 * ciclosPorAno;
    }

    @Override
    public int getTempoColheitaDias() {
        return 60;
    }

    public String getDestinoAnimal() {
        return destinoAnimal;
    }

    public void setDestinoAnimal(String destinoAnimal) {
        this.destinoAnimal = destinoAnimal;
    }

    public int getCiclosPorAno() {
        return ciclosPorAno;
    }

    public void setCiclosPorAno(int ciclosPorAno) {
        this.ciclosPorAno = ciclosPorAno;
    }
}