// === FILE: src/Animais/Ave.java ===
package Animais;

import model.ProdutorOvos;
import model.Comercializavel;

public class Ave extends Animal implements ProdutorOvos, Comercializavel {

    private int producaoDiariaOvos;

    public Ave(String id, double peso, int idadeMeses, int producaoDiariaOvos) {
        super(id, peso, idadeMeses);
        this.producaoDiariaOvos = producaoDiariaOvos;
    }

    @Override
    public String emitirSom() {
        return "Cócó!";
    }

    @Override
    public double calcularCustoAlimentacaoDiario() {
        return 0.50;
    }

    @Override
    public int coletarOvos() {
        return this.producaoDiariaOvos;
    }

    @Override
    public double calcularValorVenda(boolean abatido) {
        // Se abatida, R$ 7.50 por Kg. Se viva, R$ 5.50 por Kg.
        if (abatido) {
            return getPeso() * 7.50;
        } else {
            return getPeso() * 5.50;
        }
    }
}