package Cultivos;

public class Cereal extends Cultivo {

    private String tipoGrao;

    public Cereal(String id, double areaPlantada, String dataPlantio, String tipoGrao) {
        super(id, areaPlantada, dataPlantio);
        this.tipoGrao = tipoGrao;
    }

    @Override
    public double calcularRendimento() {
        return getAreaPlantada() * 3500;
    }

    @Override
    public int getTempoColheitaDias() {
        return 120;
    }

    public String getTipoGrao() {
        return tipoGrao;
    }

    public void setTipoGrao(String tipoGrao) {
        this.tipoGrao = tipoGrao;
    }
}