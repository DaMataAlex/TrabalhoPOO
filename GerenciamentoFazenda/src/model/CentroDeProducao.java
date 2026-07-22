package model;

import java.io.Serializable;

public class CentroDeProducao implements Serializable {
    private static final long serialVersionUID = 1L;

    private double totalLeiteAcumulado = 0.0;
    private double totalLaAcumulado = 0.0;
    private int totalOvosAcumulados = 0;
    private double totalArrecadadoVendas = 0.0;

    public void adicionarLeiteTotal(double quantidade) {
        this.totalLeiteAcumulado += quantidade;
    }

    public double getTotalLeiteAcumulado() {
        return totalLeiteAcumulado;
    }

    public void adicionarLaTotal(double quantidade) {
        this.totalLaAcumulado += quantidade;
    }

    public double getTotalLaAcumulado() {
        return totalLaAcumulado;
    }

    public void adicionarOvosTotal(int quantidade) {
        this.totalOvosAcumulados += quantidade;
    }

    public int getTotalOvosAcumulados() {
        return totalOvosAcumulados;
    }

    public void adicionarValorVenda(double valor) {
        this.totalArrecadadoVendas += valor;
    }

    public double getTotalArrecadadoVendas() {
        return totalArrecadadoVendas;
    }
}