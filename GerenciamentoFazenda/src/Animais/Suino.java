package Animais;

import model.Comercializavel;

public class Suino extends Animal implements Comercializavel {

    public Suino(String id, double peso, int idadeMeses) {
        super(id, peso, idadeMeses);
    }

    @Override
    public String emitirSom() {
        return "Oinc!";
    }

    @Override
    public double calcularCustoAlimentacaoDiario() {
        return 3.80;
    }

    @Override
    public double calcularValorVenda(boolean abatido) {
        // Se abatido, R$ 6.00 por Kg. Se vivo, R$ 8.50 por Kg.
        if (abatido) {
            return getPeso() * 6.00;
        } else {
            return getPeso() * 8.50;
        }
    }
}