package Animais;

import java.io.Serializable;

public abstract class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private double peso;
    private int idadeMeses;

    public Animal(String id, double peso, int idadeMeses) {
        this.id = id;
        this.peso = peso;
        this.idadeMeses = idadeMeses;
    }

    public abstract String emitirSom();
    public abstract double calcularCustoAlimentacao();
    // -----------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getIdadeMeses() {
        return idadeMeses;
    }

    public void setIdadeMeses(int idadeMeses) {
        this.idadeMeses = idadeMeses;
    }
}