package Cultivos;

public class Legume extends Cultivo {

    private String tipoLegume;
    private boolean exigeTutoramento;

    public Legume(String id, double areaPlantada, String dataPlantio, String tipoLegume, boolean exigeTutoramento) {
        super(id, areaPlantada, dataPlantio);
        this.tipoLegume = tipoLegume;
        this.exigeTutoramento = exigeTutoramento;
    }

    @Override
    public double calcularRendimento() {
        return getAreaPlantada() * 4500;
    }

    @Override
    public int getTempoColheitaDias() {
        return 90;
    }

    public String getTipoLegume() {
        return tipoLegume;
    }

    public void setTipoLegume(String tipoLegume) {
        this.tipoLegume = tipoLegume;
    }

    public boolean isExigeTutoramento() {
        return exigeTutoramento;
    }

    public void setExigeTutoramento(boolean exigeTutoramento) {
        this.exigeTutoramento = exigeTutoramento;
    }
}