// === FILE: src/Main.java ===
import Animais.*;
import Pessoas.Peao;
import Pessoas.Pessoa;
import Pessoas.Veterinario;
import model.Fazenda;
import excecoes.DadoInvalidoException;
import utils.GerenciadorArquivo;

public class Main {
    public static void main(String[] args) {

        // 1. CARREGAR DADOS DA FAZENDA
        System.out.println("Iniciando sistema e buscando dados salvos...");
        Fazenda minhaFazenda = GerenciadorArquivo.carregar();

        // 2. CADASTRO INICIAL (Só acontece se o arquivo de dados estiver vazio)
        if (minhaFazenda.getAnimais().isEmpty()) {
            try {
                System.out.println("Nenhum dado encontrado. Realizando cadastro inicial...");

                // Cadastrando funcionários
                minhaFazenda.adicionarFuncionario(new Peao("João", "123.456.789-00", "01/01/1980", "(99) 99999-9999", 2000.0, "Setor A"));
                minhaFazenda.adicionarFuncionario(new Veterinario("Dra. Ana", "987.654.321-00", "10/05/1975", "(88) 88888-8888", 5000.0, "CRMV-123"));

                // Cadastrando animais
                minhaFazenda.adicionarAnimal(new Bovino("BOV-01", 350.0, 28, 31));
                minhaFazenda.adicionarAnimal(new Bovino("BOV-02", 328.0, 26, 28));
                minhaFazenda.adicionarAnimal(new Ovino("OV-01", 55.0, 12, 3.2));
                minhaFazenda.adicionarAnimal(new Ovino("OV-02", 75.0, 13, 2.9));
                minhaFazenda.adicionarAnimal(new Suino("SUI-01", 160, 8));
                minhaFazenda.adicionarAnimal(new Ave("AVE-01", 2.0, 4, 1));

                System.out.println("Cadastro inicial realizado com sucesso!\n");

            } catch (DadoInvalidoException e) {
                // Captura e exibe o erro caso alguma regra de negócio falhe
                System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
                return; // Encerra a execução caso ocorra um erro crítico no cadastro
            }
        } else {
            System.out.println("Dados carregados com sucesso! Temos " + minhaFazenda.getAnimais().size() + " animais e " + minhaFazenda.getFuncionarios().size() + " funcionários na fazenda.\n");
        }

        System.out.println("=== ROTINA DIÁRIA DA FAZENDA ===");

        if (!minhaFazenda.getFuncionarios().isEmpty()) {
            Pessoa responsavel = minhaFazenda.getFuncionarios().get(0);
            System.out.println("\nFuncionário em serviço: " + responsavel.getNome());
            System.out.println("Função: " + responsavel.desempenharFuncao());
        }

        // Iterando sobre a lista de animais gerenciada pela Fazenda
        for (Animal animal : minhaFazenda.getAnimais()) {
            System.out.println("\n[ID: " + animal.getId() + "]");
            System.out.println("Som: " + animal.emitirSom());
            System.out.println("Custo diário de alimentação: R$ " + animal.calcularCustoAlimentacaoDiario());

            if (animal instanceof model.ProdutorLeite) {
                model.ProdutorLeite produtor = (model.ProdutorLeite) animal;
                double leite = produtor.coletarLeite();
                System.out.println("Produção de leite coletada: " + leite + " litros");
                minhaFazenda.getCentroDeProducao().adicionarLeiteTotal(leite);
            }

            if (animal instanceof model.ProdutorLa) {
                model.ProdutorLa produtor = (model.ProdutorLa) animal;
                double la = produtor.tosarLa();
                System.out.println("Lã extraída na tosa: " + la + " kg");
                minhaFazenda.getCentroDeProducao().adicionarLaTotal(la);
            }
            if (animal instanceof model.ProdutorOvos) {
                model.ProdutorOvos produtor = (model.ProdutorOvos) animal;
                int ovos = produtor.coletarOvos();
                System.out.println("Ovos coletados: " + ovos + " unidades");
                minhaFazenda.getCentroDeProducao().adicionarOvosTotal(ovos);
            }
            if (animal instanceof model.Comercializavel) {
                model.Comercializavel comercializavel = (model.Comercializavel) animal;
                double valorVivo = comercializavel.calcularValorVenda(false);
                double valorAbatido = comercializavel.calcularValorVenda(true);

                System.out.println("Cotação para venda VIVO: R$ " + String.format("%.2f", valorVivo));
                System.out.println("Cotação para venda ABATIDO: R$ " + String.format("%.2f", valorAbatido));
            }
        }

        System.out.println("\n=== RESUMO FINAL DA PRODUÇÃO ===");
        System.out.println("Total de leite na fazenda: " + minhaFazenda.getCentroDeProducao().getTotalLeiteAcumulado() + " litros");
        System.out.println("Total de lã na fazenda: " + minhaFazenda.getCentroDeProducao().getTotalLaAcumulado() + " kg");
        System.out.println("Total de ovos na fazenda: " + minhaFazenda.getCentroDeProducao().getTotalOvosAcumulados() + " unidades");
        

        GerenciadorArquivo.salvar(minhaFazenda);
    }
}