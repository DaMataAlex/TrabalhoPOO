package Cultivos;

import java.io.Serializable;

public abstract class Cultivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private double areaPlantada;
    private String dataPlantio;

    public Cultivo(String id, double areaPlantada, String dataPlantio) {
        this.id = id;
        this.areaPlantada = areaPlantada;
        this.dataPlantio = dataPlantio;
    }

    public abstract double calcularRendimento();
    public abstract int getTempoColheitaDias();
    // -----------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAreaPlantada() {
        return areaPlantada;
    }

    public void setAreaPlantada(double areaPlantada) {
        this.areaPlantada = areaPlantada;
    }

    public String getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(String dataPlantio) {
        this.dataPlantio = dataPlantio;
    }
}