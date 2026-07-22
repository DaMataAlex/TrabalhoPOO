package model;

public interface Comercializavel {
    // Retorna o valor da venda com base no peso e se o animal está abatido (true) ou vivo (false)
    double calcularValorVenda(boolean abatido);
}