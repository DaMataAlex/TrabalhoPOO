package Cultivos;

public class Fruta extends Cultivo {

    private boolean perene;

    public Fruta(String id, double areaPlantada, String dataPlantio, boolean perene) {
        super(id, areaPlantada, dataPlantio);
        this.perene = perene;
    }

    @Override
    public double calcularRendimento() {
        return getAreaPlantada() * 12000;
    }

    @Override
    public int getTempoColheitaDias() {
        return 365;
    }

    public boolean isPerene() {
        return perene;
    }

    public void setPerene(boolean perene) {
        this.perene = perene;
    }
}