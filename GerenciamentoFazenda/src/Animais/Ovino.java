package Animais;

public class Ovino extends Animal implements model.ProdutorLa {

    private double quantidadeLaDisponivel;

    public Ovino(String id, double peso, int idadeMeses, double quantidadeLaDisponivel) {
        super(id, peso, idadeMeses);
        this.quantidadeLaDisponivel = quantidadeLaDisponivel;
    }

    @Override
    public String emitirSom() {
        return "Mbééé!";
    }

    @Override
    public double calcularCustoAlimentacaoDiario() {
        return 2.50;
    }

    @Override
    public double tosarLa() {
        double totalTosado = this.quantidadeLaDisponivel;
        this.quantidadeLaDisponivel = 0.0;
        return totalTosado;
    }

    public double getQuantidadeLaDisponivel() {
        return quantidadeLaDisponivel;
    }

    public void setQuantidadeLaDisponivel(double quantidadeLaDisponivel) {
        this.quantidadeLaDisponivel = quantidadeLaDisponivel;
    }
}