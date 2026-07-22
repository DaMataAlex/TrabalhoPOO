package utils;

import model.Fazenda;
import java.io.*;

public class GerenciadorArquivo {

    private static final String Gerenciamento_Fazenda = "dados_fazenda.dat";

    public static void salvar(Fazenda fazenda) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Gerenciamento_Fazenda))) {
            oos.writeObject(fazenda);
            System.out.println("\n[SISTEMA] Dados da fazenda salvos com sucesso no arquivo!");
        } catch (IOException e) {
            System.err.println("\n[ERRO] Não foi possível salvar os dados: " + e.getMessage());
        }
    }

    public static Fazenda carregar() {
        File arquivo = new File(Gerenciamento_Fazenda);

        if (!arquivo.exists()) {
            return new Fazenda();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Gerenciamento_Fazenda))) {
            return (Fazenda) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\n[ERRO] Não foi possível carregar os dados. Criando nova fazenda. Erro: " + e.getMessage());
            return new Fazenda();
        }
    }
}