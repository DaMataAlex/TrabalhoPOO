package Animais;

public class Bovino extends Animal implements model.ProdutorLeite {

    private double producaoDiariaLeite;

    public Bovino(String id, double peso, int idadeMeses, double producaoDiariaLeite) {
        super(id, peso, idadeMeses);
        this.producaoDiariaLeite = producaoDiariaLeite;
    }

    @Override
    public String emitirSom() {
        return "Muuu!";
    }

    @Override
    public double calcularCustoAlimentacaoDiario() {
        if (getPeso() > 400) {
            return 14.50;
        } else {
            return 10.00;
        }
    }

    @Override
    public double coletarLeite() {
        return this.producaoDiariaLeite;
    }

    public double getProducaoDiariaLeite() {
        return producaoDiariaLeite;
    }

    public void setProducaoDiariaLeite(double producaoDiariaLeite) {
        this.producaoDiariaLeite = producaoDiariaLeite;
    }
}